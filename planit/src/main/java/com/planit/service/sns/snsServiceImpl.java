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
		System.out.println("startNumstartNum: "+startNum);
		List<PostDTO> imgPost = snsmapper.selectImgPost(params);

		return imgPost;
	}

	@Override
	public List<PostDTO> searchSNS(String keyField,String keyword,int startNum, int endNum) {

		
		if(keyField.equals("식물")) {
			keyField="plantsname"; 
		}else if(keyField.equals("계정")) {
			keyField="userId";
		}else if(keyField.equals("내용")) {
			keyField="postcontent";
		}
		
		System.out.println("service keyField: "+keyField+ " keyword: "+keyword);
		List<PostDTO> serachSNS = snsmapper.searchSNS(keyField,keyword,startNum,endNum);
		 
		return serachSNS;
	}

	@Override
	public List<AccountDTO> selectMainAccINfo(String USERID) {
		List<AccountDTO> AccInfo = snsmapper.selectMainAccINfo(USERID);
 
		return AccInfo;
	}

	@Override
	public List<PlantsCateDTO> selectMainCate(String USERID) {

		List<PlantsCateDTO> CateList = snsmapper.selectMainCate(USERID);

		return CateList;
	}

	@Override
	public boolean updateInfo(AccountDTO params) {
		int updateInfo = snsmapper.updateInfo(params);
		System.out.println("updateInfo IMPL: " + updateInfo);
		return (updateInfo == 1) ? true : false;
	}

 	
	@Override
	public List<FollowDTO> selectFollowerList(int startNum, int endNum,String userId) {
		System.out.println(" Service selectFollowerList startNum: "+startNum+" endNum: "+endNum+" userId: "+userId);
		List<FollowDTO> FollowerList = snsmapper.selectFollowerList(startNum,endNum,userId);
		return FollowerList;
	}

	@Override
	public List<FollowDTO> selectFollowerList2(String USERID, String followerid) {
		HashMap params = new HashMap();
		params.put("parameter1", USERID);
		params.put("parameter2", followerid);

		List<FollowDTO> selectFollowerList2 = snsmapper.selectFollowerList2(params);

		return selectFollowerList2;
	}
	
	@Override
	public List<FollowDTO> selectFollowingList(int startNum, int endNum,String userId) {
		 
		List<FollowDTO> FollowingList = snsmapper.selectFollowingList(startNum,endNum,userId);
		
		return FollowingList;
	}

	@Override
	public boolean insertFollow(String userId, String followerId, String followingId, int ftype) {

		HashMap params = new HashMap();
		params.put("parameter1", userId);
		params.put("parameter2", followerId);
		params.put("parameter3", followingId);
		params.put("parameter4", ftype);

		int insertFollow = snsmapper.insertFollow(params);

		return (insertFollow == 1) ? true : false;
	}

	@Override
	public boolean unfollow(String USERID, String followingid) {

		HashMap params = new HashMap();
		params.put("parameter1", USERID);
		params.put("parameter2", followingid);

		int unfollow = snsmapper.unFollow(params);

		return (unfollow == 1) ? true : false;
	}

	

}
