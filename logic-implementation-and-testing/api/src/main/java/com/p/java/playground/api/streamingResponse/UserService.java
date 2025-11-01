package com.p.java.playground.api.streamingResponse;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserService {

    public List<User> generateDummyUsers(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new User("User" + i, "user" + i + "@example.com", 20 + i))
                .collect(Collectors.toList());
    }
}
