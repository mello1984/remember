package ru.butakov.remember.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.butakov.remember.dao.UserRepository;
import ru.butakov.remember.entity.Role;
import ru.butakov.remember.entity.User;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MailSender mailSender;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean addUser(User user) {
//        System.out.println("***************************");
//        System.out.println(userRepository.findAll());
//        List<User> users = userRepository.findAll();
//        System.out.println("***************************");
        Optional<User> userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb.isPresent()) {
            return false;
        }

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(user);

        if (StringUtils.hasText(user.getEmail())) {
            String message = "Hello, {0}!\n" +
                    "Confirm email on link: http://localhost:8080/activate/{1}";

            mailSender.send(user.getEmail(), "Activation code",
                    MessageFormat.format(message, user.getUsername(), user.getActivationCode()));
        }

        return true;
    }

    @Override
    public boolean activateEmail(String code) {
        Optional<User> userFromDb = userRepository.findByActivationCode(code);
        if (userFromDb.isEmpty()) return false;

        User user = userFromDb.get();
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }
}
