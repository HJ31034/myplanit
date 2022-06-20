package com.planit.service.sns;

import java.util.HashMap;
import java.util.List;

import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.FollowDTO;
import com.planit.domain.sns.PlantsCateDTO;
import com.planit.domain.sns.PostDTO;

 

public interface snsService {

	public List<PostDTO> selectImgPost(int startNum, int endNum);
	public List<AccountDTO> selectMainAccINfo();
	public List<PlantsCateDTO> selectMainCate();
	public boolean updateInfo(AccountDTO params);
	public List<FollowDTO> selectFollowerList(String USERID);
	 
	public List<FollowDTO> selectFollowingList(String USERID);
	
	public boolean insertFollow(String USERID, String followingid);
	public boolean unfollow(String USERID, String followerid);
 
	public List<FollowDTO> selectFollowerList2(String USERID, String followingid);
 
}
