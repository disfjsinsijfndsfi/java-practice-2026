package ru.itis.shop.user.application;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String email, String password, String profileDescription) {
        User user = new User(email, password, profileDescription);
        userRepository.save(user);
    }

    public boolean signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get().getPassword().equals(password);
        } else return false;
    }
    public void updateUserProfile(String email, String newProfileDescription) {
        Optional<User> findUserByEmail = userRepository.findByEmail(email);
        if (findUserByEmail.isPresent()) {
            User user = findUserByEmail.get();
            user.setProfileDescription(newProfileDescription);
            userRepository.update(user);
            System.out.println("Данные пользователя успешно обновлены");
        } else {
            System.out.println("Пользователь с " + email + " не найден");
        }
    }
    public Optional<String> findUserEmailById(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(User::getEmail);
    }
}
