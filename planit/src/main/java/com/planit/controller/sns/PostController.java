package com.planit.controller.sns;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.planit.domain.main.UserDTO;
import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.CommentDTO;
import com.planit.domain.sns.FilesDTO;
import com.planit.domain.sns.LikesDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.domain.sns.PostDetailDTO;
import com.planit.domain.sns.UserToPlantsDTO;
import com.planit.service.sns.PostService;
import com.planit.service.sns.ProfileService;

@Controller
@RequestMapping(value = "/planiter/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private ProfileService profileService;
	
	@GetMapping(value = "/write")
	public String writePost (Model model, HttpServletRequest request) {
		String returnUrl = "sns/write-post";
		
		HttpSession session = null;
		UserDTO userDto = null;
		String userId = "";

		try{
			session  = request.getSession();
			userDto = (UserDTO) session.getAttribute("userdto");
			
			userId = userDto.getUserId();
			
			List<UserToPlantsDTO> userToPlantList = postService.selectPlantsCate(userId);
			
			model.addAttribute("userToPlantList", userToPlantList);
			
		}catch(Exception e){
			returnUrl = "sns/session-error";
		}
		
		return returnUrl;
	}
	
	@PostMapping(value = "/write")
	public String writePost (@ModelAttribute("params") PostDetailDTO params,  Model model) {
		
		postService.insertPost(params);
		
		return "redirect:/planiter/profile.do";
//		return "sns/write-post";
	}
	
	@GetMapping(value = "/modify")
	public String modifyPost (@RequestParam(value = "postno") Long postNo, Model model, HttpServletRequest request) {
		HttpSession session  = request.getSession();
		UserDTO userDto = (UserDTO) session.getAttribute("userdto");
		
		String userId = userDto.getUserId();
		
		PostDTO post = postService.getBoardDetail(postNo);
		List<UserToPlantsDTO> userToPlantList = postService.selectPlantsCate(userId);
		List<FilesDTO> fileList = postService.getFiles(postNo);  
		
		model.addAttribute("post", post);
		model.addAttribute("userToPlantList", userToPlantList);
		model.addAttribute("files", fileList);
		
		return "sns/modify-post";
	}
	
	@PostMapping(value = "/modify")
	public String modifyPost (@ModelAttribute("params") PostDetailDTO params,  Model model) {
		postService.modify(params);
		
		return "redirect:/planiter/profile.do";
//		return "sns/modify-post";
	}
	
	@GetMapping(value = "/delete")
	public String postDelete (@RequestParam(value = "postno") Long postNo, Model model) {
		
		postService.deleteYN(postNo);
		
		return "redirect:/planiter/profile.do";	
	}
	
	@GetMapping(value = "/read")
	public String readPost (@RequestParam(value = "postno") Long postNo, Model model, HttpServletRequest request) {
		
		String returnUrl = "sns/read-post";
		
		HttpSession session = null;
		UserDTO userDto = null;
		String userId = "";

		try{
			session  = request.getSession();
			userDto = (UserDTO) session.getAttribute("userdto");
			
			userId = userDto.getUserId();
			
			LikesDTO likeDto = new LikesDTO();
			
			likeDto.setPostNo(postNo);
			likeDto.setUserId(userId);
			
			PostDTO post = postService.getBoardDetail(postNo);
			List<CommentDTO> commentList = postService.getCommentDetail(postNo);
			String isLike = postService.getLikes(likeDto);
			AccountDTO accountProfile = profileService.selectUserProfile(post.getUserId());
			
			model.addAttribute("post", post);
			model.addAttribute("commentList", commentList);
			model.addAttribute("isLike", isLike);
			model.addAttribute("accountProfile", accountProfile);
			
		}catch(Exception e){
			returnUrl = "sns/session-error";
		}
		
		return returnUrl;
	}

	@GetMapping(value = "/refresh/comment")
	public String refreshComment (@RequestParam(value = "postNo")Long postNo, Model model) {
		List<CommentDTO> commentList = null;

		try {
			commentList = postService.getCommentDetail(postNo);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		model.addAttribute("commentList", commentList);

		return "sns/read-post :: #comment_table_second";
	}
	
	@PostMapping(value = "/insert/comment")
	public String insertComment (Model model, CommentDTO params, HttpServletRequest request) {
		HttpSession session  = request.getSession();
		UserDTO userDto = (UserDTO) session.getAttribute("userdto");
		String userId = userDto.getUserId();
		
		try {
			params.setUserId(userId);
			postService.insertComment(params);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		model.addAttribute("commentList", postService.getCommentDetail(params.getPostNo()));
		
		 return "sns/read-post :: #comment_table";		
	}

	@GetMapping(value = "/delete/comment")
	public String deleteComment (Model model, CommentDTO params, HttpServletRequest request) {
		HttpSession session  = request.getSession();
		UserDTO userDto = (UserDTO) session.getAttribute("userdto");
		String userId = userDto.getUserId();
		
		try {
			params.setUserId(userId);
			postService.deleteComment(params);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		model.addAttribute("commentList", postService.getCommentDetail(params.getPostNo()));

		return "sns/read-post :: #comment_table";
	}
	
	@PostMapping(value = "/like")
	public String Likes (LikesDTO params, @RequestParam(value = "isLike") String isLike, Model model, HttpServletRequest request) {
		HttpSession session  = request.getSession();
		UserDTO userDto = (UserDTO) session.getAttribute("userdto");
		
		String userId = userDto.getUserId();
		
		String result = null;
		params.setUserId(userId);
		
		try {
			postService.likeControl(params, isLike);
			result = postService.getLikes(params);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		model.addAttribute("isLike", result);
		
		return "sns/read-post :: #icon_box";		
	}
	
	@GetMapping(value = "/popup/map")
	public String openKakaoMap (@RequestParam(value = "latitude") Double latitude, @RequestParam(value = "longitude") Double longitude, Model model) {
		model.addAttribute("latitude", latitude);
		model.addAttribute("longitude", longitude);

		return "sns/popup/kakaoMap";
	}
	
	

}
