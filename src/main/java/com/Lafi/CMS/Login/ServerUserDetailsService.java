package com.Lafi.CMS.Login;

import com.Lafi.CMS.DataBaseAccess.UserAccess;
import com.Lafi.CMS.Models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service


public class ServerUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccess userAccess = new UserAccess();
        User user = userAccess.readByEmail(email);
        LoginUser loginUser = new LoginUser(user);
        return loginUser;
    }
}

