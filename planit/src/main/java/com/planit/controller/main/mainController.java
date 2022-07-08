package com.planit.controller.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.planit.domain.main.PlantKeywordDTO;
import com.planit.service.main.MainService;

@Controller
public class MainController {
	@Autowired
	private MainService mainService;

	@GetMapping(value = "/planit")
	public String main(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String id = null;
		if (session.getAttribute("id") != null)
			id = session.getAttribute("id").toString();
		
		model.addAttribute("plantKwdList", mainService.selectPlantKeyword(0));
		model.addAttribute("users", mainService.selectUsers(id));
		model.addAttribute("postList", mainService.selectPost());
		return "main/index";
	}

	@ResponseBody
	@PostMapping(value = "/kwd")
	public List<PlantKeywordDTO> kwd(@RequestParam("keyId") int keyId) {
		return mainService.selectPlantKeyword(keyId);
	}
	
	

	 
	
}
