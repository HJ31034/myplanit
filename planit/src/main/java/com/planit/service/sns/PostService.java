package com.planit.service.sns;

import java.util.List;

import com.planit.domain.sns.CommentDTO;
import com.planit.domain.sns.FilesDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.domain.sns.PostDetailDTO;
import com.planit.domain.sns.PostFilesDTO;
import com.planit.domain.sns.UserToPlantsDTO;
import com.planit.domain.sns.WeatherLocationDTO;

public interface PostService {
	
	public PostDTO getBoardDetail(Long postNo);
	public List<CommentDTO> getCommentDetail(Long postNo);
	public void insertComment(CommentDTO parmas);
	public FilesDTO insertFiles(FilesDTO params);
	public void deleteFile(long no);
	public void insertPost(PostDetailDTO params);
	public List<UserToPlantsDTO> selectPlantsCate(String userId);
}
