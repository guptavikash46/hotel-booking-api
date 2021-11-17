package com.vikas.hotelbookingsystemrestapi.service;

import com.vikas.hotelbookingsystemrestapi.entity.LoginData;
import com.vikas.hotelbookingsystemrestapi.repository.LoginDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginDataService implements UserDetailsService {

    @Autowired
    private LoginDataRepository loginDataRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LoginData loginData = loginDataRepository.getUserDetails(s);
        return new CustomLoginDataService(loginData.getUsername(), loginData.getPassword(), loginData.isEnabled(),
                loginData.isAccountNonExpired(), loginData.isCredentialsNonExpired(), loginData.isAccountNonLocked(),
                loginData.getAuthorities());
    }

    public boolean registerNewUser(String uname, String pass) {
        return loginDataRepository.registerNewUser(uname, bCryptPasswordEncoder.encode(pass));
    }
}
