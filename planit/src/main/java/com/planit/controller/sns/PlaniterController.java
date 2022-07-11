package com.planit.controller.sns;

import java.util.List;

 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
 
import org.springframework.web.servlet.ModelAndView;
 
import com.planit.domain.sns.AccountDTO;
 
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.planit.domain.sns.AccountDTO;
import com.planit.domain.sns.FollowDTO;
 
import com.planit.domain.sns.PlantsCateDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.mapper.sns.SnsMapper;
import com.planit.service.sns.snsService;

@Controller
@RequestMapping(value = "/planiter")
public class PlaniterController {

	@Autowired
	private snsService snsservice;
	
	@Autowired
	private SnsMapper snsmapper;
	 
	 
	
	// SNS메인 게시물리스트 및 검색처리
	@RequestMapping(value = "/")
    public String mainSnsPostList(Model model,
			@RequestParam(value="keyField",required = false) String keyField,
			@RequestParam(value="keyword", required = false) String keyword) {
		
		List<PostDTO> ImgPost = null;
		String USERID="kosta";
 
		 
		if(keyword==null||keyField==null) {
			
		 ImgPost = snsservice.selectImgPost(0,15);
		 
		}else if(keyword!=null||keyField!=null){ //검색처리
			String searchText=" 기준 검색결과";
			model.addAttribute("keyFieldSearch",keyField+searchText);
			
			model.addAttribute("keyword", keyword);
			model.addAttribute("keyField", keyField);
 
			model.addAttribute("keyField", keyField);
 
		 
			ImgPost = snsservice.searchSNS(keyField, keyword,0,15);
		}
		
		model.addAttribute("ImgPost", ImgPost);
		
		
		//계정 정보 출력 = 팔로워,팔로잉,프로필사진,식물카테고리
		List<AccountDTO> AccInfo = snsservice.selectMainAccINfo(USERID);
		model.addAttribute("AccInfo", AccInfo);
		List<PlantsCateDTO> CateList = snsservice.selectMainCate(USERID);
		model.addAttribute("CateList",CateList);
		 
		
		return "sns/index";
	}
	
  
	@ResponseBody
	@RequestMapping(value = "/nextImgPost")
	public  List<PostDTO> nextImgPost(@RequestParam(value="page", required = false) String page,
									  @RequestParam(value="keyField",required = false) String keyField,
 
									  @RequestParam(value="keyword", required = false) String keyword, HttpServletRequest request,Model model) {
  
 
		 
		System.out.println("nextImgPost keyword: "+keyword);
		System.out.println("nextImgPost keyField: "+ keyField); 
		System.out.println("nextImgPost page: " +page);
 
		 if(page== null) {
			 page = "1";
		 }
		 
 		  int pageNum=Integer.parseInt(page);
 		  System.out.println("pageNum: "+pageNum);
 		 
		 int totalCnt = snsmapper.selectImgPostCnt();
		 int nowPage = pageNum;
		 int startNum=0;
		 int endNum=15;
		 int numPerPage=15; 
		 int totalPage = (int)Math.ceil((float)totalCnt/(float)numPerPage);
		 
		 System.out.println("totalPage: "+ totalPage);
		 if (pageNum==1){
			 
			 startNum = 1;
			 endNum = numPerPage ;
			 
		 }else{
			 startNum = (pageNum * numPerPage)-numPerPage;
			 endNum = numPerPage;
		} 
		 
		 List<PostDTO> nextImgPost = null;
			
			
			if("".equals(keyword)&&"".equals(keyField)) {
				System.out.println("next: "+totalPage+" "+nowPage);
				
				if(totalPage>=nowPage) {
				nextImgPost = snsservice.selectImgPost(startNum,endNum);  
				}
				return nextImgPost;
				
				}else{ //검색처리
					
					String searchText=" 기준 검색결과";
					model.addAttribute("keyFieldSearch",keyField+searchText);
					
					model.addAttribute("keyword", keyword);
					model.addAttribute("keyField", keyField);
					 
					nextImgPost = snsservice.searchSNS(keyField, keyword,startNum,endNum);
				    return nextImgPost;
				}
			
			 
	}
		 
 
	
	//팔로우 페이지 이동
	@RequestMapping(value = "/follower.do")
	public ModelAndView selectFollow(Model model, @RequestParam(value = "fol") String fol,  ModelAndView mav) {
		
		
		  String USERID="kosta"; //계정 정보 출력 = 팔로워,팔로잉,프로필사진,식물카테고리 
		  List<AccountDTO> AccInfo = snsservice.selectMainAccINfo(USERID); 
		  model.addAttribute("AccInfo", AccInfo); List<PlantsCateDTO> CateList = snsservice.selectMainCate(USERID);
		  model.addAttribute("CateList",CateList);
		  
		  model.addAttribute("fol",fol);
		  
		 
		
		mav.setViewName("sns/followList");
		return mav;
	}
 
}
