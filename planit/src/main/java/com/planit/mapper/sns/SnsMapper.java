package com.planit.mapper.sns;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.FollowDTO;
import com.planit.domain.sns.PlantsCateDTO;
import com.planit.domain.sns.PostDTO;

 
 
@Mapper
public interface SnsMapper {
	 
	public List<PostDTO> selectImgPost(HashMap params);
	public List<PostDTO> searchSNS(@Param("keyField") String keyField,@Param("keyword") String keyword,
			@Param("startNum") int startNum, @Param("endNum") int endNum);
	public List<AccountDTO> searchAccount(@Param("keyField") String keyField,@Param("keyword") String keyword,
			@Param("startNum") int startNum, @Param("endNum") int endNum);
	
	public int selectImgPostCnt();
	
	public List<AccountDTO> selectMainAccINfo(String USERID);
	public List<PlantsCateDTO> selectMainCate(String USERID);
	
	public int updateInfo(AccountDTO params);
	
	public List<FollowDTO> selectFollowingList(@Param("startNum") int startNum, @Param("endNum") int endNum, @Param("userId") String userId);
	public List<FollowDTO> selectFollowerList(@Param("startNum") int startNum, @Param("endNum") int endNum,@Param("userId") String userId);
	public List<FollowDTO> selectFollowerList2(HashMap params);
	
	public int insertFollow(HashMap params);
	public int unFollow(HashMap params);
	
	public int updateFollowcount(@Param("userId") String userId, @Param("check") String check);
 	

	
 
}
