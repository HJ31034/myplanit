package com.planit.controller.sns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.planit.domain.sns.CommentDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.service.sns.PostService;

@Controller
@RequestMapping(value = "/planiter/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@GetMapping(value = "/write")
	public String writePost (Model model) {
		
		

		return "sns/write-post";
	}
	
	@GetMapping(value = "/read")
	public String readPost (@ModelAttribute("params") PostDTO params, @RequestParam(value = "postno") Long postNo, Model model) {
		
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
		
		 return "sns/insert/comment";		
	}
	
	@GetMapping(value = "/popup/map")
	public String openKakaoMap (@RequestParam(value = "latitude") Double latitude, @RequestParam(value = "longitude") Double longitude, Model model) {
		model.addAttribute("latitude", latitude);
		model.addAttribute("longitude", longitude);

		return "sns/popup/kakaoMap";
	}
	
	

}
