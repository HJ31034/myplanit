package com.planit.service.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planit.domain.main.PlantsDTO;
import com.planit.mapper.main.SearchMapper;

@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchMapper searchMapper;

	@Override
	public List<PlantsDTO> selectPlants(String term) {
		return searchMapper.selectPlants('%' + term + '%');
	}

	@Override
	public List<PlantsDTO> selectKwdSearch(int kwdId) {
		return searchMapper.selectKwdSearch(kwdId);
	}

	
	
	
	/*	plants Detail */
	public List<PlantsDTO> plantDetail(int plantsId){
		List<PlantsDTO> detailList=searchMapper.plantDetail(plantsId);
		return detailList;
	}
	public List<PlantsDTO> plantDes(int plantsId){
		List<PlantsDTO> plantDes=searchMapper.plantDes(plantsId);
		return plantDes;
	}
	public List<PlantsDTO> plantImgs(int plantsId){
		List<PlantsDTO> plantImgs=searchMapper.plantImgs(plantsId);
		return plantImgs;
	}
}
