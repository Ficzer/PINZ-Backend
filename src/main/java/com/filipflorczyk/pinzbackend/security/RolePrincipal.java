package com.filipflorczyk.pinzbackend.security;

import com.filipflorczyk.pinzbackend.entities.UserRole;
import org.springframework.security.core.GrantedAuthority;

public class RolePrincipal implements GrantedAuthority {

    private UserRole userRole;

    public RolePrincipal(UserRole userRole){
        this.userRole = userRole;
    }

    @Override
    public String getAuthority() {
        return userRole.getName();
    }
}
