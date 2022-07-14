package com.planit.service.sns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planit.domain.sns.CommentDTO;
import com.planit.domain.sns.FilesDTO;
import com.planit.domain.sns.LikesDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.domain.sns.PostDetailDTO;
import com.planit.domain.sns.PostFilesDTO;
import com.planit.domain.sns.UserToPlantsDTO;
import com.planit.domain.sns.WeatherLocationDTO;
import com.planit.mapper.sns.PostMapper;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostMapper postMapper;

	@Override
	public PostDTO getBoardDetail(Long postNo) {
		PostDTO postDto = postMapper.selectPostDetail(postNo);
 
 
		postDto.setOrgFileNameArr(postDto.getOrgFileName().split(","));
		postDto.setRealFileNameArr(postDto.getRealFileName().split(","));
 
		return postDto;
	}
	
	@Override
	public void deleteYN(Long postNo) {
		postMapper.deleteYN(postNo);
	}
	
	@Override
	public void modify(PostDetailDTO parmas) {
		postMapper.modify(parmas);
	}

	@Override
	public List<CommentDTO> getCommentDetail(Long postNo) {
		return postMapper.commentList(postNo);
	}

	@Override
	public void insertComment(CommentDTO params) {
		postMapper.insertComment(params);
		
	}

	@Override
	public void deleteComment(CommentDTO params) {
		postMapper.deleteComment(params);
	}

	@Override
	public FilesDTO insertFiles(FilesDTO params){
		postMapper.insertFiles(params);

		return params;
	}

	@Override
	public void deleteFile(long no){
		postMapper.deleteFile(no);
	}

	@Override
	public void insertPost(PostDetailDTO params) {
		Long[] fileNoArr = params.getNo();

		postMapper.insertPost(params);
		postMapper.insertWeatherLocation(params);

		for(Long fileNo: fileNoArr){
			PostFilesDTO dto = new PostFilesDTO();

			dto.setPostNo(params.getPostNo());
			dto.setNo(fileNo);

			postMapper.insertPostFiles(dto);
		}
	}

	@Override
	public List<UserToPlantsDTO> selectPlantsCate(String userId) {
		return postMapper.selectPlantsCate(userId);
	}
	
	@Override
	public String getLikes(LikesDTO params) {
		int result = postMapper.getLikes(params);
		
		if(result == 0) {
			return "unlike";	
		}
		
		return "like";
	}

	@Override
	public void insertLikes(LikesDTO params) {
		postMapper.insertLikes(params);
		
	}

	@Override
	public void deleteLikes(LikesDTO params) {
		postMapper.deleteLikes(params);
	}
	
	public void likeControl(LikesDTO params, String isLike) {
		if(isLike.equalsIgnoreCase("like")) {
			this.insertLikes(params);
		}else {
			this.deleteLikes(params);
		}
	}

	@Override
	public List<FilesDTO> getFiles(Long postNo) {
		return postMapper.getFiles(postNo);
	}

	

}
