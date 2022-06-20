package com.planit.service.sns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planit.domain.sns.CommentDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.mapper.sns.PostMapper;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostMapper postMapper;

	@Override
	public PostDTO getBoardDetail(Long postNo) {
		PostDTO postDto = postMapper.selectPostDetail(postNo);
		postDto.setFileNameArr(postDto.getFileName().split(","));
		return postDto;
	}

	@Override
	public List<CommentDTO> getCommentDetail(Long postNo) {
		return postMapper.commentList(postNo);
	}

	@Override
	public void insertComment(CommentDTO parmas) {
		//로그인 처리 후에 지울 
		parmas.setUserId("kosta");
		postMapper.insertComment(parmas);
		
	}

}
