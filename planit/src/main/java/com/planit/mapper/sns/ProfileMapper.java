package com.planit.mapper.sns;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.FollowDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.domain.sns.UserToPlantsDTO;

@Mapper
public interface ProfileMapper {
	public AccountDTO selectUserProfile(String id);
	public List<UserToPlantsDTO> selectUserPlants(String id);
	public List<PostDTO> selectUserPost(@Param("params") UserToPlantsDTO params, @Param("page") String page);
	public List<PostDTO> selectUserLikes(String id);
	public int selectFollow(FollowDTO params);
	public void insertFollow(FollowDTO params);
	public void deleteFollow(FollowDTO params);
	public void updateFollowCount(@Param("userId") String id, @Param("check") String check);
}
