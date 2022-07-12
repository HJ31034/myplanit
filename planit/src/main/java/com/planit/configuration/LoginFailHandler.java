package com.planit.configuration;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;



public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, 
			HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {
		logger.info("login Failure handler");
		
		String errorMessage;
		
		if (e instanceof BadCredentialsException || e instanceof InternalAuthenticationServiceException) {
			errorMessage = "아이디 혹은 비밀번호를 확인해 주세요.";
		} else if (e instanceof UsernameNotFoundException) {
			errorMessage = "존재하지 않는 아이디 입니다.";
		} else {
			errorMessage = "알수 없는 이유로인하여 로그인이 되지 않습니다.";
		}
		
	errorMessage=URLEncoder.encode(errorMessage, "UTF-8");
	setDefaultFailureUrl("/login?error=true&exception="+errorMessage);
	super.onAuthenticationFailure(request, response, e);
	}
}
