package com.sp.fc.student;

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
import java.util.stream.Collectors;

@Component
public class StudentManager implements AuthenticationProvider, InitializingBean {

    private HashMap<String , Student> studentDB = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication instanceof UsernamePasswordAuthenticationToken){
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            if(studentDB.containsKey(token.getName())){
                return getStudentAuthenticationToken(token.getName());
            }
            return null;

        }
        StudentAuthenticationToken token = (StudentAuthenticationToken) authentication;
        if(studentDB.containsKey(token.getCredentials())){
            return getStudentAuthenticationToken(token.getCredentials());
        }
        return null;
    }

    private StudentAuthenticationToken getStudentAuthenticationToken(String id) {
        Student student = studentDB.get(id);
        return StudentAuthenticationToken.builder()
                .principal(student)
                .details(student.getUsername())
                .authenticated(true) // 내가 처리를 할 수 없을 때는 false가 아닌 null로 보내야 한다.
                .build();
    }

    // student에게 있는 선생이 같은지 확인하고 학생목록을 가져오게 된다.
    public List<Student> myStudentList(String teacherId){
        return studentDB.values().stream().filter(s->s.getTeacherId().equals(teacherId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // UsernamePasswordAuthenticationToken 의 class로 리턴 해주면 우리가 동작하는
        // provider 로 선언이 된다.
        return authentication == StudentAuthenticationToken.class ||
                authentication == UsernamePasswordAuthenticationToken.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 인위적으로 값을 넣는 구간
        Set.of(
                new Student("hong","홍길동",Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")),"choi"),
                new Student("kim","김민섭",Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")),"choi"),
                new Student("rang","호랑이",Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")),"choi")

        ).forEach(s ->
                studentDB.put(s.getId() ,s)
        );


    }
}