package ru.butakov.remember.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.butakov.remember.dao.UserRepository;
import ru.butakov.remember.entity.User;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userFromDb = userRepository.findByUsername(username);
        return userFromDb.orElseThrow(() -> new UsernameNotFoundException(
                MessageFormat.format("User {0} not found", username)));
    }
}
