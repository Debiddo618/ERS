package com.example.employee_reimbursement_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.employee_reimbursement_system.model.Reimbursement;
import com.example.employee_reimbursement_system.repository.ReimbursementRepository;

@Service
public class ReimbursementService {
    @Autowired
    private ReimbursementRepository reimbursementRepository;

    public Reimbursement saveReimbursement(Reimbursement reimbursement) {
        return reimbursementRepository.save(reimbursement);
    }

    public List<Reimbursement> getAllReimbursements() {
        return reimbursementRepository.findAll();
    }

    public Optional<Reimbursement> getReimbursementById(Long id) {
        return reimbursementRepository.findById(id);
    }

    public void deleteReimbursement(Long id) {
        reimbursementRepository.deleteById(id);
    }
}
