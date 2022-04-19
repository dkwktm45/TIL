package com.sp.fc.web.config;

import lombok.Getter;
import lombok.Setter;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

@Getter
@Setter
public class CustomMethodSecurityExpression extends SecurityExpressionRoot
    implements MethodSecurityExpressionOperations {
    MethodInvocation invocation;
    public CustomMethodSecurityExpression(Authentication authentication, MethodInvocation invocation) {
        super(authentication);
        this.invocation = invocation;
    }

    // prefilter 에서 쓰도록 하기 위해 필요!
    private Object filterObject;

    // afterInvocation 에서 사용하기 위함!
    private Object returnObject;

    public boolean isStudent(){
        // hasRole 과 다를바가 없다고 생각하자
        return getAuthentication().getAuthorities().stream()
                .filter(a -> a.getAuthority().equals("ROLE_STUDENT"))
                .findAny().isPresent();
    }
    public boolean isTutor(){
        // hasRole 과 다를바가 없다고 생각하자
        return getAuthentication().getAuthorities().stream()
                .filter(a -> a.getAuthority().equals("ROLE_TUTOR"))
                .findAny().isPresent();
    }
    @Override
    public Object getThis() {
        return null;
    }
}
