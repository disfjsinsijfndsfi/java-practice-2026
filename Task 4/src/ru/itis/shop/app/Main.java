package ru.itis.shop.app;

import ru.itis.shop.user.api.UserConsoleOperations;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.infrastructure.persistence.UserFileRepository;
import ru.itis.shop.user.infrastructure.persistence.UserMapper;
import ru.itis.shop.user.infrastructure.persistence.UserRepositoryJdbcImpl;

public class Main {
    public static void main(String[] args) {
        UserRepositoryJdbcImpl userRepository = new UserRepositoryJdbcImpl();
        UserService userService = new UserService(userRepository);

        UserConsoleOperations operations = new UserConsoleOperations(userService);

        while (true) {
            operations.showMenu();
        }
    }
}
