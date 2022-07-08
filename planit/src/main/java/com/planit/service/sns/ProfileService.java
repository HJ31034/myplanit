package com.planit.service.sns;

import java.util.List;

import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.FollowDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.domain.sns.UserToPlantsDTO;

public interface ProfileService {
	public AccountDTO selectUserProfile(String id);
	public List<UserToPlantsDTO> selectUserPlants(String id);
	public List<PostDTO> selectUserPost(UserToPlantsDTO params, String page);
	public List<PostDTO> selectUserLikes(String id);
	public int selectFollow(FollowDTO params);
	public void insertFollow(FollowDTO params);
	public void deleteFollow(FollowDTO params);
	public void updateFollowCount(String id, String check);
}
