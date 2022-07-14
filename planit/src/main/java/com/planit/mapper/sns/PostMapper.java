package com.planit.mapper.sns;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.planit.domain.sns.CommentDTO;
import com.planit.domain.sns.FilesDTO;
import com.planit.domain.sns.LikesDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.domain.sns.PostDetailDTO;
import com.planit.domain.sns.PostFilesDTO;
import com.planit.domain.sns.UserToPlantsDTO;

@Mapper
public interface PostMapper {
	
	public PostDTO selectPostDetail(Long postNo);
	public void deleteYN(Long postNo);
	public void modify(PostDetailDTO parmas);
	public List<CommentDTO> commentList(Long postNo);
	public void insertComment(CommentDTO params);
	public void deleteComment(CommentDTO params);
	public Long insertPost(PostDetailDTO params);
	public void insertPostFiles(PostFilesDTO params);
	public void insertWeatherLocation(PostDetailDTO params);
	public void insertFiles(FilesDTO params);
	public void deleteFile(long no);
	public List<UserToPlantsDTO> selectPlantsCate(String userId);
	public int getLikes(LikesDTO params);
	public void insertLikes(LikesDTO params);
	public void deleteLikes(LikesDTO params);
	public List<FilesDTO> getFiles(Long postNo);

}
