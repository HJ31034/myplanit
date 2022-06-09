package com.planit.mapper.sns;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.planit.domain.sns.PostDTO;

 
 
@Mapper
public interface SnsMapper {
	
	public List<PostDTO> selectImgPost();

}
