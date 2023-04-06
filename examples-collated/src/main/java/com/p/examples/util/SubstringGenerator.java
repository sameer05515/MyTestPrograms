package com.p.examples.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SubstringGenerator {

    public static String[] generatePossibleSubstrings(String str) {
        String[] subs = null;
        if (str == null) {
            return null;
        }
        str = str.trim();
        if (str.length() <= 0) {
            return null;
        }

        Set<String> substringSet = new HashSet<String>();

        for (int i = 1; i <= str.length(); i++) {
            for (int j = 0; j <= str.length() - i; j++) {
                String subss = str.substring(j, j + i);
                substringSet.add(subss);
            }
        }

        List<String> substringList = new ArrayList<String>(substringSet);
        Collections.sort(substringList);

        subs = substringList.toArray(new String[0]);

        return subs;
    }
}
