package com.planit.controller.sns;

import com.planit.domain.sns.FilesDTO;
import com.planit.service.sns.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileController {
    @Value("${planit.upload.path}")
    private String uploadPath;

	@Autowired
	private PostService postService;
	
	@PostMapping("/planiter/post/file/upload")
    public String uploadFile(@RequestParam("uploadFiles") MultipartFile[] files, Model model) throws IllegalStateException, IOException {
		List<FilesDTO> list = new ArrayList<>();

		for (MultipartFile file : files) {
			if(!file.isEmpty()) {
				FilesDTO dto = new FilesDTO();

				File file1 = new File(file.getOriginalFilename());
				file.transferTo(file1); // 받아온 파일을 업로드

				String fileName = file.getOriginalFilename(); // 업로드한 파일 명만 저장
				dto.setFileName(fileName);

				dto = postService.insertFiles(dto);

				list.add(dto);
			}
		}
		model.addAttribute("files", list);

		return "sns/write-post :: #file_list";

    }

	@GetMapping("/planiter/post/file/delete")
	public String uploadFile(@RequestParam("fileNo") long no, Model model) throws IllegalStateException, IOException {
		postService.deleteFile(no);

		return "sns/write-post";
	}
}
