package org.sysoev.task5.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.sysoev.task5.entity.User;
import org.sysoev.task5.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService{

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
}
