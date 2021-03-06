package com.sp.fc.web.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    AbstractSecurityInterceptor abstractSecurityInterceptor;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser(
                        User.withDefaultPasswordEncoder()
                                .username("user1")
                                .password("1111")
                                .roles("USER")
                );
    }

    AccessDecisionManager filterAccessDecisionManager(){
        return new AccessDecisionManager() {
            @Override
            public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
                throw new AccessDeniedException("접근 금지");
                // 아무 것도 하지 않고 return 하면 통과가 될 것이다.
                //return;
            }

            @Override
            public boolean supports(ConfigAttribute attribute) {
                // 어떤게 오든 모든 권한을 true : 통과
                return true;
            }

            @Override
            public boolean supports(Class<?> clazz) {
                // 해당 voter가 쓰일 거다.
                return FilterInvocation.class.isAssignableFrom(clazz);
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests(authority->authority
                        .mvcMatchers("/greeting").hasRole("USER")
                        .anyRequest().authenticated()
//                        .accessDecisionManager(filterAccessDecisionManager())
                );
    }
}

