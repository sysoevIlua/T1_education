package org.sysoev.service;

import java.util.Map;

public class AuthenticationService {

    public boolean authenticate(String username, String password, Map<String, String> users) {
        if (username == null) {
            throw new IllegalArgumentException("Имя пользователя не может быть null");
        }
        return users.containsKey(username) && users.get(username).equals(password);
    }

    public void registerUser(String username, String password, Map<String, String> users) {
        if (users.containsKey(username)) {
            throw new IllegalStateException("Пользователь уже существует: " + username);
        }
        users.put(username, password);
    }
}
