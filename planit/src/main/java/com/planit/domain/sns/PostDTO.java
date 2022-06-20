package com.planit.domain.sns;

import lombok.Data;

@Data
public class PostDTO {
//	private int POSTNO;
//	private String POSTCONTENT;
//	private String USERID;
//	private String createdt;
//	private int likesCount;
//	private int plantsCateNO;
//	
//	private String filename;

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
