package com.p.sort.hashmap;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class Main {
	public static void main(String[] args) {
		Map<Integer, Employee> map = new HashMap<>();

		map.put(1, new Employee(1, "ABC444", 35));
		map.put(2, new Employee(2, "ABC277", 36));
		map.put(3, new Employee(3, "ABC83", 37));
		map.put(4, new Employee(4, "ABC74", 38));
		map.put(5, new Employee(5, "ABC50000", 45));

		Map<Integer, Employee> hm1 = sortByValue(map);

		// print the sorted hashmap
		for (Map.Entry<Integer, Employee> en : hm1.entrySet()) {
			System.out.println("Key = " + en.getKey() + ", Value = " + en.getValue());
		}

//		Collections.sort(list, new Comparator<Map.Entry<Integer, Employee>>() {
//			@Override
//			public int compare(Entry<Integer, Employee> o1, Entry<Integer, Employee> o2) {
//
//				return (o1.getValue().getName()).compareTo(o2.getValue().getName());
//			}
//		});
//
//		Set<Integer> idSet = map.keySet();
//
//		for (int id : idSet) {
//			System.out.println(map.get(id));
//		}

	}

	// function to sort hashmap by values
	public static HashMap<Integer, Employee> sortByValue(Map<Integer, Employee> map) {
		// Create a list from elements of HashMap
		List<Map.Entry<Integer, Employee>> list = new LinkedList<Map.Entry<Integer, Employee>>(map.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<Integer, Employee>>() {
			public int compare(Map.Entry<Integer, Employee> o1, Map.Entry<Integer, Employee> o2) {
				return (o1.getValue().getName()).compareTo(o2.getValue().getName());
			}
		});

		// put data from sorted list to hashmap
		HashMap<Integer, Employee> temp = new LinkedHashMap<Integer, Employee>();
		for (Map.Entry<Integer, Employee> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}

}
