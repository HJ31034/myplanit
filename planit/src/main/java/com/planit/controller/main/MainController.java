package com.planit.controller.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.planit.domain.main.PlantKeywordDTO;
import com.planit.domain.sns.AccountDTO;
import com.planit.service.main.MainService;
import com.planit.util.CSVReader;

@Controller
public class MainController {
	@Autowired
	private MainService mainService;
	
	public List<AccountDTO> recommendUser(String id){
		List<AccountDTO> userList = new ArrayList<>();
		
		if(id == null)
			return mainService.selectUsers(new ArrayList<String>());
		
		long userNo = -1;
		
		CSVReader csvReader = new CSVReader();
        List<List<String>> a = csvReader.readCSV();
        
        for(int i = 0; i < a.size(); i++) {
        	if(id.equals(a.get(i).get(1))){
//        		System.out.println(a.get(i));
        		userNo = Long.parseLong(a.get(i).get(0));
        	}
        }
        
		try {
			List<String> userIdList = new ArrayList<>();
			DataModel dm = new FileDataModel(new File("data/recommendUsers.csv"));
			TanimotoCoefficientSimilarity sim = new TanimotoCoefficientSimilarity(dm);
			GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dm, sim);

			List<RecommendedItem> recommendations = recommender.mostSimilarItems(userNo, 6);
			for (RecommendedItem recommendation : recommendations) {
//				System.out.println(userNo + "," + recommendation.getItemID() + "," + recommendation.getValue());
				for(int i = 0; i < a.size(); i++) {
				    if(recommendation.getItemID() == Long.parseLong(a.get(i).get(0))){
//				    	System.out.println(a.get(i).get(1));
				    	userIdList.add(a.get(i).get(1));
				    }
				}
			}
			userList = mainService.selectUsers(userIdList);
			if(userList.size() < 6) { // 6명 보다 작으면 랜덤 사용자 추천
				userIdList.add(id);
				List<AccountDTO> temp = mainService.selectUsers2(userIdList, 6 - userList.size());
				for(int i = 0; i < temp.size(); i++) {
					userList.add(temp.get(i));
				}
			}
//			System.out.println(userList);
		} catch (IOException e) {
			System.out.println("there was an error.");
			e.printStackTrace();
		} catch (TasteException e) {
			System.out.println("there was an Taste Exception.");
			e.printStackTrace();
		}
		
		return userList;
	}

	@GetMapping(value = "/planit")
	public String main(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		String id = null;
		if (session.getAttribute("id") != null)
			id = session.getAttribute("id").toString();
		 
		System.out.println("main id: "+id);
		
		model.addAttribute("plantKwdList", mainService.selectPlantKeyword(0));
//		model.addAttribute("users", mainService.selectUsers(id));
		model.addAttribute("users", recommendUser(id));
		model.addAttribute("postList", mainService.selectPost());
		model.addAttribute("recommendList", mainService.selectPlantRecommend(id));
		return "main/index";
	}

	@ResponseBody
	@PostMapping(value = "/kwd")
	public List<PlantKeywordDTO> kwd(@RequestParam("keyId") int keyId) {
		return mainService.selectPlantKeyword(keyId);
	}
	
	 
	
}
