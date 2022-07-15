package com.planit.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.planit.domain.main.PlantKeywordDTO;
import com.planit.domain.main.PlantsDTO;
import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.PostDTO;

@Mapper
public interface MainMapper {
	public List<PlantKeywordDTO> selectPlantKeyword(int keyId);
	public List<AccountDTO> selectUsers(List<String> userId);
	public List<AccountDTO> selectUsers2(@Param("list") List<String> userId, @Param("count") int count);
	public List<PostDTO> selectPost();
	public List<PlantsDTO> selectPlantRecommend(String userId);
}
