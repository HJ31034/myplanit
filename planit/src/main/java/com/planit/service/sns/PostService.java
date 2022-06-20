package com.planit.service.sns;

import java.util.List;

import com.planit.domain.sns.CommentDTO;
import com.planit.domain.sns.PostDTO;

public interface PostService {
	
	public PostDTO getBoardDetail(Long postNo);
	public List<CommentDTO> getCommentDetail(Long postNo);
	public void insertComment(CommentDTO parmas);

}
