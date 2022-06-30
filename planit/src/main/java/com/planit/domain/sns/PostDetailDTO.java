package com.planit.domain.sns;

import lombok.Data;

@Data
public class PostDetailDTO {

	private long postNo;
	private String postContent;
	private String userId;
	private String createDt;
	private int likesCount;
	private int plantsCateNo;
	private String plantsName;
	private String deleteYN;
	
	private String latitude;
	private String longitude;
	private String baseDate;
	private String weather;
	private String address;
	
	private Long[] no;
}
