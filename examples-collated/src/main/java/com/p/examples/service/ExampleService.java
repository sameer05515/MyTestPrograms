package com.p.examples.service;

import com.p.examples.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    private final Util util;



    @Autowired
    public ExampleService(Util util) {
        this.util = util;
    }

    public boolean checkPalindrome(String given) {
        return util.check(given);
    }
}
