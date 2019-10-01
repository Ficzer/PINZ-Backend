package com.filipflorczyk.pinzbackend.security;

import com.filipflorczyk.pinzbackend.entities.User;
import com.filipflorczyk.pinzbackend.entities.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private User user;

    public UserPrincipal(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<UserRole> userRoleList = new ArrayList<>(user.getUserRoles());
        List<RolePrincipal> rolePrincipalList = new ArrayList<>();

        for (UserRole userRole: userRoleList){
            rolePrincipalList.add(new RolePrincipal(userRole));
        }

        return rolePrincipalList;
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
