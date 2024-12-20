package com.example.employee_reimbursement_system.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.employee_reimbursement_system.model.User;
import com.example.employee_reimbursement_system.model.UserPrincipal;
import com.example.employee_reimbursement_system.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = repo.findByUsername(username);
        if (!optionalUser.isPresent()) {
            // System.out.println("User not found in the database.");
            throw new UsernameNotFoundException("User 404");
        }

        User user = optionalUser.get();
        return new UserPrincipal(user);
    }

}
