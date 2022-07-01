package com.planit.controller.sns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.planit.domain.sns.CommentDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.domain.sns.PostDetailDTO;
import com.planit.domain.sns.UserToPlantsDTO;
import com.planit.service.sns.PostService;

@Controller
@RequestMapping(value = "/planiter/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@GetMapping(value = "/write")
	public String writePost (Model model) {
		//session으로 변경할 것 
		String userId = "test";
		List<UserToPlantsDTO> userToPlantList = postService.selectPlantsCate(userId);
		
		model.addAttribute("userToPlantList", userToPlantList);
		
		return "sns/write-post";
	}
	
	@PostMapping(value = "/write")
	public String writePost (@ModelAttribute("params") PostDetailDTO params, Model model) {
		
		postService.insertPost(params);
		
		
		return "sns/write-post";
	}
	
	@GetMapping(value = "/read")
	public String readPost (@RequestParam(value = "postno") Long postNo, Model model) {
		
		PostDTO post = postService.getBoardDetail(postNo);
		
		model.addAttribute("post", post);
		
		List<CommentDTO> commentList = postService.getCommentDetail(postNo);
		
		model.addAttribute("commentList", commentList);
		
		return "sns/read-post";
	}
	
	@PostMapping(value = "/insert/comment")
	public String readPost (Model model, CommentDTO params) {
		try {
			postService.insertComment(params);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		model.addAttribute("commentList", postService.getCommentDetail(params.getPostNo()));
		
		 return "sns/read-post :: #comment_table";		
	}
	
	@GetMapping(value = "/popup/map")
	public String openKakaoMap (@RequestParam(value = "latitude") Double latitude, @RequestParam(value = "longitude") Double longitude, Model model) {
		model.addAttribute("latitude", latitude);
		model.addAttribute("longitude", longitude);

		return "sns/popup/kakaoMap";
	}
	
	

}
