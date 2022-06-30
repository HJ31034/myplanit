package com.planit.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.planit.domain.main.PlantsDTO;

@Mapper
public interface SearchMapper {
	public List<PlantsDTO> selectPlants(String term);
	public List<PlantsDTO> selectKwdSearch(int kwdId);
}
