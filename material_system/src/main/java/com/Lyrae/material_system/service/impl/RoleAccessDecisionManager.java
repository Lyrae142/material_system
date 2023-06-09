package com.Lyrae.material_system.service.impl;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("roleAccessDecisionManager")
public class RoleAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        System.out.println("-----------decide-----------");
        //如果登录成功,则信息会存储在Authorities中
        Collection<? extends GrantedAuthority> myRoles = authentication.getAuthorities();
        //如果前面的getAttributes()返回非空,则返回的数据作为形参传入,如果返回的为null,则不会进入decide()直接放行

        System.out.println("myRole:" + myRoles);
        System.out.println("systemRole:" + configAttributes);

        for (GrantedAuthority myRole: myRoles){ //当前登录的角色
            for (ConfigAttribute urlRoles: configAttributes){ //前台传入的url的角色,UrlDaoImpl.getAttributes获得的
                if (myRole.getAuthority().equals(urlRoles.getAttribute())){
                    //说明此URL地址符合权限,可以放行
                    return;
                }
            }
        }
        System.out.println("-----------权限验证失败-----------");
        throw new AccessDeniedException("权限越界!");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
