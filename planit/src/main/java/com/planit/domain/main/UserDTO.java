package com.planit.domain.main;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDTO {
	@Size(min=5, max=12)
	private String userId;
	

	@NotBlank
	private String userName;
	
	@Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "영문 대,소문자, 숫자, 특수기호 최소 1개를 포함한 8자 ~ 20자의 조합")
	private String password;
	
	

	//@NotBlank(message="이메일 입력은 필수 입니다.")
	@Email(message="이메일 형식은 xxx@xxx.com")
	private String email;
	
	
	
	private String phone;

	
	//private int keyId;
	
	
	
}
