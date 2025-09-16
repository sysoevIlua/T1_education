package org.sysoev.task1.service;

import org.sysoev.task1.annotations.*;

import java.util.HashMap;
import java.util.Map;

public class TestService {

    private AuthenticationService authService;
    private Map<String, String> testUsers;

    @BeforeSuite
    public static void startAuthTests() {
        System.out.println("Старт тестов");
    }


    @BeforeTest
    public void initializeService() {
        authService = new AuthenticationService();
        testUsers = new HashMap<>();
        testUsers.put("admin", "admin123");
        testUsers.put("user", "password");
        testUsers.put("test", "test123");
        System.out.println("Данные аутентификации инициализирован");
    }

    @AfterTest
    public void clearService() {
        authService = null;
        testUsers = null;
        System.out.println("Данные аутентификации очищен");
    }


    @Test(priority = 10)
    public void testSuccessfulLogin() {
        boolean result = authService.authenticate("admin", "admin123", testUsers);
        assert result : "Ожидалась успешная аутентификация для admin/admin123";
        System.out.println("Успешная аутентификация администратора");
    }

    @Test(priority = 9)
    public void testFailedLoginWrongPassword() {
        boolean result = authService.authenticate("admin", "wrong", testUsers);
        assert !result : "Ожидался отказ в аутентификации при неверном пароле";
        System.out.println("Корректный отказ при неверном пароле");
    }

    @Test(priority = 1)
    public void testFailedLoginUnknownUser() {
        boolean result = authService.authenticate("unknown", "password", testUsers);
        assert !result : "Ожидался отказ в аутентификации для неизвестного пользователя";
        System.out.println("Корректный отказ для неизвестного пользователя");
    }

    @Test(priority = 7)
    public void testNullUsernameHandling() {
        try {
            authService.authenticate(null, "password", testUsers);
            assert false : "Ожидалось исключение для null username";
        } catch (IllegalArgumentException e) {
            System.out.println("Корректная обработка null имени пользователя");
        }
    }

    @Test(priority = 6)
    public void testEmptyPasswordHandling() {
        boolean result = authService.authenticate("user", "", testUsers);
        assert !result : "Ожидался отказ в аутентификации при пустом пароле";
        System.out.println("Корректная обработка пустого пароля");
    }

    @Test
    public void testUserRegistration() {
        authService.registerUser("newuser", "newpass", testUsers);
        assert testUsers.containsKey("newuser") : "Новый пользователь не был зарегистрирован";
        System.out.println("Регистрация нового пользователя прошла успешно");
    }

    @Test(priority = 4)
    public void testDuplicateUserRegistration() {
        try {
            authService.registerUser("admin", "newpass", testUsers);
            assert false : "Ожидалось исключение при регистрации существующего пользователя";
        } catch (IllegalStateException e) {
            System.out.println("Корректная обработка дубликата пользователя");
        }
    }

    @AfterSuite
    public static void completeAuthTests() {
        System.out.println("Завершение тестов");
    }

}
