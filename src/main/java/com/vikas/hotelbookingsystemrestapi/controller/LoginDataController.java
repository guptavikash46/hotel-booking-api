package com.vikas.hotelbookingsystemrestapi.controller;

import com.vikas.hotelbookingsystemrestapi.others.NewUser;
import com.vikas.hotelbookingsystemrestapi.service.LoginDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class LoginDataController {

    private Logger logger = LoggerFactory.getLogger(LoginDataController.class);
    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    private LoginDataService loginDataService;

    @GetMapping("/login")
    public ResponseEntity<Boolean> getUserDetails(@RequestParam("uname") String uname,
                                                  @RequestParam("pass") String pass) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(uname, pass);
        Authentication authenticated = daoAuthenticationProvider.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticated);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logoutUser() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> registerNewUser(@RequestBody NewUser newUser) {
        logger.info("Values from user: "+newUser.getUname()+", pass: "+newUser.getPass());
        Map<String, String> responseMsg = new HashMap<>();
        loginDataService.registerNewUser(newUser.getUname(), newUser.getPass());
        responseMsg.put("message","User created successfully");
        return new ResponseEntity<>(responseMsg, HttpStatus.CREATED);
    }
}
