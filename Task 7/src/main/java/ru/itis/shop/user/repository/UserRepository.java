package ru.itis.shop.user.repository;

import ru.itis.shop.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer id);

    List<User> findAll();

    void updateProfileDescriptionByEmail(String email, String newDescription);

    List<User> findAllByProfileDescription(String profileDescription);
}
