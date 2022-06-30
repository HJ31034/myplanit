package com.planit.domain.sns;

import lombok.Data;

@Data
public class FollowDTO {
	private String userId;
	private String followerId;
	private String followingId;
	
	private int ftype;
	private String accDescription;
	private String profileImg;

}
