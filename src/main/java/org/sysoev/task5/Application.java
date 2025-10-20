package org.sysoev.task5;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.sysoev.task5.service.UserService;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private final UserService userService;

    public Application(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Пользователи после миграции:");
        userService.getAllUsers().forEach(System.out::println);

        userService.createUser("Новый пользователь");
        System.out.println("После добавления:");
        userService.getAllUsers().forEach(System.out::println);

        var user = userService.getAllUsers().getFirst();
        userService.updateUser(user.getId(), "Обновлённый " + user.getUsername());
        System.out.println("После обновления:");
        userService.getAllUsers().forEach(System.out::println);

        userService.deleteUser(user.getId());
        System.out.println("После удаления:");
        userService.getAllUsers().forEach(System.out::println);
    }
}
