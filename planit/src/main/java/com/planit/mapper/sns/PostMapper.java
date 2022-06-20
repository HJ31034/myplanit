package com.planit.mapper.sns;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.planit.domain.sns.CommentDTO;
import com.planit.domain.sns.PostDTO;

@Mapper
public interface PostMapper {
	
	public PostDTO selectPostDetail(Long postNo);
	public List<CommentDTO> commentList(Long postNo);
	public void insertComment(CommentDTO params);

}
