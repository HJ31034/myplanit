package com.planit.service.main;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.planit.domain.main.UserDTO;
import com.planit.mapper.main.UserMapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
	@Autowired
	private UserMapper userMapper;
 
	//private UserMapper2 userMapper2;
	// 회원가입 시큐리티 처리

	public void joinUser(UserDTO userdto) {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userdto.setPassword(passwordEncoder.encode(userdto.getPassword()));
		userdto.setUserAuth("USER");
		// userdto.setUserAuth("USER");
		userMapper.saveUser(userdto);
		
	}
	
//	public void joinUser2(UserDTO2 userdto2) {
//		userMapper2.saveUser2(userdto2);
//	}
	
	

	// 회원가입 시, 유효성 체크
	public Map<String, String> validateHandling(Errors errors) {
		Map<String, String> validatorResult = new HashMap<>();

		for (FieldError error : errors.getFieldErrors()) {
			String validKeyName = String.format("valid_%s", error.getField());
			validatorResult.put(validKeyName, error.getDefaultMessage());
		}

		return validatorResult;
	}

	
	//로그인 처리
	@Override
	public UserDTO loadUserByUsername(String userId) throws UsernameNotFoundException {
		
		
		
		UserDTO authUser = userMapper.findByUsername(userId);

			
//		for(int i=0; i < authUser.getKeyId().length; i++) {
//			
//		}
		
		if (authUser == null) {
			System.out.println("service: 정보 불러들일 수 없음");
			throw new UsernameNotFoundException("userId"+userId+"not found");
		
		} 
		
		
		
		//return (UserDTO) dto;
		return authUser;
	}
	



//아이디 중복 체크용 테스트

	public int idCheck(String userId) {

		int cnt = userMapper.idCheck(userId);
		System.out.println(cnt);
		return cnt;
	}


	//비밀번호 변경
	public void pwdCh(UserDTO userdto) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userdto.setPassword(passwordEncoder.encode(userdto.getPassword()));
		userMapper.pwdCh(userdto);
		System.out.println("비밀번호 서비스");
	}

	// 유저정보 보기 메서드
	public void userInfo(UserDTO userdto) {
		userMapper.userInfo(userdto);
		System.out.println("유저정보 불러오기 메서드");
	}

	// 회원정보 수정
	public void infoCh(UserDTO userdto) {
		userMapper.infoCh(userdto);
		System.out.println("회원정보 수정 작동 ?");

	}
	
	public void interest(UserDTO userdto) {
		userMapper.interest(userdto);
	}


	

}
