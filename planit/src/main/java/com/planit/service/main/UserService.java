package com.planit.service.main;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.planit.domain.main.UserDTO;
import com.planit.mapper.main.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	@Autowired
	UserMapper userMapper;
	
	//private final boolean yes=true;
	//private final boolean no=false;
//회원 가입
	//@Transactional
	public void joinUser(UserDTO userdto) {

		// BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// userdto.setUserPw(passwordEncoder.encode(userdto.getPassword()));
		userMapper.saveUser(userdto);
	}
	

	
	 // 회원가입 시, 유효성 체크
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }

//로그인 메서드 
//	public UserDTO userLogin(String userId, String password) throws Exception {
//		//(userdto.getUserId(), userdto.getPassword())
//		UserDTO authUser = userMapper.loginUser(userId, password);
//		if (authUser == null) {
//			System.out.println("service: 정보 불러들일 수 없음");
//
//		} else {
//			System.out.println("service: 로그인 정보 불러오기 정상작동 ");
//
//		}
//		return authUser;
//	}
    //로그인 메서드 2 
	public UserDTO userLogin(UserDTO userdto) throws Exception {
		
		UserDTO authUser = userMapper.loginUser(userdto);
		if (authUser == null) {
			System.out.println("service: 정보 불러들일 수 없음");

		} else {
			System.out.println("service: 로그인 정보 불러오기 정상작동 ");

		}
		return authUser;
	}	

//아이디 중복 체크용 테스트
	
    public int idCheck(String userId) {
    	
        int cnt = userMapper.idCheck(userId);
        System.out.println(cnt);
        return cnt;
    }
    //현재 비밀번호 확인
public int pwdCheck(String password) {
    	
        int count = userMapper.pwdCheck(password);
        System.out.println("현재 비밀번호 확인"+count);
        return count;
    }


	
    public void pwdCh(UserDTO userdto) {
    	userMapper.pwdCh(userdto);	
    	System.out.println("비밀번호 서비스");
    }
    
    
    //유저정보 보기 메서드 
    public void userInfo(UserDTO userdto) {
    	userMapper.userInfo(userdto);
    	System.out.println("유저정보 불러오기 메서드");
    }
    
    //회원정보 수정
   public void infoCh(UserDTO userdto) {
	    userMapper.infoCh(userdto);
	   System.out.println("회원정보 수정 작동 ?");
   
   }
}
