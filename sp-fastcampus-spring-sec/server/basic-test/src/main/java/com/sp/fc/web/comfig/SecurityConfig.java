package com.sp.fc.web.comfig;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 사용자가 추가될 때!
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser(User.builder()
                        .username("user2")
                        .password(passwordEncoder().encode("2222"))
                        .roles("USER")
                ).withUser(User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("3333"))
                        .roles("ADMIN")
                );

    }
    // encoder
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    // 모든페이지를 다 막아버리는게 spring-security..
    // 이 부분은 아래와 같은 것으로 해결가능
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // 어떤 request든 인증 받은 상태라는 설정
        //http.authorizeRequests((requests) ->
        //        requests.antMatchers("/").permitAll() // 모든 페이지를 허용
        //            .anyRequest().authenticated()
        //        // requests..anyRequest().authenticated() 를 하게 된다면 다시 인증이 필요하게 된다.
        //);
        //http.formLogin();
        //http.httpBasic();
        http
                .headers().disable()
                .csrf().disable()
                .formLogin(login ->
                                login.defaultSuccessUrl("/",false)
                )
                .logout().disable()
                .requestCache().disable();
    }

}
