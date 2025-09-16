package org.sysoev.task2;

import org.sysoev.task2.entity.Employee;

import java.util.*;
import java.util.stream.Collectors;

public class AllStreamMethods {

    public void startAllStreamMethods() {
        task1_thirdMaxNumber();
        task2_thirdMaxUniqNumber();
        task3_thirdOldestEmployee();
        task4_avgAge();
        task5_mostLengthWord();
        task6_mapFromString();
        task7_sortingString();
        task8_mostLengthWordFromArrays();
    }

    public void task1_thirdMaxNumber() {

        List<Integer> list = new ArrayList<>(List.of(5, 2, 10, 9, 4, 3, 10, 1, 13));

        list.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .ifPresent(maxThird -> System.out.println("Максимальное третье число: " + maxThird));
        System.out.println("----------------");
    }

    public void task2_thirdMaxUniqNumber() {
        List<Integer> list = new ArrayList<>(List.of(5, 2, 10, 9, 4, 3, 10, 1, 13));

        list.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .ifPresent(maxThird -> System.out.println("Максимальное третье уникальное число: " + maxThird));
        System.out.println("----------------");
    }

    public void task3_thirdOldestEmployee() {
        List<Employee> list = new ArrayList<>(
                List.of(new Employee("Аркаша", 12, "Инженер"),
                        new Employee("Гена", 43, "Сеньер помидор"),
                        new Employee("Женек", 23, "Работяга мидл"),
                        new Employee("Владик", 33, "Бедолага тестировщик"),
                        new Employee("Илюша", 25, "Молодой джун"),
                        new Employee("Света", 46, "Инженер"),
                        new Employee("Димон", 65, "Инженер"),
                        new Employee("Авдосия", 29, "Инженер"),
                        new Employee("Саша", 5, "Чей-то ребенок")));
        list.stream()
                .filter(employee -> employee.getPost().equals("Инженер"))
                .sorted(Comparator.comparing(Employee::getAge).reversed())
                .limit(3)
                .forEach(employee -> System.out.println(employee.getName()));
        System.out.println("----------------");
    }

    public void task4_avgAge() {
        List<Employee> list = new ArrayList<>(
                List.of(new Employee("Аркаша", 12, "Инженер"),
                        new Employee("Гена", 43, "Сеньер помидор"),
                        new Employee("Женек", 23, "Работяга мидл"),
                        new Employee("Владик", 33, "Бедолага тестировщик"),
                        new Employee("Илюша", 25, "Молодой джун"),
                        new Employee("Света", 46, "Инженер"),
                        new Employee("Димон", 65, "Инженер"),
                        new Employee("Авдосия", 29, "Инженер"),
                        new Employee("Саша", 5, "Чей-то ребенок")));
        list.stream()
                .filter(employee -> employee.getPost().equals("Инженер"))
                .mapToInt(Employee::getAge)
                .average()
                .ifPresent(avg -> System.out.println("Средний возраст: " + avg));
        System.out.println("----------------");
    }

    public void task5_mostLengthWord() {
        List<String> list = new ArrayList<>(List.of("qwerty", "asd", "zx", "zxcvbnm", "yuiop", "qwertyuiop"));

        list.stream()
                .max(Comparator.comparing(String::length))
                .ifPresent(maxWord -> System.out.println("Самое длинное слово: " + maxWord));
        System.out.println("----------------");
    }

    public void task6_mapFromString(){
        String randomString = "java python java sql python java spring sql python java";

        Arrays.stream(randomString.split(" "))
                .map(word -> new AbstractMap.SimpleEntry<>(word, 1))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum
                ))
                .forEach((word,count) -> System.out.println(word + " : " + count));

        System.out.println("----------------");
    }

    public void task7_sortingString(){
        List<String> list = new ArrayList<>(List.of("qwerty", "asd", "zx", "zxcvbnm", "yuiop"
                , "qwertyuiop", "qserty", "qsmrty"));

        list.stream()
                .sorted(Comparator.comparing(String::length)
                        .thenComparing(Comparator.naturalOrder()))
                .forEach(System.out::println);

        System.out.println("----------------");
    }

    public void task8_mostLengthWordFromArrays(){
        String[] arrays = {
                "java python kotlin swift javascript",
                "spring hibernate jpa thymeleaf bootstrap",
                "microservices docker kubernetes aws azure",
                "programming development testing deployment maintenance"
        };

        Arrays.stream(arrays)
                .flatMap(word -> Arrays.stream(word.split(" ")))
                .max(Comparator.comparing(String::length))
                .ifPresent(word -> System.out.println("Самое длинное слово в массивах: " + word));
    }
}
