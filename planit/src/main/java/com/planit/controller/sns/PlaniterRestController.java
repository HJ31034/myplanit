package com.planit.controller.sns;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
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
import com.planit.service.sns.snsService;

 
@RestController
@RequestMapping(value = "/planiter")
public class PlaniterRestController {

	@Autowired
	private snsService snsservice;
	
	@Autowired
	private SnsMapper snsmapper;
	 

 
	@RequestMapping(value="/imgList.do")
	public List<PostDTO> imgList(@RequestParam(value="page") int page , Model model){ //메인 POST IMG리스트
		
		
		 int totalCnt = snsmapper.selectImgPostCnt();
		 int nowPage = page;
		 int startNum=0;
		 int endNum=9;
		 int numPerPage=9; 
		 
		 if (page==1){
			 
			 startNum = 1;
			 endNum = numPerPage ;
			 
		 }else{
			 startNum = (page * numPerPage)-numPerPage;
			 endNum = numPerPage;
			 	}
			 
		 PostDTO dto = new PostDTO();
		
		 model.addAttribute("totalCnt",snsmapper.selectImgPostCnt());
		 model.addAttribute("startNum", startNum);
		 
		 List<PostDTO> imgList = snsservice.selectImgPost(startNum, endNum);
		 
	
		
		return imgList;
	}
	
	
	
	// 팔로잉 리스트
	@RequestMapping(value = "/followingList.do", method = RequestMethod.GET)
	public List<FollowDTO> follower(Model model, ModelAndView mav) {
		String USERID = "kosta";
		List<FollowDTO> followerList_TEMP = new ArrayList<FollowDTO>();
		List<FollowDTO> followerList = snsservice.selectFollowingList(USERID);
		 
		
		for (FollowDTO followDTO : followerList) { //USERID 기준 팔로워리스트 데이터
			String followID = followDTO.getFollowerId();
			String ProfileIMG=followDTO.getProfileImg();
			String ACCdescription=followDTO.getAccDescription();
			
			System.out.println("follow followingList: "+ followID);
			
			FollowDTO dto = new FollowDTO();
			dto.setFollowerId(followID);
			dto.setUserId(USERID);
			dto.setAccDescription(ACCdescription);
			dto.setProfileImg(ProfileIMG);
			
			List<FollowDTO> fList = snsservice.selectFollowingList(USERID);
			 
			if(fList.size() != 0) {
				dto.setFollowingId("1");
			}else {
				dto.setFollowingId("0");
			} 
				followerList_TEMP.add(dto);
				System.out.println("fList: "+fList);
		}
		 
		return followerList_TEMP;

	}
	
	// 팔로워 리스트
	@RequestMapping(value = "/followerList.do", method = RequestMethod.GET)
	public List<FollowDTO> Following(Model model, ModelAndView mav) {
		 
		
		List<FollowDTO> followingList_TEMP = new ArrayList<FollowDTO>();
		
		String USERID = "kosta";
		
		List<FollowDTO> followingList = snsservice.selectFollowerList(USERID);
		for (FollowDTO followDTO : followingList) {
			String followID = followDTO.getUserId();
			String ProfileIMG=followDTO.getProfileImg();
			String ACCdescription=followDTO.getAccDescription();
			System.out.println("followIDIDIDIDIDID: "+ followID);
			FollowDTO dto = new FollowDTO();
			dto.setFollowerId(followID); 
			dto.setUserId(USERID);
			dto.setAccDescription(ACCdescription);
			dto.setProfileImg(ProfileIMG);
		 
			//List<FollowDTO> fList = snsservice.selectFollowerList(followID);
			List<FollowDTO> fList = snsservice.selectFollowerList2(USERID,followID);//followID기준 
			System.out.println("followID: "+followID +" flist: "+fList.size());
			
			if(fList.size() != 0) {
				dto.setFollowingId("1");
			}else {
				dto.setFollowingId("0");
			} 
			followingList_TEMP.add(dto);
		}
		
		
		
		return followingList_TEMP;

	}
	
	
	@RequestMapping(value="/follow.do")
	public String follow(@RequestParam(value="followID") String followID,@RequestParam(value="USERID") String USERID ) {
		 
		 System.out.println("FOLLOW !! followID: "+followID+" USERID: "+USERID);
		 snsservice.insertFollow(USERID,followID);
		 
		 
		return "1";
	}
	
	
	@RequestMapping(value="/unfollow.do")
	public String unfollow(@RequestParam(value="followID") String followID,@RequestParam(value="USERID") String USERID) {
		 System.out.println("UNFOLLOW !! followID: "+followID+" USERID: "+USERID);
		 snsservice.unfollow(USERID, followID);
		 
		 return "1";
	}
	
	
	
	//프로필 수정 페이지 이동
	@GetMapping(value = "/goProfileEdit.do")
	public ModelAndView goProfileEdit(Model model, ModelAndView mav,AccountDTO dto) {
		 
		List<AccountDTO> AccInfo = snsservice.selectMainAccINfo();
		model.addAttribute("AccInfo", AccInfo);
		
		mav.setViewName("sns/profile_edit");
		return mav;
	}

	// 프로필 수정
	@RequestMapping(value = "/updateInfo.do", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	public ModelAndView updateBlogInfo(@RequestParam(value = "InfoDes") String InfoDes,
			@RequestParam(value = "profileIMG") MultipartFile profileIMG, ModelAndView mav) throws IOException {
		String fileName = new String(profileIMG.getOriginalFilename().getBytes("8859_1"), "UTF-8");
		
		String saveDir = "C:/Users/illus/Documents/GitHub/KOSTA_Project/planit/src/main/resources/static/imgs/img_section/";
		
		if(profileIMG.isEmpty()) {
			fileName="thumb.png";
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
		dto.setUserId("kosta");
		snsservice.updateInfo(dto);

		System.out.println("InfoDes: " + InfoDes);
		mav.setViewName("redirect:/planiter/goProfileEdit.do");
		return mav;

	}

}
