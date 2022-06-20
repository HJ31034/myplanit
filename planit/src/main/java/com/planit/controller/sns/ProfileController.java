package com.planit.controller.sns;

import java.util.ArrayList;
import java.util.List;

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
	public String myProfile(Model model) {
		// ����� ���� ������ ���� ���� �����ͼ� �Ѱ��ֱ�
		String id = "test";
		String page = "main";
		
		// ����� ������
		model.addAttribute("myUserProfile", profileService.selectUserProfile(id));
		
		// ������� �ݷ��Ĺ� �̸� ����Ʈ
		List<UserToPlantsDTO> userPlantList = profileService.selectUserPlants(id);
		model.addAttribute("myUserPlants", userPlantList);
		
		// ������ �̸��� ���� ����Ʈ
		List<List<PostDTO>> userPostList = new ArrayList<>();
		int count = 0;
		for(int i = 0; i < userPlantList.size(); i++) {
			count += profileService.selectUserPost(new UserToPlantsDTO(userPlantList.get(i).getPlantsName(), id), "count").size();
			userPostList.add(profileService.selectUserPost(new UserToPlantsDTO(userPlantList.get(i).getPlantsName(), id), page));
		}
		model.addAttribute("myUserPosts", userPostList);
		
		// �Խù� ����
		model.addAttribute("myPostCount", count);
		
		List<PostDTO> userLikes = profileService.selectUserLikes(id);

		// ���ƿ� �Խù� ����
		model.addAttribute("userLikes", userLikes.size());
		
		return "sns/profile";
	}
	
	@ResponseBody // ajax�� �� ������ ���!!!
	@PostMapping("/plantPosts")
	public List<PostDTO> profile(@ModelAttribute UserToPlantsDTO params) {
		return profileService.selectUserPost(params, "detail");
	}
	
	@ResponseBody
	@PostMapping("/userLikes")
	public List<PostDTO> userLikes(@RequestParam("userId") String id){
		return profileService.selectUserLikes(id);
	}
	
	// �ٸ� ��� ����
	@GetMapping("/{id}")
	public String userProfile(@PathVariable String id, Model model) {
		String page = "main";
		
		// ����� ������
		model.addAttribute("userProfile", profileService.selectUserProfile(id));
		
		// ������� �ݷ��Ĺ� �̸� ����Ʈ
		List<UserToPlantsDTO> userPlantList = profileService.selectUserPlants(id);
		model.addAttribute("userPlants", userPlantList);
		
		// ������ �̸��� ���� ����Ʈ
		List<List<PostDTO>> userPostList = new ArrayList<>();
		int count = 0;
		for(int i = 0; i < userPlantList.size(); i++) {
			count += profileService.selectUserPost(new UserToPlantsDTO(userPlantList.get(i).getPlantsName(), id), "count").size();
			userPostList.add(profileService.selectUserPost(new UserToPlantsDTO(userPlantList.get(i).getPlantsName(), id), page));
		}
		model.addAttribute("userPosts", userPostList);
		
		// �Խù� ����
		model.addAttribute("postCount", count);
		
		List<PostDTO> userLikes = profileService.selectUserLikes(id);
		
		// ���ƿ� �Խù� ����
		model.addAttribute("userLikes", userLikes.size());
		
		FollowDTO follow = new FollowDTO();
		
		// ���� ����
		String myId = "test";
		
		follow.setUserId(myId);
		follow.setFollowingId(id);
		
		// �ٸ� ������� �ȷο� ����
		model.addAttribute("followCheck", profileService.selectFollow(follow));

		return "sns/otherProfile";
	}
	
	@ResponseBody
	@PostMapping("/follow")
	public AccountDTO insertFollow(@ModelAttribute FollowDTO follow) {
		profileService.insertFollow(follow);
		profileService.updateFollowCount(follow.getUserId(), "following");
		profileService.updateFollowCount(follow.getFollowingId(), "follower");
		return profileService.selectUserProfile(follow.getFollowingId());
	}
	
	@ResponseBody
	@PostMapping("/unfollow")
	public AccountDTO deleteFollow(@ModelAttribute FollowDTO follow) {
		profileService.deleteFollow(follow);
		profileService.updateFollowCount(follow.getUserId(), "unfollowing");
		profileService.updateFollowCount(follow.getFollowingId(), "unfollower");
		return profileService.selectUserProfile(follow.getFollowingId());
	}
}
