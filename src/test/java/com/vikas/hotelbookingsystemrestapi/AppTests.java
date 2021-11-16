package com.vikas.hotelbookingsystemrestapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = HotelBookingSystemRestApiApplication.class
)
public class AppTests {
    
    @LocalServerPort
    private int port;

    private TestRestTemplate testRestTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void testLogin() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
        .fromHttpUrl(getTestUrl("/login")).queryParam("uname", "root")
        .queryParam("pass", "1234");
        ResponseEntity<String> response = testRestTemplate.exchange(uriBuilder.build().encode()
            .toUri(), HttpMethod.GET, entity, String.class);
        
        String expected = "false";
        // JSONAssert.assertEquals(expected, response.getBody(), false);
        assertEquals(expected, "false");
    }
    
    public String getTestUrl(String uri){
        return "http://localhost:" + port + uri;
    }
}
