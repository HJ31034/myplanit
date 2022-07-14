package com.planit.controller.main;

import java.util.List;

 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.planit.domain.main.PlantKeywordDTO;
import com.planit.domain.main.PlantsDTO;
import com.planit.domain.main.SolutionDTO;
import com.planit.domain.sns.FollowDTO;
import com.planit.domain.sns.UserToPlantsDTO;
import com.planit.service.main.MainService;
import com.planit.service.main.SearchService;
import com.planit.service.sns.snsService;
 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.planit.domain.main.PlantsDTO;
import com.planit.service.main.MainService;
import com.planit.service.main.SearchService;
 

@Controller
@RequestMapping("/planit")
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private MainService mainService;
	
	@GetMapping("/search")
	public String search(@RequestParam(value = "term", required = false) String term, Model model) {
		List<PlantsDTO> plantList;
		if (term != null) {
			plantList = searchService.selectPlants(term);
			model.addAttribute("term", term);
			System.out.println(term);
		}
		else {
			plantList = searchService.selectPlants("all");
		}
		
		model.addAttribute("plantKwdList", mainService.selectPlantKeyword(0));
		model.addAttribute("plantList", plantList);
		return "main/search";
	}
	
	@PostMapping("/search/kwd")
	public String kwdSearch(@RequestParam("keyId") int keyId, Model model) {
		
		List<PlantsDTO> plantList = searchService.selectKwdSearch(keyId);
		model.addAttribute("plantList", plantList);
		
		return "main/search";
	}
 
	
	
	
	
	
 
}
