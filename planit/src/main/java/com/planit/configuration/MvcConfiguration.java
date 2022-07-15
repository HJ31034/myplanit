package com.planit.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.planit.interceptor.LoggerInterceptor;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
	@Value("${planit.upload.path}")
	private String saveDir;
	
	@Value("${access.static.resource}")
	private List<String> urls;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor())
		.excludePathPatterns("/css/**", "/fonts/**", "/plugin/**", "/scripts/**");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		for(String url: urls){
			registry.addResourceHandler(url + "/imgs/img_section/**").addResourceLocations("file:" + saveDir);
		}
	}


}