package com.example.employee_reimbursement_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.employee_reimbursement_system.exception.ReimbursementNotFoundException;
import com.example.employee_reimbursement_system.model.Reimbursement;
import com.example.employee_reimbursement_system.service.ReimbursementService;

@Controller
@RequestMapping("/reimbursements")
public class ReimbursementController {
    @Autowired
    private ReimbursementService reimbursementService;

    // Create
    @PostMapping
    public ResponseEntity<?> createReimbursement(@RequestBody Reimbursement reimbursement) {
        try {
            Reimbursement savedReimbursement = reimbursementService.saveReimbursement(reimbursement);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReimbursement);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the reimbursement.");
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
}
