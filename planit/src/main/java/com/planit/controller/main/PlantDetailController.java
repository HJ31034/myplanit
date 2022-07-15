package com.planit.controller.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

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
import com.planit.mapper.main.MainMapper;
import com.planit.service.main.PlantDetailService;

@Controller
@RequestMapping("/planit")
public class PlantDetailController {

	@Autowired
	private PlantDetailService detailService;
 
	
	/* plants_detail */
	@RequestMapping(value = "/plantsDetail")
	public String plantDetail(Model model, HttpServletRequest request,@RequestParam int plantsId,
			@RequestParam("plantsName") String plantsName,  HttpSession session) {
		
		 String USERID = "";
		if (session.getAttribute("id") != null) {
			USERID = session.getAttribute("id").toString();
		}
	 
		 model.addAttribute("USERID", USERID);
		 model.addAttribute("plantsId", plantsId); 
		  
		 model.addAttribute("plantDetail", detailService.plantDetail(plantsId));
	     model.addAttribute("plantImgs",detailService.plantImgs(plantsId));
		 model.addAttribute("imgsCnt", detailService.ImgsCnt(plantsId));
		 model.addAttribute("PlantKeyword", detailService.PlantKeyword(plantsId));
		
		 model.addAttribute("solution",  detailService.selectSolution(plantsId));
		 List<UserToPlantsDTO> plantUser = detailService.detail_User(plantsId);
		 String resultText=null;
		 if(plantUser.size()==0) {
			 model.addAttribute("userList", detailService.random_User());
			 resultText="플래니터 둘러보기";
			 model.addAttribute("plantsName",resultText);
		 }else if(plantUser.size()!=0) {
			 model.addAttribute("userList", plantUser);
			  resultText="을(를) 키우는 플래니터";
			 model.addAttribute("plantsName", plantsName+resultText);
		 }
		 
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
		//System.out.println("userIdplantsId: "+userId+" plantsId: "+plantsId+" plantsName: "+plantsName);
        int check = detailService.userPlantCheck(userId, plantsId);
        
        if(check==0) {
        	UserToPlantsDTO utp = new UserToPlantsDTO(plantsName, plantsId, userId);
        	detailService.addPlant(utp);        	
        }
        
		return check;
	}
 
 
}
