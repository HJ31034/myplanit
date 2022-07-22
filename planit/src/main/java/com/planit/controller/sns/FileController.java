package com.planit.controller.sns;

import com.planit.domain.sns.FilesDTO;
import com.planit.service.sns.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class FileController {
    @Value("${planit.upload.path}")
    private String saveDir;

	@Autowired
	private PostService postService;
	
	@PostMapping("/planiter/post/file/upload")
    public String uploadFile(@RequestParam("uploadFiles") MultipartFile[] files, Model model) throws IllegalStateException, IOException {
		List<FilesDTO> list = new ArrayList<>();
		 
		for (MultipartFile file : files) {
			if(!file.isEmpty()) {
				FilesDTO dto = new FilesDTO();
				String realFileName = UUID.randomUUID().toString();

				File file1 = new File(saveDir + realFileName);
				file.transferTo(file1); // 받아온 파일을 업로드

				String orgFileName = file.getOriginalFilename(); // 업로드한 파일 명만 저장

				dto.setRealFileName(realFileName);
				dto.setOrgFileName(orgFileName);

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
	
	@ResponseBody
	@GetMapping("/planiter/post/image/{filename}")
	public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
		return new UrlResource("file:///" + saveDir + filename.trim());
	}
}
