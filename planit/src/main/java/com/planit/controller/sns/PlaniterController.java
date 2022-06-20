package com.planit.controller.sns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.FollowDTO;
import com.planit.domain.sns.PlantsCateDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.mapper.sns.SnsMapper;
import com.planit.service.sns.snsService;

@Controller
@RequestMapping(value = "/planiter")
public class PlaniterController {

	@Autowired
	private snsService snsservice;
	
	@Autowired
	private SnsMapper snsmapper;
	 
	
	@GetMapping(value = "/")
	public String mainSnsPostList(Model model) {
		PostDTO postDto = new PostDTO();
		String USERID= "kosta";
		//좋아요 순 이미지 출력
		//List<PostDTO> ImgPost = snsservice.selectImgPost();
		//model.addAttribute("ImgPost", ImgPost);
		
		//계정 정보 출력 = 팔로워,팔로잉,프로필사진,식물카테고리
		List<AccountDTO> AccInfo = snsservice.selectMainAccINfo();
		model.addAttribute("AccInfo", AccInfo);
		List<PlantsCateDTO> CateList = snsservice.selectMainCate();
		model.addAttribute("CateList",CateList);
		int followerCount = snsmapper.followerCount(USERID);
		model.addAttribute("followerCount",followerCount);
		int followingCount = snsmapper.followingCount(USERID);
		model.addAttribute("followingCount",followingCount);
		
		
		return "sns/index";
	}

	//팔로워 팔로잉 페이지
	@RequestMapping(value = "/follower.do")
	public ModelAndView selectFollow(Model model,@RequestParam(value = "fol") String fol, ModelAndView mav) {
		
		String USERID="kosta";
		//계정 정보 출력 = 팔로워,팔로잉,프로필사진,식물카테고리
		List<AccountDTO> AccInfo = snsservice.selectMainAccINfo();
		model.addAttribute("AccInfo", AccInfo);
		List<PlantsCateDTO> CateList = snsservice.selectMainCate();
		model.addAttribute("CateList",CateList);
		int followerCount = snsmapper.followerCount(USERID);
		model.addAttribute("followerCount",followerCount);
		int followingCount = snsmapper.followingCount(USERID);
		model.addAttribute("followingCount",followingCount);
		//팔로워페이지 이동인지 팔로잉페이지 이동인지 체크
		model.addAttribute("fol",fol);
		
		 
		
		mav.setViewName("sns/followList");
		return mav;
	}
 
}
