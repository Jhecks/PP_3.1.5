package ru.jhecks;

import ru.jhecks.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

public class Application {

    private static final String URL = "http://94.198.50.185:7081/api/users";
    private static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, List.class);
        headers.set("Cookie", String.join(";",
                Objects.requireNonNull(responseEntity.getHeaders().get("Set-Cookie"))));

        User user1 = new User();
        user1.setId(3L);
        user1.setName("James");
        user1.setLastName("Brown");
        user1.setAge((byte) 73);

        HttpEntity<User> entity = new HttpEntity<>(user1, headers);
        System.out.print(restTemplate.exchange(URL, HttpMethod.POST, entity, String.class).getBody());

        user1.setName("Thomas");
        user1.setLastName("Shelby");
        entity = new HttpEntity<>(user1, headers);
        System.out.print(restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class).getBody());

        System.out.print(restTemplate.exchange(URL + "/3", HttpMethod.DELETE, entity, String.class).getBody());
    }
}
