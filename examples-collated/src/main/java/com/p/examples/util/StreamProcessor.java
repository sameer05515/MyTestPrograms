package com.p.examples.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class StreamProcessor {

    /**
     * Calculates the average age of a list of Person objects.
     *
     * @param people the list of Person objects
     * @return the average age of the people in the list, or 0.0 if the list is empty
     */
    public double averageAge(List<Person> people) {
        return people.stream()
                .mapToInt(Person::getAge)
                .average()
                .orElse(0.0);
    }

    /**
     * Groups a list of Person objects by a specific attribute and counts the number of objects in each group.
     *
     * @param people     the list of Person objects to be grouped
     * @param classifier the function to classify the Person objects by a specific attribute
     * @return a map where the keys are the attribute values and the values are the counts of Person objects in each group
     */
    public Map<String, Long> groupAndCount(List<Person> people, Function<Person, String> classifier) {
        return people.stream()
                .collect(Collectors.groupingBy(classifier, Collectors.counting()));
    }

    /**
     * Filters out strings that contain a specific substring from a list of strings.
     *
     * @param strings   the list of strings to be filtered
     * @param substring the substring to filter out
     * @return a list of strings that do not contain the specified substring
     */
    public List<String> filterStrings(List<String> strings, String substring) {
        return strings.stream()
                .filter(s -> s.contains(substring))
                .collect(Collectors.toList());
    }

    /**
     * Finds the maximum value in a list of integers.
     *
     * @param numbers the list of integers
     * @return the maximum value in the list, or Integer.MIN_VALUE if the list is empty
     */
    public int findMax(List<Integer> numbers) {
        return numbers.stream()
                .max(Integer::compareTo)
                .orElse(Integer.MIN_VALUE);
    }

    /**
     * Finds the first n elements of a list.
     *
     * @param list the list from which to extract the elements
     * @param n    the number of elements to extract
     * @param <T>  the type of elements in the list
     * @return a list containing the first n elements of the input list
     */
    public <T> List<T> findFirstNElements(List<T> list, int n) {
        return list.stream()
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Flattens a list of lists into a single list.
     *
     * @param lists the list of lists to be flattened
     * @param <T>   the type of elements in the lists
     * @return a single list containing all the elements from the nested lists
     */
    public <T> List<T> flatten(List<List<T>> lists) {
        return lists.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     * Finds the longest string in a list of strings.
     *
     * @param strings the list of strings
     * @return the longest string in the list, or null if the list is empty
     */
    public String findLongestString(List<String> strings) {
//        return strings.stream().max((s1, s2) -> Integer.compare(s1.length(), s2.length()))
//                .orElse(null);
        return strings.stream().max(Comparator.comparingInt(String::length))
                .orElse(null);
    }

    /**
     * Converts a list of objects to a map where the key is a specific attribute and the value is the object itself.
     *
     * @param list      the list of objects to be converted
     * @param keyMapper the function to extract the key from the object
     * @param <T>       the type of objects in the list
     * @return a map where the keys are the specified attribute values and the values are the objects themselves
     */
    public <T> Map<String, T> toMap(List<T> list, Function<T, String> keyMapper) {
        return list.stream()
                .collect(Collectors.toMap(keyMapper, Function.identity()));
    }

    /**
     * Calculates the sum of squares of a list of integers.
     *
     * @param numbers the list of integers
     * @return the sum of squares of the integers in the list
     */
    public int sumOfSquares(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(n -> n * n)
                .sum();
    }

    /**
     * Filters out null values from a list of objects.
     *
     * @param list the list of objects
     * @param <T>  the type of objects in the list
     * @return a list containing only non-null objects from the input list
     */
    public <T> List<T> filterNulls(List<T> list) {
        return list.stream()
                .filter(item -> item != null)
                .collect(Collectors.toList());
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Person {
    private int age;
    private String attribute;
}
