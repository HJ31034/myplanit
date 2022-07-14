package com.planit.controller.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.planit.domain.main.PlantKeywordDTO;
import com.planit.domain.main.PlantsDTO;
import com.planit.domain.main.SolutionDTO;
import com.planit.domain.sns.UserToPlantsDTO;
import com.planit.service.main.PlantDetailService;

@Controller
@RequestMapping("/planit")
public class PlantDetailController {

	@Autowired
	private PlantDetailService detailService;
	
	/* plants_detail */
	@RequestMapping(value = "/{plantsId}")
	public String plantDetail(Model model, HttpServletRequest request,@PathVariable int plantsId,HttpSession session) {
		 String USERID = "";
		if (session.getAttribute("id") != null) {
			USERID = session.getAttribute("id").toString();
		}
		 
		 model.addAttribute("USERID", USERID);
		 model.addAttribute("plantsId", plantsId);
		 List<PlantsDTO> plantDetail = detailService.plantDetail(plantsId);
		 model.addAttribute("plantDetail", plantDetail);
		 List<PlantsDTO> plantImgs = detailService.plantImgs(plantsId);
	     model.addAttribute("plantImgs", plantImgs);
		 int imgsCnt=detailService.ImgsCnt(plantsId);
		 model.addAttribute("imgsCnt", imgsCnt);
		 List<PlantKeywordDTO> PlantKeyword = detailService.PlantKeyword(plantsId);
		 model.addAttribute("PlantKeyword", PlantKeyword);
		 List<SolutionDTO> solution = detailService.selectSolution(plantsId);
		 model.addAttribute("solution", solution);
		return "main/plant_detail";
	}
	
	//식물 상세 설명 출력
	@ResponseBody
	@RequestMapping(value = "/plantDes")
	public List<PlantsDTO> plantDes(Model model, @RequestParam(value="plantsId") int plantsId) {
		return detailService.plantDes(plantsId);	
	}
	
	//나만의 식물 추가
	@ResponseBody
	@RequestMapping(value = "/addPlant")
	public int addPlant(Model model, @RequestParam(value="plantsId") int plantsId,
									    @RequestParam(value="userId") String userId,
									    @RequestParam(value="plantsName") String plantsName) {
		System.out.println("userIdplantsId: "+userId+" plantsId: "+plantsId);
        int check = detailService.userPlantCheck(userId, plantsId);
        
        if(check==0) {
        	UserToPlantsDTO utp = new UserToPlantsDTO(plantsName, plantsId, userId);
        	detailService.addPlant(utp);        	
        }
        
		return check;
	}
 

}
