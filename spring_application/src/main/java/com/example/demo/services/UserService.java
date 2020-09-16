package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.exceptions.UserAlreadyExistException;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Username must be unique and that the two hashes match
     * we must store encoded passwords as spring security expects
     */
    public void register(String username, String password, String passwordConfirmation) throws UserAlreadyExistException {
        //if passwords match
        if (password.equals(passwordConfirmation)) {
            // if the username is new
            if (!usernameExist(username)) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
            } else {
                throw new UserAlreadyExistException();
            }
        } else {
            throw new IllegalArgumentException("Password doesn't match confirmation");
        }
    }

    private boolean usernameExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
