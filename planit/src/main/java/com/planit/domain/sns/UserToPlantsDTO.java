package com.planit.domain.sns;

import lombok.Data;

@Data
public class UserToPlantsDTO {
	
	private int plantsCateNo;
	private String plantsName;
	private int plantId;
	private String userId;
	
	public UserToPlantsDTO(String plantsName, String userId) {
		this.plantsName = plantsName;
		this.userId = userId;
	}

	public UserToPlantsDTO() {
		super();
	}

	public UserToPlantsDTO(String plantsName, int plantId, String userId) {
		super();
		this.plantsName = plantsName;
		this.plantId = plantId;
		this.userId = userId;
	}
	
	
	
	
	 
}
