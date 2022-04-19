package com.sp.fc.web.teacher;

import com.sp.fc.web.student.Student;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public class TeacherManager implements AuthenticationProvider, InitializingBean {

    private HashMap<String , Teacher> studentDB = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        if(studentDB.containsKey(token.getName())){
            Teacher teacher = studentDB.get(token.getName());
            return TeacherAuthenticationToken.builder()
                    .principal(teacher)
                    .details(teacher.getUsername())
                    .authenticated(true) // 내가 처리를 할 수 없을 때는 false가 아닌 null로 보내야 한다.
                    .build();
        }
        return null;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        // UsernamePasswordAuthenticationToken 의 class로 리턴 해주면 우리가 동작하는
        // provider 로 선언이 된다.
        return authentication == UsernamePasswordAuthenticationToken.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 인위적으로 값을 넣는 구간
        Set.of(
                new Teacher("choi","최선생",Set.of(new SimpleGrantedAuthority("ROLE_TEACHER")),null)

        ).forEach(s ->
                studentDB.put(s.getId() ,s)
        );


    }
}