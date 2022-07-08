package com.planit.controller.main;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletResponse;


//세션처리용
public class SessionController {
	public static final String loginSession ="loginSession";
	private Map<String, Object> sessionStorage= new ConcurrentHashMap<>();
	
	public void createSession(Object value, HttpServletResponse response) {
		//세션생성 
		String session=UUID.randomUUID().toString();
		sessionStorage.put(session, value);
	}
	
}
