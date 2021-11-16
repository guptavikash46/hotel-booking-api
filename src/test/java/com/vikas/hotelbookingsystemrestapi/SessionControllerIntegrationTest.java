package com.vikas.hotelbookingsystemrestapi;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotelBookingSystemRestApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SessionControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private Jedis jedis;
    private TestRestTemplate testRestTemplate;
    private TestRestTemplate testRestTemplateWithAuth;

    private Logger logger = LoggerFactory.getLogger(getClass());
//    @BeforeClass
//    public static void startRedisServer() throws IOException {
//        redisServer = new RedisServer(6379);
//        redisServer.start();
//    }
//    @AfterClass
//    public static void stopRedisServer() {
//        redisServer.stop();
//    }
    @Before
    public void clearRedisData() {
        testRestTemplate = new TestRestTemplate();
        testRestTemplateWithAuth = new TestRestTemplate("admin", "password");

        jedis = new Jedis("localhost", 6379);
        jedis.flushAll();
    }
    @Test
    public void testRedisIsEmpty() {
        Set<String> result = jedis.keys("*");
        assertEquals(0, result.size());
        logger.info(result.toString());
    }
    @Test
    public void testUnauthenticatedCantAccess() {
        ResponseEntity<String> result = testRestTemplate.getForEntity(getTestUrl(), String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        logger.info("unathourized access: "+result.toString());
    }
    @Test
    public void testRedisControlSession() {
        ResponseEntity<String> result = testRestTemplateWithAuth.getForEntity(getTestUrl(), String.class);
        assertEquals(true, result.getBody()); //login worked
        logger.info("access granted: "+result.toString());

        Set<String> redisData  = jedis.keys("*");
        assertTrue(redisData.size() > 0);  //values are populated in redis
        logger.info("redis data: "+redisData);

        String sessionCookie = result.getHeaders().get("Set-Cookie").get(0).split(",")[0];
        logger.info("session cookie: "+sessionCookie.toString());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", sessionCookie);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        result = testRestTemplateWithAuth.exchange(getTestUrl(), HttpMethod.GET, httpEntity, String.class);
        assertEquals(true, result.getBody());  //access with session data works

        jedis.flushAll(); //remove all entries from redis

        result = testRestTemplate.exchange(getTestUrl(), HttpMethod.GET, httpEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());// access denied after sessions are removed in redis
    }

    private String getTestUrl(){
        return "http://localhost:" + port+"/login?uname=vikas&pass=1234";
    }


}
