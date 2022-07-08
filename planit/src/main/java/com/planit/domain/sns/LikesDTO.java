package com.planit.domain.sns;

import lombok.Data;

@Data
public class LikesDTO {

	private Long postNo;
	private String userId;
	private String targetId;
	private String regDate;
}
