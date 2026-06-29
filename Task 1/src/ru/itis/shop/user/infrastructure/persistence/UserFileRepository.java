package ru.itis.shop.user.infrastructure.persistence;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;
import java.io.*;
import java.util.UUID;

public class UserFileRepository implements UserRepository {

    private final String fileName;

    public UserFileRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            writer.write(user.getId() + "|" +
                    user.getEmail() + "|" +
                    user.getPassword() + "|" +
                    user.getProfileDescription());
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
    @Override
    public User findById(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4 && parts[0].equals(id)) {
                    return new User(parts[0], parts[1], parts[2], parts[3]);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка чтения файла " + fileName, e);
        }
        return null;
    }
}
