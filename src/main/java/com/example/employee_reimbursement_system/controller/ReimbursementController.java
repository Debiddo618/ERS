package com.example.employee_reimbursement_system.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.employee_reimbursement_system.exception.ReimbursementNotFoundException;
import com.example.employee_reimbursement_system.model.Reimbursement;
import com.example.employee_reimbursement_system.model.User;
import com.example.employee_reimbursement_system.service.ReimbursementService;
import com.example.employee_reimbursement_system.service.UserService;

@Controller
@RequestMapping("/reimbursements")
@CrossOrigin(origins = "http://localhost:5173")
public class ReimbursementController {
    @Autowired
    private ReimbursementService reimbursementService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createReimbursement(@RequestBody Reimbursement reimbursement) {
        try {
            // Fetch the authenticated user's details
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            // Retrieve the user from the database using the username
            Optional<User> optionalUser = userService.findByUsername(username);
            if (!optionalUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User with username " + username + " not found");
            }

            User user = optionalUser.get();

            // Associate the reimbursement with the user
            reimbursement.setUser(user);

            // Save the reimbursement
            Reimbursement savedReimbursement = reimbursementService.saveReimbursement(reimbursement);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedReimbursement);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the reimbursement: " + e.getMessage());
        }
    }

    // Get All
    @GetMapping
    public ResponseEntity<?> getAllReimbursements() {
        try {
            List<Reimbursement> reimbursements = reimbursementService.getAllReimbursements();
            return ResponseEntity.ok(reimbursements);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving reimbursements.");
        }
    }

    // Get by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getReimbursementById(@PathVariable Long id) {
        try {
            Reimbursement reimbursement = reimbursementService.getReimbursementById(id)
                    .orElseThrow(() -> new ReimbursementNotFoundException("Reimbursement not found with id: " + id));
            return ResponseEntity.ok(reimbursement);
        } catch (ReimbursementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving the reimbursement.");
        }
    }

    // Delete by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReimbursement(@PathVariable Long id) {
        try {
            reimbursementService.deleteReimbursement(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the reimbursement.");
        }
    }

    // Update reimbursement by id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReimbursement(@PathVariable Long id,
            @RequestBody Reimbursement reimbursementDetails) {
        try {
            Reimbursement updatedReimbursement = reimbursementService.updateReimbursement(id, reimbursementDetails);
            return ResponseEntity.ok(updatedReimbursement);
        } catch (ReimbursementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the reimbursement.");
        }
    }

    // Approve reimbursement manager only
    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveReimbursement(@PathVariable Long id) {
        try {

            // Fetch the authenticated user's details
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            // Retrieve the user from the database using the username
            Optional<User> optionalUser = userService.findByUsername(username);
            if (!optionalUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User with username " + username + " not found");
            }

            User user = optionalUser.get();

            // Check if the user is not a Manager
            if (!user.getRole().getName().equals("MANAGER")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Unauthorized Access: Only Managers are allowed to approve reimbursements.");
            }
            Reimbursement approvedReimbursement = reimbursementService.approveReimbursement(id);
            return ResponseEntity.ok(approvedReimbursement);
        } catch (ReimbursementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getReimbursementsByUserId(@PathVariable Long userId) {
        try {
            List<Reimbursement> reimbursements = reimbursementService.getReimbursementsByUserId(userId);

            if (reimbursements.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No reimbursements found for user with ID: " + userId);
            }

            return ResponseEntity.ok(reimbursements);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving reimbursements for user ID: " + userId);
        }
    }
}
