package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;


@SpringBootApplication
public class DemoApplication	{

	public interface Predicate<T> {
		boolean test(T t);
	}

	static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
		List<Apple> result = new ArrayList<Apple>();
		for(Apple apple: inventory) {
			if (p.test(apple)) {
				result.add(apple);
			}
		}

		return result;
	}

	static List<Apple> fillterApples(List<Apple> inventory, Predicate<Apple> p) {
		List<Apple> result = new ArrayList<Apple>();
		for(Apple apple: inventory) {
			if (p.ac(apple)) {
				result.add(apple);
			}
		}

		return result;
	}

	public static void main(String[] args) {
		List<Apple> inventory = new ArrayList<>();
		inventory.add(new Apple(10, "green"));
		inventory.add(new Apple(100, "red"));
		inventory.add(new Apple(200, "blue"));

		List<Apple> rest = inventory.parallelStream()
				.filter((Apple a) -> a.getColor() == "green")
						.collect(toList());
		System.out.println(rest);
		// SpringApplication.run(DemoApplication.class, args);
	}

}
