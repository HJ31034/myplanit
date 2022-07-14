package com.planit.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.planit.domain.main.PlantsDTO;

@Mapper
public interface SearchMapper {
	public List<PlantsDTO> selectPlants(@Param("term") String term,
										@Param("keyId") int keyId, 
										@Param("start") int start, 
										@Param("end") int end);
	public List<PlantsDTO> selectKwdSearch(@Param("keyId") int keyId, @Param("start") int start, @Param("end") int end);
	public int plantsTotalCount(@Param("term") String term, @Param("keyId") int keyId);
 
	
	 
	//plants Detail
	public List<PlantsDTO> plantDetail(int plantsId);
	public List<PlantsDTO> plantDes(int plantsId);
	public List<PlantsDTO> plantImgs(int plantsId);
	public int ImgsCnt(int plantsId);
	
 
}
