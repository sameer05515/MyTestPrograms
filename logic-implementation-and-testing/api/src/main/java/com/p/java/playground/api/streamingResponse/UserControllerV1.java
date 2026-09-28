package com.p.java.playground.api.streamingResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
public class UserControllerV1 {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users/stream/v1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBodyEmitter streamDummyUsersV1() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        new Thread(() -> {
            try {
                // Generate 20 dummy users
                List<User> users = userService.generateDummyUsers(20);

                // Stream each user one by one
                for (User user : users) {
                    emitter.send(user);
                    Thread.sleep(200); // Simulate processing delay (optional)
                }
                emitter.complete();
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e);
            }
        }).start();

        return emitter;
    }

    @GetMapping(value = "/users/stream/v2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBodyEmitter streamDummyUsersV2() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        new Thread(() -> {
            try {
                // Generate 20 dummy users
                List<User> users = userService.generateDummyUsers(20);

                // Stream each user one by one
                for (User user : users) {
                    emitter.send(user); // Send each user as it's generated
                    Thread.sleep(200);    // Send each chunk with a delay of 200 s
                }
                emitter.complete(); // Mark the streaming as complete
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e); // Handle any exceptions
            }
        }).start();

        return emitter;
    }

    @GetMapping(value = "/users/stream/v3", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StreamingResponseBody> streamDummyUsersV3() {
        StreamingResponseBody responseBody = outputStream -> {
            List<User> users = userService.generateDummyUsers(20);

            // Stream each user one by one
            for (User user : users) {
                String userJson = convertToJson(user) + "\n";
                outputStream.write(userJson.getBytes());
                outputStream.flush();

                try {
                    Thread.sleep(200); // 200 ms delay between user chunks
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            outputStream.close();
        };

        return ResponseEntity.ok().body(responseBody);
    }

    private String convertToJson(User user) {
        // You can use a library like Jackson here to convert the user object to JSON
        return "{\"name\":\"" + user.getName() + "\",\"email\":\"" + user.getEmail() + "\",\"age\":" + user.getAge() + "}";
    }
}
