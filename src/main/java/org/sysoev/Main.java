package org.sysoev;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.sysoev.task4.entity.User;
import org.sysoev.task4.service.UserService;


@ComponentScan
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);


        UserService userService = context.getBean(UserService.class);

        userService.createUser("Илья");
        userService.createUser("Алексей");
        userService.createUser("Гена");
        System.out.println("Пользователи были созданы");

        System.out.println("Все пользователи:");
        userService.getAllUsers().forEach(user -> System.out.println(user.toString()));

        Long id = 6L;
        User user = userService.getUser(id);
        System.out.println("Получен пользователь: " + user.toString()+ " с id: "+ id);
        userService.updateUser(user.getId(), "тестТЕСТ123");
        System.out.println("После обновления: " + userService.getUser(id).toString());

        userService.deleteUser(user.getId());
        System.out.println("После удаления:");
        userService.getAllUsers().forEach(System.out::println);

    }
}