package ru.butakov.remember.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.butakov.remember.dao.UserRepository;
import ru.butakov.remember.entity.User;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userFromDb = userRepository.findByUsername(username);
        return userFromDb.orElseThrow(() -> new UsernameNotFoundException(
                MessageFormat.format("User {0} not found", username)));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(int id){
        return userRepository.findById(id);
    }
}
