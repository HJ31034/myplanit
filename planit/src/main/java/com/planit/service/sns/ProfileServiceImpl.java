package com.planit.service.sns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.FollowDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.domain.sns.UserToPlantsDTO;
import com.planit.mapper.sns.ProfileMapper;

@Service
public class ProfileServiceImpl implements ProfileService{
	@Autowired
	private ProfileMapper profileMapper;

	@Override
	public AccountDTO selectUserProfile(String id) {
		return profileMapper.selectUserProfile(id);
	}

	@Override
	public List<UserToPlantsDTO> selectUserPlants(String id) {
		return profileMapper.selectUserPlants(id);
	}

	@Override
	public List<PostDTO> selectUserPost(UserToPlantsDTO params, String page) {
		return profileMapper.selectUserPost(params, page);
	}

	@Override
	public List<PostDTO> selectUserLikes(String id) {
		return profileMapper.selectUserLikes(id);
	}

	@Override
	public int selectFollow(FollowDTO params) {
		return profileMapper.selectFollow(params);
	}

	@Override
	public void insertFollow(FollowDTO params) {
		profileMapper.insertFollow(params);		
	}

	@Override
	public void deleteFollow(FollowDTO params) {
		profileMapper.deleteFollow(params);
	}

	@Override
	public void updateFollowCount(String id, String check) {
		profileMapper.updateFollowCount(id, check);
	}
	
	
}
