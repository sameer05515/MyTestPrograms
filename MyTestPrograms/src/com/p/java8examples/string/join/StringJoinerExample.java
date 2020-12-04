package com.p.java8examples.string.join;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class StringJoinerExample {

	public static void main(String[] args) {

		StringJoiner sj = new StringJoiner(",");
		sj.add("aaa");
		sj.add("bbb");
		sj.add("ccc");
		String result = sj.toString(); // aaa,bbb,ccc

		StringJoiner sj2 = new StringJoiner("/", "prefix-", "-suffix");
		sj2.add("2016");
		sj2.add("02");
		sj2.add("26");
		String result2 = sj2.toString(); // prefix-2016/02/26-suffix

		// 2015-10-31
		String result3 = String.join("-", "2015", "10", "31");

		// Join a List by a delimiter.
		List<String> list = Arrays.asList("java", "python", "nodejs", "ruby");
		// java, python, nodejs, ruby
		String result4 = String.join(", ", list);

		// Collectors.joining
		// Two Stream and Collectors.joining examples.
		// 3.1 Join List<String> example.
		List<String> list2 = Arrays.asList("java", "python", "nodejs", "ruby");
		// java | python | nodejs | ruby
		String result5 = list2.stream().map(x -> x).collect(Collectors.joining(" | "));

		// Join List<Object> example.
		List<Game> list3 = Arrays.asList(new Game("Dragon Blaze", 5), new Game("Angry Bird", 5),
				new Game("Candy Crush", 5));
		// {Dragon Blaze, Angry Bird, Candy Crush}
		String result6 = list3.stream().map(x -> x.getName()).collect(Collectors.joining(", ", "{", "}"));

		System.out.printf("%s %n%s %n%s %n%s %n%s %n%s", result, result2, result3, result4, result5, result6);

	}
}

class Game {
	String name;
	int ranking;

	public Game(String name, int ranking) {
		this.name = name;
		this.ranking = ranking;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
}