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
	public List<PlantsDTO> selectPlants(String term, int keyId, int start, int end) {
		return searchMapper.selectPlants('%' + term + '%', keyId, start, end);
	}

	@Override
	public List<PlantsDTO> selectKwdSearch(int kwdId, int start, int end) {
		return searchMapper.selectKwdSearch(kwdId, start, end);
	}
	
	@Override
	public int plantsTotalCount(String term, int keyId) {
		return searchMapper.plantsTotalCount('%' + term + '%', keyId);
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
	public int ImgsCnt(int plantsId) {
		return searchMapper.ImgsCnt(plantsId);
	}

	
 
}
