package com.example.employee_reimbursement_system.service;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employee_reimbursement_system.model.User;
import com.example.employee_reimbursement_system.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Create, Update, or Register the User
    public User saveUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        // System.out.println(hashed);
        return userRepository.save(user);
    }

    // Login User
    public Optional<User> loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (BCrypt.checkpw(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    // Read All
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Read by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Read by Username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Update
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    // Delete by ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
