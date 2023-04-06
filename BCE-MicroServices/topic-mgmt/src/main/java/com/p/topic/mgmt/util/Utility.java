package com.p.topic.mgmt.util;

import org.apache.commons.lang3.RandomStringUtils;

public class Utility {
    public static String generateUniqueId() {
        return RandomStringUtils.randomAlphabetic(20);
    }
}
