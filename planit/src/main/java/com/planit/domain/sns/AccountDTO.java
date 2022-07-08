package com.planit.domain.sns;

import lombok.Data;

@Data
public class AccountDTO {
	private String userId;
	private String accDescription;
	private String profileImg;
	private int followerCount;
	private int followingCount;
	 
	 private String plantsname;

	 
	 public AccountDTO() {
			super();
			 
		} 
	 

	 
	 
	 
}
