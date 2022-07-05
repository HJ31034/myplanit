package com.planit.service.sns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planit.domain.sns.CommentDTO;
import com.planit.domain.sns.FilesDTO;
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
	public List<CommentDTO> getCommentDetail(Long postNo) {
		return postMapper.commentList(postNo);
	}

	@Override
	public void insertComment(CommentDTO parmas) {
		//로그인 처리 후에 지울 
		parmas.setUserId("kosta");
		postMapper.insertComment(parmas);
		
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

}
