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
 
	private String orgFileName;
	private String realFileName;
 
	private String plantsName;
	
	private String[] orgFileNameArr;
	private String[] realFileNameArr;
	
	private String deleteYN;
	
	private String weather;
	private String address;
	
	// 임시
	private String fileName;
	
}
