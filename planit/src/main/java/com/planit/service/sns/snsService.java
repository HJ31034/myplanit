package com.planit.service.sns;

import java.util.HashMap;
import java.util.List;

import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.FollowDTO;
import com.planit.domain.sns.PlantsCateDTO;
import com.planit.domain.sns.PostDTO;

 

public interface snsService {

	public List<PostDTO> selectImgPost(int startNum, int endNum);
	public List<PostDTO> searchSNS(String keyField,String keyword,int startNum, int endNum);
	//public List<AccountDTO> searchAcc(String keyField,String keyword,int startNum, int endNum);
	
	public List<AccountDTO> selectMainAccINfo(String USERID);
	public List<PlantsCateDTO> selectMainCate(String USERID);
	
	public boolean updateInfo(AccountDTO params);
	
	public List<FollowDTO> selectFollowingList(int startNum, int endNum, String USERID);
	public List<FollowDTO> selectFollowerList(int startNum, int endNum, String USERID);
	public List<FollowDTO> selectFollowerList2(String USERID, String followingid);
	
	public boolean insertFollow(String userId,String followerId,String followingId,int ftype);
	public boolean unfollow(String USERID, String followingid);
 
 
}
