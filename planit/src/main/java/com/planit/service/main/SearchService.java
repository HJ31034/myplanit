package com.planit.service.main;

import java.util.List;

import com.planit.domain.main.PlantsDTO;
 

public interface SearchService {
	public List<PlantsDTO> selectPlants(String term);
	public List<PlantsDTO> selectKwdSearch(int kwdId);
	
	/*	plants Detail */
	public List<PlantsDTO> plantDetail(int plantsId);
	public List<PlantsDTO> plantDes(int plantsId);
	public List<PlantsDTO> plantImgs(int plantsId);
	public int ImgsCnt(int plantsId);

}
