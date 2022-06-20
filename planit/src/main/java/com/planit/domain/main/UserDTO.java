package com.planit.domain.main;

import lombok.Data;

@Data
public class UserDTO {

	private String userId;
	
	private String userName;
	
	
	private String password;
	
	private String email;
	
	private String phone;
	private int keyId;
}
