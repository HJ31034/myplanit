package com.planit.service.sns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planit.domain.sns.PostDTO;
import com.planit.mapper.sns.SnsMapper;

@Service
public class snsServiceImpl implements snsService {

	@Autowired
	private SnsMapper snsmapper;

	@Override
	public List<PostDTO> selectImgPost() {
		PostDTO postdto = new PostDTO();
		List<PostDTO> imgPost = snsmapper.selectImgPost();
	 
		return imgPost;
	}

}
