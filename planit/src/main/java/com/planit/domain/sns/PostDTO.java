package com.planit.domain.sns;

import lombok.Data;

@Data
public class PostDTO {
	private int POSTNO;
	private String POSTCONTENT;
	private String USERID;
	private String createdt;
	private int likesCount;
	private int plantsCateNO;
	
	private String filename;

}
