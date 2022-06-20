package com.planit.domain.sns;

import lombok.Data;

@Data
public class CommentDTO {

	private long postNo;
	private String userId;
	private String regDate;
	private String content;
}
