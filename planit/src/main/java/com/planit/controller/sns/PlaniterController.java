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

import com.planit.domain.main.UserDTO;
import com.planit.domain.sns.AccountDTO;
  
import com.planit.domain.sns.PlantsCateDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.mapper.sns.SnsMapper;
import com.planit.service.main.UserService;
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
			@RequestParam(value="keyword", required = false) String keyword, HttpSession session) {
	
		 
		List<PostDTO> ImgPost = null;
		String USERID = null;
		
		if (session.getAttribute("id") != null) {
			USERID = session.getAttribute("id").toString();
		}else {
			USERID="guest";
		}
		model.addAttribute("sessionId",USERID);
        
		if(keyword==null||keyField==null) {
			
		 ImgPost = snsservice.selectImgPost(0,15);
		 model.addAttribute("ImgPost", ImgPost);
		 
		}else if(keyword!=null||keyField!=null){//검색처리
			String searchText=" 기준 검색결과";
			model.addAttribute("keyFieldSearch",keyField+searchText);
			model.addAttribute("keyword", keyword);
			model.addAttribute("keyField", keyField);
  
		 if(keyField=="계정" || "계정".equals(keyField)) {//계정검색
			 ImgPost = snsservice.searchSNS(keyField, keyword,0,15);
			 
		 }else if("식물".equals(keyField) || "내용".equals(keyField)){//식물,내용 검색
			 ImgPost = snsservice.searchSNS(keyField, keyword,0,15);
		 }
		 	model.addAttribute("ImgPost", ImgPost);
		 }
		 
		//계정 정보 출력 = 팔로워,팔로잉,프로필사진,식물카테고리 
		model.addAttribute("AccInfo",snsservice.selectMainAccINfo(USERID));
		model.addAttribute("CateList",snsservice.selectMainCate(USERID));
		 
		
		return "sns/index";
	}
	
  
	@ResponseBody
	@RequestMapping(value = "/nextImgPost")
	public  List<PostDTO> nextImgPost(@RequestParam(value="page", required = false) String page,
									  @RequestParam(value="keyField",required = false) String keyField,
									  @RequestParam(value="keyword", required = false) String keyword, HttpServletRequest request,Model model) {
  
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
				if(totalPage>=nowPage) {
				nextImgPost = snsservice.selectImgPost(startNum,endNum);  
				}
				 
				
			}else{ //검색처리 
					String searchText=" 기준 검색결과";
					model.addAttribute("keyFieldSearch",keyField+searchText); 
					model.addAttribute("keyword", keyword);
					model.addAttribute("keyField", keyField);
					 
					nextImgPost = snsservice.searchSNS(keyField, keyword,startNum,endNum);
				}
			 return nextImgPost;
				}
		 
 
	
	//팔로우 페이지 이동
	@RequestMapping(value = "/follower.do")
	public ModelAndView selectFollow(Model model, @RequestParam(value = "fol") String fol, 
									 ModelAndView mav, HttpSession session,
									 @RequestParam(value="otherUserId",required = false)String otherUserId) {
		
		
        String USERID = "";
		
        if (otherUserId == null || "".equals(otherUserId)) {
			USERID = session.getAttribute("id").toString();
		}else {
			USERID=otherUserId;
		}
		  System.out.println("selectFollow USERID: "+USERID);
		  model.addAttribute("USERID",USERID);
		  model.addAttribute("sessionId", session.getAttribute("id").toString());
		  
		  //계정 정보 출력 = 팔로워,팔로잉,프로필사진,식물카테고리 
		  model.addAttribute("AccInfo", snsservice.selectMainAccINfo(USERID)); 
		  model.addAttribute("CateList",snsservice.selectMainCate(USERID));
		  //팔로잉페이지인지 팔로워페이지인지 구분
		  model.addAttribute("fol",fol);
		   
		  mav.setViewName("sns/followList");
		  return mav;
	}
 
	
}
