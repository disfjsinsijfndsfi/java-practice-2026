package ru.itis.shop.user.api;

import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.domain.User;
import java.util.*;
import java.util.Optional;
import java.util.Scanner;

public class UserConsoleOperations {

    private final UserService userService;
    private final Scanner scanner;

    public UserConsoleOperations(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        printUserMenu();

        String command = scanner.nextLine();

        switch (command) {
            case "1": {
                signUp();
            }
            break;
            case "2": {
                signIn();
            }
            break;
            case "3":{
                findUserById();
            }
            break;
            case "4": {
                updateUserProfile();
            }
            break;
            case "5":{
                showAllUsers();
            }
            break;
            case "0": {
                System.exit(0);
            }
        }
    }

    private static void printUserMenu() {
        System.out.println("1. Регистрация пользователя");
        System.out.println("2. Вход в систему");
        System.out.println("3. Найти пользователя по id");
        System.out.println("4. Обновить данные пользователя");
        System.out.println("5. Показать всех пользователей");
        System.out.println("0. Выход");
    }

    private void signUp() {
        System.out.println("Сейчас будем регистрировать пользователя");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();
        System.out.println("Введите описание профиля:");
        String profileDescription = scanner.nextLine();

        userService.signUp(email, password, profileDescription);
    }

    private void signIn() {
        System.out.println("Вы можете войти в приложение");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();

        if (userService.signIn(email, password)) {
            System.out.println("Вы вошли в приложение");
        } else {
            System.out.println("Email или пароль не верны");
        }
    }

    private void updateUserProfile() {
        System.out.println("Обновление данных пользователя");
        System.out.println("Введите email пользователя:");
        String email = scanner.nextLine();
        System.out.println("Введите новое описание профиля:");
        String newDesc = scanner.nextLine();
        userService.updateUserProfile(email, newDesc);
    }

    private void findUserById() {
        System.out.println("Введите ID пользователя:");
        String id = scanner.nextLine().trim();
        Optional<String> emailOptional = userService.findUserEmailById(id);
        if (emailOptional.isPresent()) {
            System.out.println("Пользователь найден");
            System.out.println("Email: " + emailOptional.get());
        } else {
            System.out.println("Пользователь с " + id + " не найден");
        }
    }
    private void showAllUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            System.out.println("Пользователей нет");
            return;
        }
        System.out.println("Список всех пользователей:");
        for (User user:users) {
            System.out.println(user.getProfileDescription() + " " + user.getEmail());
        }
    }


}
