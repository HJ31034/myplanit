package com.planit.service.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planit.domain.main.UserDTO;
import com.planit.mapper.main.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	@Autowired
	UserMapper userMapper;
//회원 가입
	@Transactional
	public void joinUser(UserDTO userdto) {

		// BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// userdto.setUserPw(passwordEncoder.encode(userdto.getPassword()));
		userMapper.saveUser(userdto);
	}

//로그인 메서드 
	public UserDTO userLogin(String userId, String password) throws Exception {
		//(userdto.getUserId(), userdto.getPassword())
		UserDTO authUser = userMapper.loginUser(userId, password);
		if (authUser == null) {
			System.out.println("service: 정보 불러들일 수 없음");

		} else {
			System.out.println("service: 로그인 정보 불러오기 정상작동 ");

		}
		return authUser;
	}

//아이디 중복 체크용 테스트 
	public UserDTO idChk(String userId) throws Exception {
		UserDTO exist = userMapper.userId(userId);
		if(exist==null) {
			
		}
	return exist;}
}
