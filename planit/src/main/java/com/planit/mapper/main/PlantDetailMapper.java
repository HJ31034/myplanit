package com.planit.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.planit.domain.main.PlantKeywordDTO;
import com.planit.domain.main.PlantsDTO;
import com.planit.domain.main.SolutionDTO;
import com.planit.domain.sns.UserToPlantsDTO;

@Mapper
public interface PlantDetailMapper {
	
	//plants Detail
		public List<PlantsDTO> plantDetail(int plantsId);
		public List<PlantsDTO> plantDes(int plantsId);
		public List<PlantsDTO> plantImgs(int plantsId);
		public int ImgsCnt(int plantsId);
		public void addPlant(UserToPlantsDTO params);
		public int userPlantCheck(@Param("userId")String userId,@Param("plantId") int plantId);
		public List<PlantKeywordDTO> PlantKeyword(int plantsId);
		public List<SolutionDTO> selectSolution(int plantsId);
		public List<UserToPlantsDTO> detail_User(int plantsId);
		public List<UserToPlantsDTO> random_User();
}
