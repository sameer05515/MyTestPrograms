package com.p.consume.rest.flux;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.main.web-application-type=none",
        "app.run-on-startup=false"
})
class ApplicationTests {

    @Test
    void contextLoads() {
    }
}
