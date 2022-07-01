package com.planit.controller.sns;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
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

	// 팔로잉 리스트
	@RequestMapping(value = "/followingList.do", method = RequestMethod.GET)
	public List<FollowDTO> follower(Model model, ModelAndView mav,
			@RequestParam(value="page") int page) {
		System.out.println("followingList con page: "+page);
		
		 int nowPage = page;
		 int startNum=0;
		 int endNum=5;
		 int numPerPage=5; 
		 
		 if (page==1){
			 
			 startNum = 1;
			 endNum = numPerPage ;
			 
		 }else{
			 startNum = (page * numPerPage)-numPerPage;
			 endNum = numPerPage;
		}
		
		String USERID = "kosta";
		
		List<FollowDTO> followingList = snsservice.selectFollowingList(startNum,endNum,USERID);
		System.out.println("RestCon Service selectFollowingList startNum: "+startNum+" endNum: "+endNum+" userId: "+USERID);
		 
		System.out.println("followingList return: "+followingList);

		return followingList;

	}

	// 팔로워 리스트
	@RequestMapping(value = "/followerList.do", method = RequestMethod.GET)
	public List<FollowDTO> Following(Model model, ModelAndView mav) {
 
//		 int nowPage = page;
//		 int startNum=0;
//		 int endNum=5;
//		 int numPerPage=5; 
//		 
//		 if (page==1){
//			 
//			 startNum = 1;
//			 endNum = numPerPage ;
//			 
//		 }else{
//			 startNum = (page * numPerPage)-numPerPage;
//			 endNum = numPerPage;
//		}
		
		List<FollowDTO> followerList_TEMP = new ArrayList<FollowDTO>();
		
		String USERID = "kosta";

		 
		//List<FollowDTO> followerList = snsservice.selectFollowerList(1,5,USERID);
		List<FollowDTO> followerList = snsservice.selectFollowerList(USERID);
		for (FollowDTO followDTO : followerList) {
			String followID = followDTO.getFollowerId();
			System.out.println("Following followID: " + followID);
			String ProfileIMG = followDTO.getProfileImg();
			String ACCdescription = followDTO.getAccDescription();
			System.out.println("Following followIDIDIDIDIDID: " + followID);
			
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
		
		System.out.println("followerList_TEMP: "+followerList_TEMP);
 
		return followerList_TEMP;

	}

	@RequestMapping(value = "/follow.do")
	public String follow(@RequestParam(value = "followerId") String userId,
			@RequestParam(value = "followingId") String followingId) {
		int ftype=1;
		 
		snsservice.insertFollow(userId,userId, followingId,ftype);
		
		snsmapper.updateFollowcount(userId, "follower");
		snsmapper.updateFollowcount(followingId, "following");
 
		return "1";
	}

	@RequestMapping(value = "/unfollow.do")
	public String unfollow(@RequestParam(value = "followerId") String followerId,
			@RequestParam(value = "followingId") String followingId) {
	 
		snsservice.unfollow(followerId, followingId);
 
		snsmapper.updateFollowcount(followerId, "unfollowing");
		snsmapper.updateFollowcount(followingId, "unfollower");
		
	 
		return "1";
	}

	// 프로필 수정 페이지 이동
	@GetMapping(value = "/goProfileEdit.do")
	public ModelAndView goProfileEdit(Model model, ModelAndView mav, AccountDTO dto) {
			String USERID="kosta";
		
		List<AccountDTO> AccInfo = snsservice.selectMainAccINfo(USERID);
		model.addAttribute("AccInfo", AccInfo);

		mav.setViewName("sns/profile_edit");
		return mav;
	}

	// 프로필 수정
	@RequestMapping(value = "/updateInfo.do", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	public ModelAndView updateBlogInfo(@RequestParam(value = "InfoDes") String InfoDes,
			@RequestParam(value = "profileIMG") MultipartFile profileIMG, ModelAndView mav) throws IOException {
//		String fileName = new String(profileIMG.getOriginalFilename().getBytes("8859_1"), "UTF-8");
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
		dto.setUserId("kosta");
		snsservice.updateInfo(dto);

		System.out.println("InfoDes: " + InfoDes);
		mav.setViewName("redirect:/planiter/goProfileEdit.do");
		return mav;

	}

}
