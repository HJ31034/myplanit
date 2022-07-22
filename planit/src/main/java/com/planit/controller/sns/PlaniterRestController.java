package com.planit.controller.sns;

import java.io.BufferedOutputStream;
 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

 
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.FollowDTO;
 
import com.planit.mapper.sns.SnsMapper;
import com.planit.service.sns.ProfileService;
import com.planit.service.sns.snsService;

@RestController
@RequestMapping(value = "/planiter")
public class PlaniterRestController {
	 @Value("${planit.upload.path}")
	    private String saveDir;
	 
	@Autowired
	private snsService snsservice;

	@Autowired
	private SnsMapper snsmapper;

	@Autowired
	private ProfileService profileService;
 
	// 팔로잉 리스트
		@RequestMapping(value = "/followingList.do", method = RequestMethod.GET)
		public List<FollowDTO> follower(Model model, ModelAndView mav,
										@RequestParam(value="page") int page, HttpSession session,
										@RequestParam(value="userId",required = false)String USERID) {
			 
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
			 
			    List<FollowDTO> followingList_TEMP = new ArrayList<FollowDTO>();
				List<FollowDTO> followingList = snsservice.selectFollowingList(startNum,endNum,USERID);
			 
				for (FollowDTO followDTO : followingList) {  
				String followID = followDTO.getFollowingId();
				String ProfileIMG=followDTO.getProfileImg();
				String ACCdescription=followDTO.getAccDescription();
				 
				FollowDTO dto = new FollowDTO();
				dto.setFollowingId(followID);
				dto.setFollowerId(USERID);
				dto.setAccDescription(ACCdescription);
				dto.setProfileImg(ProfileIMG);
				
				List<FollowDTO> fList = snsservice.selectFollowerList2(session.getAttribute("id").toString(),followID); 
				
				if (fList.size() == 0) {
					dto.setFtype(0);
				} else {
					dto.setFtype(1);
				}
				followingList_TEMP.add(dto);
				 
			 }   
		  return followingList_TEMP; 
		}


	// 팔로워 리스트
	@RequestMapping(value = "/followerList.do", method = RequestMethod.GET)
	public List<FollowDTO> Following(Model model, ModelAndView mav,@RequestParam(value="page") int page,
									 HttpSession session, @RequestParam(value="userId",required = false) String USERID) { 
		List<FollowDTO> followerList_TEMP = new ArrayList<FollowDTO>();
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
			System.out.println("데이터 시작");
			String followID = followDTO.getFollowerId(); 
			String ProfileIMG = followDTO.getProfileImg();
			String ACCdescription = followDTO.getAccDescription();
			  
			FollowDTO dto = new FollowDTO();
			dto.setFollowerId(USERID);
			dto.setFollowingId(followID);
			dto.setAccDescription(ACCdescription);
			dto.setProfileImg(ProfileIMG);
			List<FollowDTO> fList = snsservice.selectFollowerList2(session.getAttribute("id").toString(), followID); 
			System.out.println("데이터 끝"); 
			 
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
			@RequestParam(value = "followingId") String followingId, HttpSession session) {
		int ftype=1;
		 
		String USERID = session.getAttribute("id").toString();
		snsservice.insertFollow(USERID,USERID,followingId,ftype);
		
		snsmapper.updateFollowcount(USERID, "follower");
		snsmapper.updateFollowcount(followingId, "following");
 
		return profileService.selectUserProfile(userId);
	}

	@RequestMapping(value = "/unfollow.do")
	public AccountDTO unfollow(@RequestParam(value = "followerId") String followerId,
			@RequestParam(value = "followingId") String followingId, HttpSession session) {
		String USERID = session.getAttribute("id").toString();
		snsservice.unfollow(USERID, followingId);
 
		snsmapper.updateFollowcount(USERID, "unfollowing");
		snsmapper.updateFollowcount(followingId, "unfollower");
		
		return profileService.selectUserProfile(followerId);
	}

	// 프로필 수정 페이지 이동
	@GetMapping(value = "/goProfileEdit.do")
	public ModelAndView goProfileEdit(Model model, ModelAndView mav, AccountDTO dto, HttpSession session) {
		   String USERID = "";
		
		   if (session.getAttribute("id") != null) {
				USERID = session.getAttribute("id").toString();
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
		 
		 

		if (profileIMG.isEmpty()) {
			fileName = "thumb.png";
		}
		 
		// 파일업로드
		try {
			byte[] fileData = profileIMG.getBytes();
			OutputStream out = new FileOutputStream(saveDir + fileName.trim());
			System.out.println(saveDir +  fileName);
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
 
		mav.setViewName("redirect:/planiter/goProfileEdit.do");
		return mav;

	}
	
	@ResponseBody
	@GetMapping("/planiter/post/image/{filename}")
	public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
		return new UrlResource("file:///" + saveDir + filename.trim());
	}

}
