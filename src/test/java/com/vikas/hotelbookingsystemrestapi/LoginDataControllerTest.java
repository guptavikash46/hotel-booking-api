package com.vikas.hotelbookingsystemrestapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vikas.hotelbookingsystemrestapi.controller.LoginDataController;
import com.vikas.hotelbookingsystemrestapi.others.NewUser;
import com.vikas.hotelbookingsystemrestapi.service.LoginDataService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LoginDataController.class)
@WithMockUser
public class LoginDataControllerTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private LoginDataService loginDataService;

    @Test
    public void registerUser() throws Exception {
        NewUser newuser = new NewUser("root", "1234");
        
        Mockito.when(
            loginDataService.registerNewUser(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(true);
        
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register")
            .content("{\"uname\":\"vikas\",\"pass\":\"henlo\"}")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        logger.warn("test result", mvcResult.getResponse().getContentAsString());
        // Map<String, String> expected = new HashMap<>();
        // expected.put("message","User created successfully");
        String expect = "{\"message\":\"User created successfully\"}"; 
        assertEquals(expect, mvcResult.getResponse().getContentAsString());
        //JSONAssert.assertEquals(expect, String.valueOf(mvcResult.getResponse()), false);

    }
}
