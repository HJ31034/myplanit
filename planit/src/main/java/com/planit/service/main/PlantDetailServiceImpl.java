package com.planit.service.main;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planit.domain.main.PlantKeywordDTO;
import com.planit.domain.main.PlantsDTO;
import com.planit.domain.main.SolutionDTO;
import com.planit.domain.sns.UserToPlantsDTO;
import com.planit.mapper.main.PlantDetailMapper;


@Service
public class PlantDetailServiceImpl implements PlantDetailService {
	
	@Autowired
	private PlantDetailMapper detailMapper;
	

	public List<PlantsDTO> plantDetail(int plantsId){
		List<PlantsDTO> detailList=detailMapper.plantDetail(plantsId);
		return detailList;
	}
	public List<PlantsDTO> plantDes(int plantsId){
		List<PlantsDTO> plantDes=detailMapper.plantDes(plantsId);
		return plantDes;
	}
	public List<PlantsDTO> plantImgs(int plantsId){
		List<PlantsDTO> plantImgs=detailMapper.plantImgs(plantsId);
		return plantImgs;
	}
	public int ImgsCnt(int plantsId) {
		return detailMapper.ImgsCnt(plantsId);
	}
	public void addPlant(UserToPlantsDTO params){
		System.out.println("SearchServiceImpl addPlant: "+params);
		detailMapper.addPlant(params);
	}
	public int userPlantCheck(@Param("userId")String userId,@Param("plantId") int plantId) {  
		return detailMapper.userPlantCheck(userId, plantId);
	}
	public List<PlantKeywordDTO> PlantKeyword(int plantsId) { 
		return detailMapper.PlantKeyword(plantsId);
	}
	public List<SolutionDTO> selectSolution(int plantsId){ 
		return detailMapper.selectSolution(plantsId);
	}
 
	public List<UserToPlantsDTO> detail_User(int plantsId){ 
		return detailMapper.detail_User(plantsId);
	}
	
	public List<UserToPlantsDTO> random_User(){
		return detailMapper.random_User();
	}

}
