package com.Lafi.CMS.Login;

import com.Lafi.CMS.Models.User;
import org.springframework.security.core.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class LoginUser implements UserDetails {

    private User user;

    public LoginUser(User user) {
        super();
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("Student"),
                new SimpleGrantedAuthority("Instructor")
                , new SimpleGrantedAuthority("Admin"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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