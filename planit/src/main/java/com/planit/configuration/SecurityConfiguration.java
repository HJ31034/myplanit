package com.planit.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.planit.mapper.main.UserMapper;
import com.planit.service.main.UserService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)

public class SecurityConfiguration {
   
   @Autowired
   UserService userService;
  @Autowired
  UserMapper userMapper;
 
  
  @Autowired
  public void SecurityConfig(UserService userService) {
      this.userService = userService;
  }
   
   
   @Bean //인증 무시하는 경로들
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
           return (web) -> web.ignoring().antMatchers
("/resources", "/css/**" , "/assets/**", "/fonts/**", "/img/**", "/js/**", "/plugin/**", "/data/**", "/scripts/**", "/scss/**", "/vender/**"); //static 디렉터리 하위 파일 목윽은 인증 무시
       }
   
   
   
   @Bean
   @Order(SecurityProperties.BASIC_AUTH_ORDER)
   protected  SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
      http
      
      .csrf().disable()
        .authorizeRequests()
            .antMatchers("/**/**","/static/**").permitAll() //누구나 접근 가능
            .antMatchers("/userAccess").hasRole("USER")
            .anyRequest().authenticated()
            .and()
        .formLogin()
        	.usernameParameter("userId")
        	.passwordParameter("password")
            .loginPage("/login").permitAll() //로그인 페이지 링크
            .loginProcessingUrl("/login") //로그인 처리 url
            .defaultSuccessUrl("/user_access") //로그인 성공시 이동하는 페이지 등록
            .failureUrl("/login")
            .and()
        .logout()
           .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //로그아웃 경로를 지정
           .logoutSuccessUrl("/planit")  //로그아웃 성공시 경로를 지정
           .invalidateHttpSession(true); //로그아웃 성공시 세션을 제거
      
      
      return http.build();   }
   
   
   
   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }
   
//   @Bean
//   public PasswordEncoder passwordEncoder() {
//      return new BCryptPasswordEncoder();
//   }
//   
   
   
   

   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
   }
   
   protected void configure(HttpSecurity http) throws Exception {
	   
   }
//   public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//       User principal = userMapper.findByUsername(userId)
//               .orElseThrow(() ->{
//                   return new UsernameNotFoundException("해당 사용자를 찾을수 없습니다.:" + userId);
//               });
//       return new PrincipalDetail(principal); //시큐리티의 세션에 유저정보가 저장이됨. 
//   }
   

   
}