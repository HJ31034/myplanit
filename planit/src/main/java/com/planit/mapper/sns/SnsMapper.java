package com.planit.mapper.sns;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.FollowDTO;
import com.planit.domain.sns.PlantsCateDTO;
import com.planit.domain.sns.PostDTO;

 
 
@Mapper
public interface SnsMapper {
	
	public List<PostDTO> selectImgPost(HashMap params);
	public List<AccountDTO> selectMainAccINfo(String USERID);
	public List<PlantsCateDTO> selectMainCate(String USERID);
	public int updateInfo(AccountDTO params);
	public List<FollowDTO> selectFollowerList(String USERID);
	public List<FollowDTO> selectFollowingList(String USERID);
	
	public int insertFollow(HashMap params);
	public int unFollow(HashMap params);
	
	public int followerCount(String USERID);
	public int followingCount(String USERID);
	
	public List<FollowDTO> selectFollowerList2(HashMap params);
	
	public int selectImgPostCnt();
	
	

	
 
}
