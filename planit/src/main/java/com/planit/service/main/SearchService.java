package com.planit.service.main;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.planit.domain.main.PlantKeywordDTO;
import com.planit.domain.main.PlantsDTO;
import com.planit.domain.main.SolutionDTO;
import com.planit.domain.sns.UserToPlantsDTO;
 
 
public interface SearchService {
	public List<PlantsDTO> selectPlants(String term, int keyId, int start, int end);
	public List<PlantsDTO> selectKwdSearch(int kwdId, int start, int end);
	public int plantsTotalCount(String term, int keyId);
 
	/*	plants Detail */
	public List<PlantsDTO> plantDetail(int plantsId);
	public List<PlantsDTO> plantDes(int plantsId);
	public List<PlantsDTO> plantImgs(int plantsId);
	public int ImgsCnt(int plantsId);
 
}
