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

		if (errors.hasErrors()) {
			// 회원가입 실패시, 입력 데이터를 유지
			model.addAttribute("user", userdto);

			// 유효성 통과 못한 필드와 메시지를 핸들링
			Map<String, String> validatorResult = userService.validateHandling(errors);
			for (String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			}

			return "/login/register";
		}

		userService.joinUser(userdto);
//		if(userdto !=null) {
//			return "redirect:/join";
//		}
		System.out.println("이거 보이면 콘솔에 찍히면 성공 ");
		System.out.println("회원가입dto:" + userdto);
		log.info("join: userdto", userdto);
		return "redirect:/login";

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

	// 현재 비밀번호 조회
//	@RequestMapping(value = "/pwdCheck")
//	@ResponseBody
//	public int pwdCheck(@RequestParam(value = "password", required = false) String password,
//			@RequestParam(value = "userId", required = false) String userId, HttpSession session) {
//		session.getAttribute("userdto");
//		int count = userService.pwdCheck(password, userId);
//		System.out.println("현재 비밀번호 조회");
//		return count;
//	}

	// 로그인 폼
	@GetMapping("login")
	public String loginForm() {
		// System.out.println("login page");

		return "/login/login2";
	}

//	// 로그인 작동 메서드
//	@PostMapping("login")
//	public String login(@ModelAttribute UserDTO userdto, Model model, BindingResult bindingResult,
//			HttpServletRequest request) throws Exception {
//
//		UserDTO userInfo = userService.userLogin(userdto);
//
//		if (userInfo == null) {
//			// bindingResult.reject("loginFail", "다시 로그인 ");
//			// System.out.println("login Fail ");
//
////			request.setAttribute("msg", "로그인 해 주세요");
////			request.setAttribute("url", "/login");
////			return "alert";
//			model.addAttribute("msg", "아이디와 비번호를 다시 입력해 주세요!");
//			return "login/login2";
//		} else {
//
//			// 세션처리
//			HttpSession sessionInfo = request.getSession();
//			sessionInfo.setAttribute(SessionController.loginSession, sessionInfo);
//			sessionInfo.setAttribute("session", sessionInfo);
//			sessionInfo.setAttribute("userdto", userInfo);
//
//			sessionInfo.setAttribute("id", userdto.getUserId());
//
//			System.out.println("세션처리:" + userInfo);
//
//			System.out.println("controller: 로그인 작동! ");
//			// model.addAttribute("msg", "로그인 성공");
//			return "redirect:/planit";
//		}
//
//	}
	
	
	// 로그인 작동 메서드 after Security 
//		@PostMapping("login")
//		public String login(@ModelAttribute UserDTO userdto, Model model, BindingResult bindingResult,
//				HttpServletRequest request) throws Exception {
//
//			UserDTO userInfo = (UserDTO) userService.loadUserByUsername(userdto.getUserId());
//
//			if (userInfo == null) {
//	
//				model.addAttribute("msg", "아이디와 비번호를 다시 입력해 주세요!");
//				return "login/login2";
//			} 
//			
//			return "redirect:/planit";
//		}

		//시큐리티 로그인 처리 ;;;
		 @GetMapping("/user_access")
		    public String userAccess(Model model, Authentication authentication, HttpServletRequest request) {
		        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
		        UserDTO userdto = (UserDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
		        if(userdto == null) {
		        	model.addAttribute("msg", "아이디와 비번호를 다시 입력해 주세요!");
					return "redirect:/login";
		        }
		        model.addAttribute("info", userdto.getUsername()+ "님" +"로그인은 성공이요");      //유저 아이디
		        HttpSession sessionInfo = request.getSession();
				sessionInfo.setAttribute(SessionController.loginSession, sessionInfo);
				sessionInfo.setAttribute("session", sessionInfo);
				sessionInfo.setAttribute("userdto", userdto);
		        return "/login/loginSuccess";
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
		//userdto.setUserId((String) session.getAttribute("id"));
		userService.userInfo(userdto);

		return "/login/update/userInfo";
	}

	// 회원정보 수정
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
