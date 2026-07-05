package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserFileRepository implements UserRepository {

    private final String fileName;

    private final UserMapper userMapper;

    public UserFileRepository(String fileName, UserMapper userMapper) {
        this.fileName = fileName;
        this.userMapper = userMapper;
    }
    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException("Метод не реализован для файлового репозитория");
    }
    @Override
    public void save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            writer.write(userMapper.toLine(user));
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){

            String line = reader.readLine();

            while (line != null) {

                User user = userMapper.fromLine(line);

                if (user.getEmail().equals(email)) {
                    return Optional.of(user);
                }

                line = reader.readLine();
            }

            return Optional.empty();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                User user = userMapper.fromLine(line);
                if (user.getId().equals(id)) {
                    return Optional.of(user);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка чтения файла", e);
        }
        return Optional.empty();
    }

    @Override
    public void update(User updatedUser) {
        List<String> lines = new ArrayList<>();
        File file = new File(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                User user = userMapper.fromLine(line);
                if (user.getId().equals(updatedUser.getId())) {
                    lines.add(userMapper.toLine(updatedUser));
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка в чтении файла при обновлении", e);
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка записи в файл при обновлении", e);
        }
    }
}
