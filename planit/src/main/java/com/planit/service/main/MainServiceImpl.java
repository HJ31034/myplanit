package com.planit.service.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planit.domain.main.PlantKeywordDTO;
import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.mapper.main.MainMapper;

@Service
public class MainServiceImpl implements MainService {
	
	@Autowired
	private MainMapper mainMapper;

	@Override
	public List<PlantKeywordDTO> selectPlantKeyword(int keyId) {
		return mainMapper.selectPlantKeyword(keyId);
	}

	@Override
	public List<AccountDTO> selectUsers(String userId) {
		return mainMapper.selectUsers(userId);
	}

	@Override
	public List<PostDTO> selectPost() {
		return mainMapper.selectPost();
	}

}
