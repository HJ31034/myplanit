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
	//public UserDTO loginUser(UserDTO userdto);
	
	
	
	//중복 체크
	//public UserDTO userId(@Param ("userId")String userId) throws Exception;
	public int idCheck(@Param ("userId")String userId);
	
	//현재 비밀번호
	//public int pwdCheck(@Param("password") String password);
	//public int pwdCheck(@Param("password") String password, @Param("userId") String userId);
	
	
	//회원정보 수정 (비밀번호)
	//public void pwdCh(@Param("userId") String userId,@Param("password") String password);
	public void pwdCh(UserDTO userdto);

	
	//회원정보 불러오기
	public void userInfo(UserDTO userdto);
	
	//회원정보 수정
	public void infoCh(UserDTO userdto);

	//public int findByUserId(String userId);

	
	//시큐리티를 통한 로그인 
	public UserDTO findByUsername(@Param("userId") String userId);

	
}
