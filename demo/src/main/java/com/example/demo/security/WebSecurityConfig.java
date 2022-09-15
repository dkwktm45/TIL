package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();

		configuration.addAllowedOrigin("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setMaxAge((long) 3600);
		configuration.setAllowCredentials(false);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable() // 기본값이 on인 csrf 취약점 보안을 해제한다. on으로 설정해도 되나 설정할경우 웹페이지에서 추가처리가 필요함.
				.headers()
				.frameOptions().sameOrigin() // SockJS는 기본적으로 HTML iframe 요소를 통한 전송을 허용하지 않도록 설정되는데 해당 내용을 해제한다.
				.and()
				.httpBasic()
				.and()
				.authorizeRequests()
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
					.antMatchers("/loginInsert").permitAll()			// 회원가입
				.anyRequest().hasRole("USER"); // 나머지 리소스에 대한 접근 설정
	}

	/**
	 * 테스트를 위해 In-Memory에 계정을 임의로 생성한다.
	 * <p>
	 * 서비스에 사용시에는 DB데이터를 이용하도록 수정이 필요하다.
	 */
	@Bean
	public UserDetailsService users() {
		// The builder will ensure the passwords are encoded before saving in memory
		User.UserBuilder users = User.withDefaultPasswordEncoder();
		UserDetails user = users
				.username("happydaddy")
				.password("{noop}1234")
				.roles("USER")
				.build();
		UserDetails admin = users
				.username("angrydaddy")
				.password("{noop}1234")
				.roles("USER")
				.build();
		UserDetails user4 = users
				.username("jin")
				.password("{noop}1234")
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user, admin,user4);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("happydaddy")
				.password("{noop}1234")
				.roles("USER")
				.and()
				.withUser("angrydaddy")
				.password("{noop}1234")
				.roles("USER")
				.and()
				.withUser("jin")
				.password("{noop}1234")
				.roles("USER")
				.and()
				.withUser("guest")
				.password("{noop}1234")
				.roles("GUEST");
	}

}