package ru.itis.shop.user.infrastructure.persistence.jdbc;

import ru.itis.shop.infrastructure.persistence.jdbc.RowMapper;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import javax.sql.ConnectionEvent;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {

    private final DataSource dataSource;

    private final RowMapper<User> userRowMapper = row -> new User(
            row.getInt("id"),
            row.getString("email"),
            row.getString("password"),
            row.getString("profile_description")
    );

    public UserRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (email, password, profile_description) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getProfileDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка сохранения пользователя", e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * from users WHERE email = ?";
        try(Connection conn = dataSource.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                return Optional.of(userRowMapper.mapRow(result));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при нахождении пользователя по email", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Integer id) {
        String sql = "SELECT * from users WHERE id = ?";
        try(Connection conn = dataSource.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                return Optional.of(userRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при поиске по id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("select * from users")) {
                    while (resultSet.next()) {
                        users.add(userRowMapper.mapRow(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Не удалось найти всех пользователей", e);
        }
        return users;
    }
    @Override
    public void updateProfileDescriptionByEmail(String email, String newDescription) {
        String sql = "UPDATE users SET profile_description = ? WHERE email = ?";
        try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newDescription);
            ps.setString(2, email);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new IllegalStateException("Пользователь с email " + email + " не найден");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при обновлении описания", e);
        }
    }
    @Override
    public List<User> findAllByProfileDescription(String profileDescription) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE profile_description = ?";
        try(Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, profileDescription);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(userRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при нахождении пользователей по описанию", e);
        }
        return users;
    }
}
