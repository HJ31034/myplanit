package com.planit.controller.sns;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.FollowDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.domain.sns.UserToPlantsDTO;
import com.planit.service.sns.ProfileService;

@Controller
@RequestMapping(value = "/planiter")
public class ProfileController {

	@Autowired
	private ProfileService profileService;
	
	@GetMapping("/profile.do")
	public String myProfile(Model model, HttpServletRequest request) {
		
		HttpSession session  = request.getSession();
		
//		String id = session.getAttribute("id").toString();
		String id = "kosta";
		String page = "main";
		
		// 사용자 프로필 정보
		model.addAttribute("myUserProfile", profileService.selectUserProfile(id));
		
		// 사용자가 키우는 반려식물의 리스트
		
		 
		List<UserToPlantsDTO> userPlantList = profileService.selectUserPlants(id);
		model.addAttribute("myUserPlants", userPlantList);
		
		// 사용자가 게시한 게시물 리스트
		List<List<PostDTO>> userPostList = new ArrayList<>();
		int count = 0;
		for(int i = 0; i < userPlantList.size(); i++) {
			count += profileService.selectUserPost(new UserToPlantsDTO(userPlantList.get(i).getPlantsName(), id), "count").size();
			userPostList.add(profileService.selectUserPost(new UserToPlantsDTO(userPlantList.get(i).getPlantsName(), id), page));
		}
		model.addAttribute("myUserPosts", userPostList);
		
		// 사용자의 게시물 총 개수
		model.addAttribute("myPostCount", count);
		
		List<PostDTO> userLikes = profileService.selectUserLikes(id);

		// 사용자가 좋아요를 누른 게시물의 개수
		model.addAttribute("userLikes", userLikes.size());
		
		return "sns/profile";
	}
	
	// 사용자 게시물에서 식물 이름 해시태그를 누르면 해당 식물에 대한 게시물 전체를 보여줌
	@ResponseBody
	@PostMapping("/plantPosts")
	public List<PostDTO> profile(@ModelAttribute UserToPlantsDTO params) {
		return profileService.selectUserPost(params, "detail");
	}
	
	// 사용자가 좋아요를 누른 게시물 리스트
	@ResponseBody
	@PostMapping("/userLikes")
	public List<PostDTO> userLikes(@RequestParam("userId") String id){
		return profileService.selectUserLikes(id);
	}
	
	// 다른 사용자의 계정 프로필
	@GetMapping("/{id}")
	public String userProfile(@PathVariable String id, Model model, HttpServletRequest request) {
		String page = "main";
		
		model.addAttribute("userProfile", profileService.selectUserProfile(id));
		
		List<UserToPlantsDTO> userPlantList = profileService.selectUserPlants(id);
		model.addAttribute("userPlants", userPlantList);
		
		List<List<PostDTO>> userPostList = new ArrayList<>();
		int count = 0;
		for(int i = 0; i < userPlantList.size(); i++) {
			count += profileService.selectUserPost(new UserToPlantsDTO(userPlantList.get(i).getPlantsName(), id), "count").size();
			userPostList.add(profileService.selectUserPost(new UserToPlantsDTO(userPlantList.get(i).getPlantsName(), id), page));
		}
		model.addAttribute("userPosts", userPostList);
		
		model.addAttribute("postCount", count);
		
		List<PostDTO> userLikes = profileService.selectUserLikes(id);
		
		model.addAttribute("userLikes", userLikes.size());
		
		FollowDTO follow = new FollowDTO();
		
		HttpSession session  = request.getSession();
		
//		String myId = session.getAttribute("id").toString();
		String myId = "kosta";
		
		follow.setUserId(myId);
		follow.setFollowingId(id);
		
		model.addAttribute("followCheck", profileService.selectFollow(follow));

		return "sns/otherProfile";
	}
	
	// 팔로우하기
	@ResponseBody
	@PostMapping("/follow")
	public AccountDTO insertFollow(@ModelAttribute FollowDTO follow) {
		profileService.insertFollow(follow);
		profileService.updateFollowCount(follow.getUserId(), "following");
		profileService.updateFollowCount(follow.getFollowingId(), "follower");
		return profileService.selectUserProfile(follow.getFollowingId());
	}
	
	// 팔로우 취소하기
	@ResponseBody
	@PostMapping("/unfollow")
	public AccountDTO deleteFollow(@ModelAttribute FollowDTO follow) {
		profileService.deleteFollow(follow);
		profileService.updateFollowCount(follow.getUserId(), "unfollowing");
		profileService.updateFollowCount(follow.getFollowingId(), "unfollower");
		return profileService.selectUserProfile(follow.getFollowingId());
	}
}
