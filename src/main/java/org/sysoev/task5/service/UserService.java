package org.sysoev.task5.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.sysoev.task5.entity.User;
import org.sysoev.task5.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements CommandLineRunner {

    private final UserRepository userRepository;


    public void createUser(String username) {
        userRepository.save(new User(username));
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(Long id, String username) {
        User user = getUser(id);
        if (user != null) {
            user.setUsername(username);
            userRepository.save(user);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void run(String... args){

        UserService userService = new UserService(userRepository);

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
