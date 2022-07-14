package com.planit.controller.sns;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.FollowDTO;
import com.planit.domain.sns.PlantsCateDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.mapper.sns.SnsMapper;
import com.planit.service.sns.ProfileService;
import com.planit.service.sns.snsService;

@RestController
@RequestMapping(value = "/planiter")
public class PlaniterRestController {

	@Autowired
	private snsService snsservice;

	@Autowired
	private SnsMapper snsmapper;

	@Autowired
	private ProfileService profileService;
	
	// 팔로잉 리스트
		@RequestMapping(value = "/followingList.do", method = RequestMethod.GET)
		public List<FollowDTO> follower(Model model, ModelAndView mav,
				@RequestParam(value="page") int page, HttpSession session) {
			System.out.println("followingList con page: "+page);
			
			 int nowPage = page;
			 int startNum=1;
			 int endNum=5;
			 int numPerPage=5; 
			 
			 if (page==1){
				 
				 startNum = 1;
				 endNum = numPerPage ;
				 
			 }else{
				 startNum = (page * numPerPage)-numPerPage+1;
				 endNum = numPerPage;
			}
			
			  String USERID = null;
				
			  if (session.getAttribute("id") != null) {
					USERID = session.getAttribute("id").toString();
				}else {
					USERID="guest";
				}
				  
			List<FollowDTO> followingList_TEMP = new ArrayList<FollowDTO>();
			List<FollowDTO> followingList = snsservice.selectFollowingList(startNum,endNum,USERID);
			System.out.println("RestCon Service selectFollowingList startNum: "+startNum+" endNum: "+endNum+" userId: "+USERID);
		 
//			for (FollowDTO followDTO : followingList) {  
//				String followID = followDTO.getFollowerId();
//				String ProfileIMG=followDTO.getProfileImg();
//				String ACCdescription=followDTO.getAccDescription();
//				
//				System.out.println("follow followingList: "+ followID);
//				
//				FollowDTO dto = new FollowDTO();
//				dto.setFollowerId(followID);
//				dto.setUserId(USERID);
//				dto.setAccDescription(ACCdescription);
//				dto.setProfileImg(ProfileIMG);
//				
//				List<FollowDTO> fList = snsservice.selectFollowingList(startNum,endNum,USERID);
//				
//				if (fList.size() == 0) {
//					dto.setFtype(0);
//				} else {
//					dto.setFtype(1);
//				}
//				followingList_TEMP.add(dto);
//				 
//			 }   

			return followingList;

		}


	// 팔로워 리스트
	@RequestMapping(value = "/followerList.do", method = RequestMethod.GET)
	public List<FollowDTO> Following(Model model, ModelAndView mav,@RequestParam(value="page") int page,
			HttpSession session) {
		System.out.println("followerList con page: "+page);
		List<FollowDTO> followerList_TEMP = new ArrayList<FollowDTO>();
		
		  String USERID = "";
			
		  if (session.getAttribute("id") != null) {
				USERID = session.getAttribute("id").toString();
			}else {
				USERID="guest";
			}
		  
		 int nowPage = page;
		 int startNum=1;
		 int endNum=5;
		 int numPerPage=5; 
		 
		 if (page==1){
			 
			 startNum = 1;
			 endNum = numPerPage ;
			 
		 }else{
			 startNum = (page * numPerPage)-numPerPage+1;
			 endNum = numPerPage;
		}
		
		
		
		List<FollowDTO> followerList = snsservice.selectFollowerList(startNum,endNum,USERID);
		for (FollowDTO followDTO : followerList) {
			String followID = followDTO.getFollowerId();
			System.out.println("aaaaaaaaaaaa: " + followID);
			String ProfileIMG = followDTO.getProfileImg();
			String ACCdescription = followDTO.getAccDescription();
			System.out.println("followIDIDIDIDIDID: " + followID);
			
			FollowDTO dto = new FollowDTO();
			dto.setFollowerId(USERID);
			dto.setFollowingId(followID);
			dto.setAccDescription(ACCdescription);
			dto.setProfileImg(ProfileIMG);

			List<FollowDTO> fList = snsservice.selectFollowerList2(USERID, followID);// followID기준
			System.out.println("followID: " + followID + " flist: " + fList.size());

			 
		 
			if (fList.size() == 0) {
				dto.setFtype(0);
			} else {
				dto.setFtype(1);
			}
			 
			followerList_TEMP.add(dto);
		}
	 
		return followerList_TEMP;

	}

	@RequestMapping(value = "/follow.do")
	public AccountDTO follow(@RequestParam(value = "followerId") String userId,
			@RequestParam(value = "followingId") String followingId) {
		int ftype=1;
		 
		snsservice.insertFollow(userId,userId,followingId,ftype);
		
		snsmapper.updateFollowcount(userId, "follower");
		snsmapper.updateFollowcount(followingId, "following");
 
		return profileService.selectUserProfile(userId);
	}

	@RequestMapping(value = "/unfollow.do")
	public AccountDTO unfollow(@RequestParam(value = "followerId") String followerId,
			@RequestParam(value = "followingId") String followingId,@RequestParam(value="page") int page) {
	    System.out.println("unfollow: "+followerId+" / "+followingId);
	    
		snsservice.unfollow(followerId, followingId);
 
		snsmapper.updateFollowcount(followerId, "unfollowing");
		snsmapper.updateFollowcount(followingId, "unfollower");
		
		return profileService.selectUserProfile(followerId);
	}

	// 프로필 수정 페이지 이동
	@GetMapping(value = "/goProfileEdit.do")
	public ModelAndView goProfileEdit(Model model, ModelAndView mav, AccountDTO dto, HttpSession session) {
		   String USERID = ""	;
		
		   if (session.getAttribute("id") != null) {
				USERID = session.getAttribute("id").toString();
			}else {
				USERID="guest";
			}
			
		
		List<AccountDTO> AccInfo = snsservice.selectMainAccINfo(USERID);
		model.addAttribute("AccInfo", AccInfo);

		mav.setViewName("sns/profile_edit");
		return mav;
	}

	// 프로필 수정
	@RequestMapping(value = "/updateInfo.do", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	public ModelAndView updateBlogInfo(@RequestParam(value = "InfoDes") String InfoDes,
			@RequestParam(value = "profileIMG") MultipartFile profileIMG, ModelAndView mav, HttpSession session
			 ) throws IOException {
		
		
		 String USERID = null;
			
		  if (session.getAttribute("id") != null)
		  USERID = session.getAttribute("id").toString();
			
		
		String fileName = profileIMG.getOriginalFilename();
		String saveDir = getClass().getClassLoader().getResource("static").getFile() + "/imgs/img_section";
		 

		if (profileIMG.isEmpty()) {
			fileName = "thumb.png";
		}
		 
		// 파일업로드
		try {
			byte[] fileData = profileIMG.getBytes();
			OutputStream out = new FileOutputStream(saveDir + "/" + fileName);
			BufferedOutputStream bout = new BufferedOutputStream(out);

			bout.write(fileData);

			if (bout != null) {
				bout.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		AccountDTO dto = new AccountDTO();
		dto.setProfileImg(fileName);
		dto.setAccDescription(InfoDes);
		dto.setUserId(USERID);
		snsservice.updateInfo(dto);

		System.out.println("InfoDes: " + InfoDes);
		mav.setViewName("redirect:/planiter/goProfileEdit.do");
		return mav;

	}

}
