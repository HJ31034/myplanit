package com.planit.domain.sns;

import lombok.Data;

@Data
public class WeatherLocationDTO {
	
	private int postNo;
	private String latitude;
	private String longitude;
	private String baseDate;
	private String weather;
	private String address;
}
