package com.planit.domain.main;

import java.util.Collection;
import java.util.Collections;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;




@ToString
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class UserDTO implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	
	@NotBlank
	private String userName;
	
	@Size(min=5, max=12)
	@Pattern(regexp="(?=.*[a-z]).{5,12}", message="영문 다섯자리 이상 12자리 이하")
	private String userId;
	
	
	
	@Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "영문 대,소문자, 숫자, 특수기호 최소 1개를 포함한 8자 ~ 20자의 조합")
	private String password;
	
	

	//@NotBlank(message="이메일 입력은 필수 입니다.")
	@Email(message="이메일 형식은 xxx@xxx.com")
	private String email;
	
	
	
	
	private String phone;
	
	//private Long keyId;
	private String regdate;
	
	private String userAuth;
	
	@NotEmpty(message="필수 사항입니다.")
	private Long[] keyId;
	

	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return Collections.singletonList(new SimpleGrantedAuthority(this.userAuth));
	}



	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userId;
	}
	
//	public void setUsername(String Username) {
//		this.userId = Username;
//	}



	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getRegdate() {
		return regdate;
	}



	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}



	public String getUserAuth() {
		return userAuth;
	}



	public void setUserAuth(String userAuth) {
		this.userAuth = userAuth;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long[] getKeyId() {
		return keyId;
	}



	public void setKeyIds(Long[] keyId) {
		this.keyId = keyId;
	}



	

	
	
//}



	



	


	
	
	


	
	






	



	

	
		
	
	
}
