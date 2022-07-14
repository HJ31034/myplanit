package com.planit.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.planit.domain.main.PlantKeywordDTO;
import com.planit.domain.main.PlantsDTO;
import com.planit.domain.main.SolutionDTO;
import com.planit.domain.sns.UserToPlantsDTO;

@Mapper
public interface SearchMapper {
	public List<PlantsDTO> selectPlants(String term);
	public List<PlantsDTO> selectKwdSearch(int kwdId);
 }
