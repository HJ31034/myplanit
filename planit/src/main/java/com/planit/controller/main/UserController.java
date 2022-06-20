package com.planit.controller.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.planit.domain.main.UserDTO;
import com.planit.service.main.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor

public class UserController {

	@Autowired
	UserService userService;


	

	@GetMapping("join")
	public String register() {
		return"/login/register";
	}
	
	// 회원가입 작동
	@PostMapping("join")
	public String join(Model model, UserDTO userdto) throws Exception{
		userService.joinUser(userdto);
		
		
		//System.out.println("이거 보이면 콘솔에 찍히면 성공 ");
		log.info("join: userdto", userdto);
		return "redirect:/hi";
	
	}
	//중복체크
	@ResponseBody
	@GetMapping("idchk")
	public String userCheck(UserDTO userdto) throws Exception{
	userService.idChk(userdto.getUserId());
	return "rediect:/";}
	
	
	// 로그인 폼
	@GetMapping("login")
	public String loginForm() {
		//System.out.println("login page");
		return "/login/loginForm";
	}


	// 로그인 작동
	@PostMapping("login")
	public String login(@Valid @ModelAttribute UserDTO userdto, 
			Model model,
			BindingResult bindingResult,
			HttpServletRequest request) throws Exception {



		UserDTO userInfo = userService.userLogin(userdto.getUserId(), userdto.getPassword());
		
		
		if (userInfo == null) {
			//bindingResult.reject("loginFail", "다시 로그인 ");
			System.out.println("login Fail ");
			model.addAttribute("msg", "아이디와 비번호를 다시 입력해 주세요!");
			return "/login/loginForm";
		} else {
		
		// 세션처리
		HttpSession sessionInfo = request.getSession();
		sessionInfo.setAttribute(SessionController.loginSession, sessionInfo);
		sessionInfo.setAttribute("session",sessionInfo);
		sessionInfo.setAttribute("id", userdto.getUserId());
		System.out.println("Controller: Session-working");

		System.out.println("controller: 로그인 작동! ");
		return "redirect:/hi";}

	}

	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {

		HttpSession sessionInfo = request.getSession(false);
		if (sessionInfo != null) {
			sessionInfo.invalidate();
		System.out.println("signout working successfully.");
		}

		return "redirect:/hi";
	}

	@GetMapping("/hi")
	public String test(HttpServletRequest request, @ModelAttribute UserDTO userdto, Model model) {
		
		System.out.println("hi");
		return "login/loginSuccess";
	}
	
	
	
	@GetMapping("loginFail")
	public String loginFail() {
		System.out.println("로그인 실패");
		return "login/loginFail" ;
	}
}
