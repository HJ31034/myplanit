package com.planit.mapper.main;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

 
import com.planit.domain.main.UserDTO;

@Mapper
public interface UserMapper {

	//회원가입 
	public void saveUser(UserDTO userdto);
	
	//로그인
	public UserDTO loginUser(@Param("userId") String userId, @Param("password") String password);
	
	
	
	//중복 체크
	public UserDTO userId(String userId) throws Exception;



	
}
