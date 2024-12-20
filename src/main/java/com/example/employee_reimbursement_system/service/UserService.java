package com.example.employee_reimbursement_system.service;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.employee_reimbursement_system.model.User;
import com.example.employee_reimbursement_system.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // Create, Update, or Register the User
    public User saveUser(User user) {
        // String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        // user.setPassword(hashed);
        // System.out.println(hashed);
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        return userRepository.save(user);
    }

    // Login user
    public Optional<User> loginUser(String username, String password) {
        System.out.println("Inside loginUser Service");
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            // This is the user in the database
            User user = userOptional.get();
            if (encoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    // Read all
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Read by id
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Read by username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Update
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    // Delete by id
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
