package com.planit.controller.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


 

@Controller
@RequestMapping("/planit")
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private MainService mainService;
	
	
	@GetMapping("/search")
	public String search(@RequestParam(value = "term", required = false) String term, Model model) {
		int total = 0;

		List<PlantsDTO> plantList;
		if (term != null) {
			total = searchService.plantsTotalCount(term, -1);
			plantList = searchService.selectPlants(term, -1, 1, 16);
			model.addAttribute("term", term);
			System.out.println(term);
		}
		else {
			total = searchService.plantsTotalCount("all", -1);
			plantList = searchService.selectPlants("all", -1, 1, 16);
		}
		
		model.addAttribute("plantKwdList", mainService.selectPlantKeyword(0)); // 1차 필터 키워드
		model.addAttribute("plantList", plantList);
		model.addAttribute("total", total);
		model.addAttribute("keyId", -1);
		return "main/search";
	}
	
	@ResponseBody
	@PostMapping("/search")
	public Map<String, Object> plants(@RequestParam(value = "term", required = false, defaultValue = "all") String term,
			@RequestParam(value = "keyId", required = false, defaultValue = "-1") int keyId,
			@RequestParam(value = "page", required = false, defaultValue = "1") String page, Model model) {
		
		int pageNum=Integer.parseInt(page);
		
		int total = searchService.plantsTotalCount(term, keyId);
		
		int nowPage = pageNum;
		 int startNum=0;
		 int endNum=16;
		 int numPerPage=16; 
		 int totalPage = (int)Math.ceil((float)total/(float)numPerPage);
		 
		 System.out.println("totalPage: "+ totalPage);
		 if (pageNum==1){
			 
			 startNum = 1;
			 endNum = numPerPage ;
			 
		 }else{
			 startNum = (pageNum * numPerPage)-numPerPage + 1;
			 endNum = startNum + numPerPage - 1;
		} 

		List<PlantsDTO> plantList;

		plantList = searchService.selectPlants(term, keyId, startNum, endNum);		
		
		Map<String, Object> map = new HashMap<>();
		map.put("plantList", plantList);
		map.put("total", total);
//		map.put("term", term);
//		map.put("keyId", keyId);
		
		return map;
	}
	
	@PostMapping("/search/kwd")
	public String kwdSearch(@RequestParam("keyId") int keyId, 
							@RequestParam(value = "page", required = false, defaultValue = "1") String page, Model model) {
		model.addAttribute("plantKwdList", mainService.selectPlantKeyword(0)); // 1차 필터 키워드
		
		int pageNum=Integer.parseInt(page);
		
		int total = searchService.plantsTotalCount("all", keyId);
		
		
		int nowPage = pageNum;
		 int startNum=0;
		 int endNum=16;
		 int numPerPage=16; 
		 int totalPage = (int)Math.ceil((float)total/(float)numPerPage);
		 
		 System.out.println("totalPage: "+ totalPage);
		 if (pageNum==1){
			 
			 startNum = 1;
			 endNum = numPerPage ;
			 
		 }else{
			 startNum = (pageNum * numPerPage)-numPerPage + 1;
			 endNum = startNum + numPerPage - 1;
		}
		
		List<PlantsDTO> plantList = searchService.selectKwdSearch(keyId, startNum, endNum);
		model.addAttribute("plantList", plantList);
		model.addAttribute("total",total);
		model.addAttribute("keyId",keyId);
		
		return "main/search";
	}
		
	
	
 
}
