package com.planit.controller.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
 
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
	
	
	
	
	//detail
	@RequestMapping(value = "/산세베리아")
	public String plantDetail(Model model, HttpServletRequest request) {
		int plantsId=1;
		 List<PlantsDTO> plantDetail = searchService.plantDetail(plantsId);
		 model.addAttribute("plantDetail", plantDetail);
		 
		 List<PlantsDTO> plantDes = searchService.plantDes(plantsId);	 
		 model.addAttribute("plantDes", plantDes);
 
		 
		 List<PlantsDTO> plantImgs = searchService.plantImgs(plantsId);
		 for(int i=0; i<=plantImgs.size(); i++) {
			 System.out.println("imgs" + i);
		 }
		 System.out.println("plantImgs"+ plantImgs );
		// model.addAttribute("plantImgs", plantImgs);
		 
		 
		return "main/plant_detail";
	}
	
	//식물 상세정보 출력
	@ResponseBody
	@RequestMapping(value = "/plantDes")
	public List<PlantsDTO> plantDes(Model model, HttpServletRequest request) {
		int plantsId=1;
		return searchService.plantDes(plantsId);	
	}
	//식물 이미지 출력
	 
//	@RequestMapping(value = "/plantDes")
//	public List<PlantsDTO> plantImgs(Model model, HttpServletRequest request) {
//		int plantsId=1;
//		 List<PlantsDTO> plantImgs = searchService.plantImgs(plantsId);
//		 for(int i=0; i<=plantImgs.size(); i++) {
//			 System.out.println(i);
//		 }
//		 model.addAttribute("plantImgs", plantImgs);
//		return searchService.plantImgs(plantsId);	
//	}
	
	
}
