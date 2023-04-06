package com.p.examples.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@SpringBootTest
public class StreamProcessorTest {

    @Autowired
    StreamProcessor streamProcessor;

    @Test
    void test_averageAge() {
        List<Person> people = List.of(
                Person.builder().age(25).build(),
                Person.builder().age(30).build(),
                Person.builder().age(35).build()
        );
        System.out.println(streamProcessor.averageAge(people)); // Output: 30.0
    }

    @Test
    void test_filterStrings() {
        List<String> strings = List.of("apple", "banana", "cherry", "date", "elderberry");
        String substring = "ap";
        List<String> filteredStrings = streamProcessor.filterStrings(strings, substring);
        filteredStrings.forEach(System.out::println); // Output: cherry, elderberry
    }

    @Test
    void test_findMax() {
        List<Integer> numbers = List.of(3, 5, 7, 2, 8);
        System.out.println(streamProcessor.findMax(numbers)); // Output: 8
    }

    @Test
    void test_groupAndCount() {
        List<Person> people = List.of(
                Person.builder().attribute("group1").build(),
                Person.builder().attribute("group1").build(),
                Person.builder().attribute("group2").build(),
                Person.builder().attribute("group3").build());
        Map<String, Long> result = streamProcessor.groupAndCount(people, Person::getAttribute);

        result.forEach((key, count) -> System.out.println(key + ": " + count));
        // Output:
        // group1: 2
        // group2: 1
        // group3: 1
    }

    @Test
    void test_findFirstNElements() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> firstFive = streamProcessor.findFirstNElements(numbers, 5);
        System.out.println(firstFive); // Output: [1, 2, 3, 4, 5]
    }

    @Test
    void test_flatten() {
        List<List<Integer>> listOfLists = List.of(
                List.of(1, 2, 3),
                List.of(4, 5),
                List.of(6, 7, 8, 9)
        );
        List<Integer> flattenedList = streamProcessor.flatten(listOfLists);
        System.out.println(flattenedList); // Output: [1, 2, 3, 4, 5, 6, 7, 8, 9]
    }

    @Test
    void test_findLongestString() {
        List<String> strings = List.of("apple", "cherry", "banana", "date");
        System.out.println(streamProcessor.findLongestString(strings)); // Output: "banana"
    }

    @Test
    void test_toMap() {
        class Person {
            private String id;
            private String name;

            public Person(String id, String name) {
                this.id = id;
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            @Override
            public String toString() {
                return "Person{id='" + id + "', name='" + name + "'}";
            }
        }

        List<Person> people = List.of(
                new Person("1", "Alice"),
                new Person("2", "Bob"),
                new Person("3", "Charlie")
        );

       // ListToMapConverter converter = new ListToMapConverter();
        Map<String, Person> personMap = streamProcessor.toMap(people, Person::getId);

        personMap.forEach((key, value) -> System.out.println(key + ": " + value));
        // Output:
        // 1: Person{id='1', name='Alice'}
        // 2: Person{id='2', name='Bob'}
        // 3: Person{id='3', name='Charlie'}
    }

    @Test
    void test_sumOfSquares(){
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        System.out.println(streamProcessor.sumOfSquares(numbers)); // Output: 55
    }

    @Test
    void test_filterNulls(){
//        List<String> strings = List.of("apple", null, "banana", null, "cherry");
        List<String> strings = new ArrayList<>();
        strings.add("apple");
        strings.add(null);
        strings.add("banana");
        strings.add(null);
        strings.add("cherry");
        List<String> filteredStrings = streamProcessor.filterNulls(strings);
        System.out.println(filteredStrings); // Output: [apple, banana, cherry]
    }
}


