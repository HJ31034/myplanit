package com.planit.domain.sns;

import lombok.Data;

@Data
public class LikesDTO {

	private int postNo;
	private String userId;
	private String targetId;
	private String regDate;
}
