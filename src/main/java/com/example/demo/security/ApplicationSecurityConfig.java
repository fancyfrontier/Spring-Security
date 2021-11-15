package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.auth.ApplicationUserService;

import static com.example.demo.security.ApplicationUserRole.*;

import java.util.concurrent.TimeUnit;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,
									 ApplicationUserService applicationUserService) {
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http	
				.csrf().disable()
				.authorizeRequests() //開始使用
				.antMatchers("/", "index", "/css/*", "/js/*").permitAll() //銜接用表給過(讓剛剛被指定的東西被怎樣)
				.antMatchers("/api/**").hasRole(STUDENT.name())
				.anyRequest() //剩下的要求都
				.authenticated() //必須通過驗證
				.and()
				.formLogin()
					.loginPage("/login")
					.permitAll()
					.defaultSuccessUrl("/courses", true)
					.passwordParameter("password")
					.usernameParameter("username")
				.and()
				.rememberMe() //直接設置記住我
					.tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21)) //設置記憶時間21天
					.key("somethingverysecured") //新增密鑰(後面會用到吧)
					.rememberMeParameter("remember-me")
				.and()
				.logout()
					.logoutUrl("/logout") //當連結到登出Controller時
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) //當.csrf().disable()時要使用，反之這行要刪掉
					.clearAuthentication(true) //清除認證資料
					.invalidateHttpSession(true) //讓Session失效
					.deleteCookies("JSESSIONID", "remember-me") //刪除Cookies
					.logoutSuccessUrl("/login"); //登出成功後的轉向
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);
		return provider;
	}
	
	
//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		UserDetails annaSmithUser = User.builder()
//				.username("annasmith")
//				.password(passwordEncorder.encode("password"))
////				.roles(STUDENT.name()) //ROLE_STUDENT
//				.authorities(STUDENT.getGrantedAuthorities())
//				.build();
//			
//		UserDetails lindaUser = User.builder()
//				.username("linda")
//				.password(passwordEncorder.encode("password123"))
////				.roles(ADMIN.name()) //ROLE_ADMIN
//				.authorities(ADMIN.getGrantedAuthorities())
//				.build();
//		
//		UserDetails tomUser = User.builder()
//				.username("tom")
//				.password(passwordEncorder.encode("password123"))
////				.roles(ADMINTRAINEE.name()) //ROLE_ADMINTRAINEE
//				.authorities(ADMINTRAINEE.getGrantedAuthorities())
//				.build();
//		
//		return new InMemoryUserDetailsManager(
//				annaSmithUser,
//				lindaUser,
//				tomUser
//		);
//	}

	
	
	
}
