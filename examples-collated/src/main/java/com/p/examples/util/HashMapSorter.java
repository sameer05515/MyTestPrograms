package com.p.examples.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HashMapSorter {

    public static <K, V extends Comparable<V>> LinkedHashMap<K, V> sortByValue(HashMap<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));
        LinkedHashMap<K, V> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public static <K, V extends Comparable<V>> LinkedHashMap<K, V> sortByValueUsingLambda(HashMap<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        list.sort(Comparator.comparing(Map.Entry::getValue));
        LinkedHashMap<K, V> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
