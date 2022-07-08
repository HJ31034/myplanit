package com.planit.service.main;

import java.util.List;

import com.planit.domain.main.PlantKeywordDTO;
import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.PostDTO;

public interface MainService {
	public List<PlantKeywordDTO> selectPlantKeyword(int keyId);
	public List<AccountDTO> selectUsers(String userId);
	public List<PostDTO> selectPost();
}
