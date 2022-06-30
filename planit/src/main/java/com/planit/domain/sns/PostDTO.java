package com.planit.domain.sns;

import lombok.Data;

@Data
public class PostDTO {

	private int postNo;
	private String postContent;
	private String userId;
	private String createDt;
	private int likesCount;
	private int plantsCateNo;
	private String fileName;
	private String plantsName;
	
	private String[] fileNameArr;
	
	
	private String deleteYN;
	
	
	
}
