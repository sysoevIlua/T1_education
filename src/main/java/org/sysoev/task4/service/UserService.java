package org.sysoev.task4.service;

import org.springframework.stereotype.Service;
import org.sysoev.task4.entity.User;
import org.sysoev.task4.repository.UserDao;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(String username) {
        userDao.save(new User(username));
    }

    public User getUser(Long id) {
        return userDao.getById(id);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void updateUser(Long id, String username) {
        User user = getUser(id);
        if (user != null) {
            user.setUsername(username);
            userDao.update(user);
        }
    }

    public void deleteUser(Long id) {
        userDao.delete(id);
    }
}
