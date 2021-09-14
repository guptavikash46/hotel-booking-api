package com.vikas.hotelbookingsystemrestapi.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomLoginDataService extends User {

    public CustomLoginDataService(String uname, String pass, boolean enabled, boolean accountNonExpired,
                                  boolean credentialsNonExpired, boolean accountNonLocked,
                                  Collection<? extends GrantedAuthority> authorities) {
        super(uname, pass, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
