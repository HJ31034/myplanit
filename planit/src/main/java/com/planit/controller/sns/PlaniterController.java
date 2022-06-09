package com.planit.controller.sns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.planit.domain.sns.PostDTO;
import com.planit.service.sns.snsService;

@Controller
@RequestMapping(value = "/planiter")
public class PlaniterController {

	@Autowired
	private snsService snsservice;
	 
	 
	
	@GetMapping(value = "/")
	public String mainSnsPostList(Model model) {
		PostDTO postDto = new PostDTO();
		List<PostDTO> ImgPost = snsservice.selectImgPost();
		
		 System.out.println(postDto.getFilename());
		 model.addAttribute("ImgPost", ImgPost);
		
		return "index";
	}

	@GetMapping(value = "/follower.do")
	public String selectFollow(Model model) {
		/*
		 * List<postVO> postList = snsservice.selectPostList();
		 * model.addAttribute("postList", postList);
		 */

		return "followList";
	}

	@GetMapping(value = "/goProfileEdit.do")
	public String goProfileEdit(Model model) {
		/*
		 * List<postVO> postList = snsservice.selectPostList();
		 * model.addAttribute("postList", postList);
		 */

		return "profile_edit";
	}

}
