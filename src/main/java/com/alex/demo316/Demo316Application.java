package com.alex.demo316;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


public class Demo316Application {
    public static void main(String[] args) {
        String URL = "http://94.198.50.185:7081/api/users";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> getRequest = new HttpEntity<>(headers);
        ResponseEntity<User[]> response = restTemplate.exchange(URL, HttpMethod.GET, getRequest, User[].class);
        String sessionId = response.getHeaders().get("Set-Cookie").get(0);
        System.out.println(sessionId);
        headers.set("Cookie", sessionId);

        User newUser = new User();
        newUser.setId(3L);
        newUser.setName("James");
        newUser.setLastName("Brown");
        newUser.setAge((byte) 23);
        HttpEntity<User> addRequest = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> addResponse = restTemplate.exchange(URL, HttpMethod.POST, addRequest, String.class);
        System.out.println(addResponse.getBody());

        newUser.setName("Thomas");
        newUser.setLastName("Shelby");
        HttpEntity<User> updateRequest = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> updateResponse = restTemplate.exchange(URL, HttpMethod.PUT, updateRequest, String.class);
        System.out.println(updateResponse.getBody());

        HttpEntity<Void> deleteRequest = new HttpEntity<>(headers);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, deleteRequest, String.class);
        System.out.println(deleteResponse.getBody());

        String code = addResponse.getBody() + updateResponse.getBody() + deleteResponse.getBody();
        System.out.println(code);
    }
}
