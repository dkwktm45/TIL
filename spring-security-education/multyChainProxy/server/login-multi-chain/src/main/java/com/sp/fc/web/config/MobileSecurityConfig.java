package com.sp.fc.web.config;

import com.sp.fc.student.StudentManager;
import com.sp.fc.teacher.TeacherManager;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Order(1)
@ComponentScan(basePackages={"com.sp.fc.student","com.sp.fc.controller","com.sp.fc.teacher"})
@Configuration
public class MobileSecurityConfig extends WebSecurityConfigurerAdapter {

    private final StudentManager studentManager;
    private final TeacherManager teacherManager;

    public MobileSecurityConfig(StudentManager studentManager, TeacherManager teacherManager) {
        // 쌓는 형태이기 때문에 이렇게 생성이 된다.
        this.studentManager = studentManager;
        this.teacherManager = teacherManager;
    }

    // 만든 manager를 사용하기 위해선 config에 등록을 시켜줘야 한다.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(studentManager);
        auth.authenticationProvider(teacherManager);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/**")
                .csrf().disable()
                .authorizeRequests(request->
                        request.anyRequest().authenticated()
                )
                // httpBasic 은 BasicAuthenticationFilter가 작동을하고
                // BasicAuthenticationFilter 는 기본적으로 usernamefilter도 사용
                // 그렇기에 usernamefilter도 핸들링이 필요로 하다.
                .httpBasic()
        ;
    }
}
