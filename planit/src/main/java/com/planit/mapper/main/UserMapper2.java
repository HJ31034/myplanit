package com.planit.mapper.main;

import org.apache.ibatis.annotations.Mapper;

import com.planit.domain.main.UserDTO2;

@Mapper
public interface UserMapper2 {

	//회원가입 
	public void saveUser2(UserDTO2 userdto2);
	
	

	

	
}
