package com.planit.domain.sns;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CommentDTO {

	private long postNo;
	private String userId;
	private String regDate;
	private String content;
}
