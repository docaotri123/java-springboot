package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.partitioningBy;

class Letter {
    public static String addHeader(String text) {
        return "From Raoul, Mario and Alan: " + text;
    }

    public static String addFooter(String text) {
        return text + " Kind regards";
    }

    public static String checkSpelling(String text) {
        return text.replaceAll("labda", "lambda");
    }
}

class Trader {
    private final String name;
    private final String city;

    public Trader(String n, String c) {
        this.name = n;
        this.city = c;
    }

    public String getName() {
        return this.name;
    }

    public String getCity() {
        return this.city;
    }

    public String toString() {
        return "Trader:" + this.name + " in " + this.city;
    }
}

class Transaction {
    private final Trader trader;
    private final int year;
    private final int value;

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return this.trader;
    }

    public int getYear() {
        return this.year;
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        return "{" + this.trader + ", " +
                "year: " + this.year + ", " +
                "value:" + this.value + "}";
    }
}

@SpringBootApplication
public class DemoApplication {

    public interface Predicate<T> {
        boolean test(T t);
    }

    static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<Apple>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }

        return result;
    }

//	static List<Apple> fillterApples(List<Apple> inventory, Predicate<Apple> p) {
//		List<Apple> result = new ArrayList<Apple>();
//		for(Apple apple: inventory) {
//			if (p.(apple)) {
//				result.add(apple);
//			}
//		}
//
//		return result;
//	}

    static void test() {
        Function<String, String> addHeader = Letter::addHeader;
        Function<String, String> transformationPipeline
                = addHeader.andThen(Letter::checkSpelling)
                .andThen(Letter::addFooter);
        System.out.println("transformationPipeline: " + transformationPipeline);
    }

    static void chapter5dot6() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

//		List<Transaction> transactionsIn2021 = transactions.stream()
//				.filter(t -> t.getYear() == 2011)
//				.collect(toList());

//		List<String> citiesWhereTradersWork = transactions.stream()
//				.map(t -> t.getTrader().getCity())
//				.distinct()
//				.collect(toList());

//		List<Trader> traderFromCambridge = transactions.stream()
//				.filter(t -> t.getTrader().getCity() == "Cambridge")
//				.map(t -> t.getTrader())
//				.sorted(Comparator.comparing(Trader::getName))
//				.collect(Collectors.toList());

//		List<String> traderNameSortedDesc = transactions.stream()
//				.map(t -> t.getTrader().getName())
//				.sorted()
//				.collect(Collectors.toList());

//		boolean isAnyTraderInMilan = transactions.stream()
//				.anyMatch(t -> t.getTrader().getCity() == "Milan");

//        List<Integer> valueTransactions = transactions.stream()
//                .map(t -> t.getValue())
//                .collect(Collectors.toList());

//        int maxValueTransactions = transactions.stream()
//                .max(Comparator.comparing(Transaction::getValue))
//                .map(t -> t.getValue())
//                .orElseThrow(NoSuchFieldError::new);

        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(20)
                .map(t -> t[0])
                .forEach(System.out::println);
        System.out.println("");
    }

    static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }

    static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(partitioningBy(candidate -> isPrime(candidate)));
    }
    static void chapter6() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

//        long howManyDishes = transactions.stream()
//                .collect(Collectors.counting());
//        IntSummaryStatistics menuStatistics =
//                transactions.stream().collect(summarizingInt(Transaction::getYear));
//        String shortMenu = transactions.stream()
//                .map(t -> t.getTrader().getName()).collect(joining(", "));
//        Map<String, List<Transaction>> transactionsByYear = transactions.stream()
//                        .collect(groupingBy(transaction -> {
//                            if (transaction.getYear() <= 2011) {
//                                return "oldYear";
//                            }
//                            return "newYear";
//                        }));
        Map<Boolean, List<Integer>> test = partitionPrimes(100);

        System.out.println(test);
    }

    public static void main(String[] args) {
        System.out.println("main");

//		List<Apple> inventory = new ArrayList<>();
//		BiFunction<Integer, String, Apple> a = Apple::new;
//		a.apply(100, "haha");
//		// System.out.println("apple: " + a);
//		inventory.add(new Apple(400, "B"));
//		inventory.add(new Apple(400, "A"));
//		inventory.add(new Apple(300, "blue"));
        // System.out.println("array: " + inventory);

//		List<Apple> rest = inventory.parallelStream()
//				.filter((Apple a) -> a.getColor() == "green")
//						.collect(toList());
//		System.out.println(rest);
        // SpringApplication.run(DemoApplication.class, args);
        // inventory.sort(Comparator.comparing(Apple::getWeight));
//		inventory.sort(Comparator.comparing(Apple::getWeight).thenComparing(Apple::getColor));
//		System.out.println("array: " + inventory);
//		List<String> test = inventory.parallelStream()
//				.filter(apple -> apple.getWeight() > 10)
//				.sorted(comparing(Apple::getWeight))
//				.map(Apple::getColor)
//				.collect(Collectors.toList());
//		System.out.println(test);
//		List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
//		List<Integer> wordLengths = words.stream()
//				.map(String::length)
//				.collect(toList());
//		System.out.println(wordLengths);
//		List<String> words = Arrays.asList("Hello", "World");
//		List<String> uniqueCharacters = words.stream()
//				.map(word -> word.split(""))
//				.flatMap(Arrays::stream)
//				.distinct()
//				.collect(toList());
//		System.out.println(uniqueCharacters);
//		List<Integer> numbers1 = Arrays.asList(1, 2, 3);
//		List<Integer> numbers2 = Arrays.asList(3, 4);
//		List<int[]> pairs =
//				numbers1.stream()
//						.flatMap(i -> numbers2.stream()
//								.map(j -> new int[]{i, j})
//						)
//						.collect(toList());
//
//		pairs.forEach(pair -> {
//			System.out.println(pair[0] + "-" +pair[1]);
//		});
        chapter6();
    }

}
