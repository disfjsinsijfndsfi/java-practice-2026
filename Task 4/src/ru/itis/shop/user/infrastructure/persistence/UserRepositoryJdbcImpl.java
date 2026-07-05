package ru.itis.shop.user.infrastructure.persistence;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.sql.*;
import java.util.*;

public class UserRepositoryJdbcImpl implements UserRepository {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/gu_mur";
    private static final String DB_USER = "gu_mur";
    private static final String DB_PASSWORD = "qwerty007";

    @Override
    public void save(User user) {
        throw new UnsupportedOperationException("Метод не реализован для JDBC");
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {
        throw new UnsupportedOperationException("Метод не реализован для JDBC");
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println(connection);
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("SELECT * FROM public.users")) {
                    while (resultSet.next()) {
                        String id = String.valueOf(resultSet.getInt("id"));
                        User user = new User(
                                id,
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("profile_description")
                        );
                        users.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при получении всех пользователей", e);
        }
        return users;
    }
}
