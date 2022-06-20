package com.planit.service.sns;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 
import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.FollowDTO;
import com.planit.domain.sns.PlantsCateDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.mapper.sns.SnsMapper;

@Service
public class snsServiceImpl implements snsService {

	@Autowired
	private SnsMapper snsmapper;

	@Override
	public List<PostDTO> selectImgPost(int startNum, int endNum) {
		 
		HashMap params = new HashMap();
		params.put("parameter1", startNum);
		params.put("parameter2", endNum);
		 
		
		List<PostDTO> imgPost = snsmapper.selectImgPost(params);
	 
		return imgPost;
	}
	
	@Override
	public List<AccountDTO> selectMainAccINfo(){
		String USERID = "kosta";
		 
		List<AccountDTO> AccInfo = snsmapper.selectMainAccINfo(USERID);
		
		return AccInfo;
	}
	
	@Override
	public List<PlantsCateDTO> selectMainCate(){
		String USERID = "kosta";
		
		List<PlantsCateDTO> CateList = snsmapper.selectMainCate(USERID);
		
		return CateList;
	}
	
	@Override
	public boolean updateInfo(AccountDTO params) {
		int updateInfo = snsmapper.updateInfo(params);
		System.out.println("updateInfo IMPL: "+ updateInfo);
		return (updateInfo == 1) ? true : false;
	}
	
	@Override
	public List<FollowDTO> selectFollowerList(String USERID){
		 
		
		List<FollowDTO> FollowerList = snsmapper.selectFollowerList(USERID);
		return FollowerList;
	}
	
	@Override
	public List<FollowDTO> selectFollowingList(String USERID){
		
		
		List<FollowDTO> FollowingList = snsmapper.selectFollowingList(USERID);
		return FollowingList;
	}
	
	@Override
	public boolean insertFollow(String USERID,String followerid) {
		 
		HashMap params = new HashMap();
		params.put("parameter1", USERID);
		params.put("parameter2", followerid);
			
		 
		int insertFollow = snsmapper.insertFollow(params);
		
		return (insertFollow == 1) ? true : false;
	}
	
	@Override
	public boolean unfollow(String USERID,String followerid) {
		 
		HashMap params = new HashMap();
		params.put("parameter1", USERID);
		params.put("parameter2", followerid);
			
		int unfollow = snsmapper.unFollow(params);
		
		return (unfollow == 1) ? true : false;
	}
	
	
 
	@Override
	public List<FollowDTO> selectFollowerList2(String USERID,String followingid){
		HashMap params = new HashMap();
		params.put("parameter1", USERID);
		params.put("parameter2", followingid);
		
		List<FollowDTO> testList = snsmapper.selectFollowerList2(params);
		
		return testList;
	}
 
	 
}
