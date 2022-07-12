package com.planit.controller.main;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping("planit/detail")
	String detail() {
		return "/login/fuleaf-detail";
	}

	// 회원가입 화면
	@GetMapping("join")
	public String register() {
		return "/login/register";
	}

	
	
	
	// 회원가입 작동
	@PostMapping("join")
	public String join(@Valid UserDTO userdto, Errors errors, Model model) {


		

		System.out.println("이거 보이면 콘솔에 찍히면 성공 ");
		System.out.println("회원가입dto:" + userdto);
		//System.out.println(userdto2);
		log.info("join: userdto", userdto);
		
		
		
		if (errors.hasErrors()) {
			// 회원가입 실패시, 입력 데이터를 유지
			model.addAttribute("user", userdto);

			// 유효성 통과 못한 필드와 메시지를 핸들링
			Map<String, String> validatorResult = userService.validateHandling(errors);
			for (String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			}

			return "/login/register";
			//return "";
		}
		
		
		
		
		
		userService.joinUser(userdto);


		System.out.println("이거 보이면 콘솔에 찍히면 성공 ");
		System.out.println("회원가입dto:" + userdto);
		log.info("join: userdto", userdto);
		return "redirect:/login";
		//return "";
	}

	
	
	
	
	
	
	
	
	
	// 아이디 체크
	@PostMapping("/idCheck")
	@ResponseBody
	public int idCheck(@RequestParam("userId") String userId) {
		log.info("userIdCheck 진입");
		log.info("전달받은 id:" + userId);
		int cnt = userService.idCheck(userId);
		log.info("확인 결과:" + cnt);
		return cnt;
	}

	// 로그인 폼
	@GetMapping("login")
	public String loginForm() {
		return "/login/loginForm";
	}
	// 로그인 실패폼
		@GetMapping("loginFail")
		public String loginFailForm(@RequestParam(value="error", required=false) String error,
				@RequestParam(value="exception", required=false) String exception,
				Model model) {
			// System.out.println("login page");
			model.addAttribute("error", error);
			model.addAttribute("exception", exception);
			return "/login/loginFail";
		}

	// 시큐리티 로그인 처리 ;;;
	@GetMapping("/login.do")
	public String userAccess(Model model, Authentication authentication, HttpServletRequest request) {
		// Authentication 객체를 통해 유저 정보를 가져올 수 있다.
		UserDTO userdto = (UserDTO) authentication.getPrincipal(); // userDetail 객체를 가져옴
		if (userdto == null) {
			model.addAttribute("msg", "아이디와 비번호를 다시 입력해 주세요!");
			return "redirect:/loginFail";
			
			//user_access
		}
		model.addAttribute("info", userdto.getUsername() + "님" + "로그인은 성공이요"); // 유저 아이디
		HttpSession sessionInfo = request.getSession();
		sessionInfo.setAttribute(SessionController.loginSession, sessionInfo);
		sessionInfo.setAttribute("session", sessionInfo);
		//sessionInfo.setAttribute("id", userdto.getUserId());
		sessionInfo.setAttribute("userdto", userdto);
		sessionInfo.setAttribute("id", userdto.getUserId());
		return "redirect:/planit";
	}

	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {

		HttpSession sessionInfo = request.getSession(false);
		if (sessionInfo != null) {
			sessionInfo.invalidate();
			System.out.println("signout working successfully.");
		}

		return "redirect:/planit";
	}

	// 회원 정보 페이지
	@GetMapping("/planit/userInfo")
	public String userInfo(HttpSession session, UserDTO userdto, Model model) throws Exception {

		session.getAttribute("userdto");
		System.out.println("회원정보:");
		// userdto.setUserId((String) session.getAttribute("id"));
		userService.userInfo(userdto);

		return "/login/update/userInfo";
	}

	// 회원정보 수정 페이지
	@GetMapping("/planit/edit.do")
	public String editUserPage(HttpSession session, UserDTO userdto) throws Exception {

		session.getAttribute("userdto");
		System.out.println("회원수정 페이지" + session.getAttribute("userdto"));

		return "/login/update/user-edit";
	}

	// 회원정보 수정 동작
	@PostMapping("/planit/edit.do")
	public String editUser(HttpSession session, @ModelAttribute UserDTO userdto, Model model) throws Exception {
		session.getAttribute("userdto");
		userService.infoCh(userdto);
	
		session.setAttribute("userdto", userdto);

		return "redirect:/planit/userInfo";

	}
	//관심항목 
	@GetMapping("/planit/interest.do")
	public String interest(HttpSession session) {
		session.getAttribute("userdto");
		return"/login/update/user-interest";
	}
	
	//관심항목 수정 동작
	@PostMapping("/planit/interest.do")
	public String interestEdit(HttpSession session, @ModelAttribute UserDTO userdto) {
		session.getAttribute("userdto");
		userService.interest(userdto);
		
		session.setAttribute("userdto", userdto);
		return"redirect:/planit/userInfo";
	}

	// 비밀번호 변경 페이지
	@GetMapping("planit/password.do")
	public String password() {
		return "/login/update/pass-edit";
	}

	// 비밀번호 변경 동작
	@PostMapping("/planit/password.do")
	public String editpwd(@ModelAttribute UserDTO userdto, HttpSession session, Model model) {

		session.getAttribute("userdto");

		System.out.println(userdto);
		userService.pwdCh(userdto);

		return "redirect:/planit/userInfo";
	}

}
