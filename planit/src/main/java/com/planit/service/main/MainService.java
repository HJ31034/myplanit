package com.planit.service.main;

import java.util.List;

import com.planit.domain.main.PlantKeywordDTO;
import com.planit.domain.main.PlantsDTO;
import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.PostDTO;

public interface MainService {
	public List<PlantKeywordDTO> selectPlantKeyword(int keyId);
	public List<AccountDTO> selectUsers(List<String> userId);
	public List<AccountDTO> selectUsers2(List<String> userId, int count);
	public List<PostDTO> selectPost();
	public List<PlantsDTO> selectPlantRecommend(String userId);
}
