package com.planit.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.planit.domain.main.PlantKeywordDTO;
import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.PostDTO;

@Mapper
public interface MainMapper {
	public List<PlantKeywordDTO> selectPlantKeyword(int keyId);
	public List<AccountDTO> selectUsers(int keyId);
	public List<PostDTO> selectPost();
}
