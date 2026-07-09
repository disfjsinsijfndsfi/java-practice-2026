package ru.itis.shop.user.api;

import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.domain.User;
import java.util.*;
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
            case "4":{
                updateProfileDescription();
            }
            break;
            case "5": {
                showAllUsers();
            }
            break;
            case "6": {
                showUsersByProfileDescription();
            }
            break;
            case "7": {
                findUserByEmail();
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
        System.out.println("4. Обновить описание пользователя по почте");
        System.out.println("5. Получить информацию обо всех пользователях");
        System.out.println("6. Показать информацию о пользователях с заданным описанием профиля");
        System.out.println("7. Показать информацию о пользователя по email");
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

    private void findUserById() {
        System.out.println("Введите id пользователя:");
        try {
            Integer id = Integer.parseInt(scanner.nextLine());
            UserDto user = userService.getUserById(id);
            System.out.println("ID: " + user.getId() + ", Email: " + user.getEmail() + ", Описание: " + user.getProfileDescription());
        } catch (NumberFormatException e) {
            System.out.println("ID должен быть числом");
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void updateProfileDescription() {
        System.out.println("Введите email пользователя:");
        String email = scanner.nextLine();
        System.out.println("Введите новое описание профиля:");
        String newDescription = scanner.nextLine();
        try {
            userService.updateProfileDescription(email, newDescription);
            System.out.println("Описание обновлено!");
        } catch (Exception e) {
            System.out.println("Ошибка обновления профиля  " + e.getMessage());
        }
    }

    private void showAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("Пользователей пока нет");
        } else {
            System.out.println("Список всех пользователей:");
            for (UserDto user : users) {
                System.out.println("ID:  " + user.getId() + " Email: " + user.getEmail() + " Описание: " + user.getProfileDescription());
            }
        }
    }

    private void showUsersByProfileDescription() {
        System.out.println("Введите описание профиля:");
        String description = scanner.nextLine();
        List<UserDto> users = userService.getUsersByProfileDescription(description);
        if (users.isEmpty()) {
            System.out.println("Пользователи с описанием '" + description + "' не найдены");
        } else {
            System.out.println("Найдено пользователей: " + users.size());
            for (UserDto user : users) {
                System.out.println("ID: " + user.getId() + " Email: " + user.getEmail());
            }
        }
    }

    private void findUserByEmail() {
        System.out.println("Введите email пользователя:");
        String email = scanner.nextLine();
        try {
            UserDto user = userService.getUserByEmail(email);
            System.out.println("ID: " + user.getId() + " Email: " + user.getEmail() + " Описание: " + user.getProfileDescription());
        } catch (RuntimeException e) {
            System.out.println("Ошибка в получении пользователя" + e.getMessage());
        }
    }
}



