package com.example.employee_reimbursement_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employee_reimbursement_system.exception.ReimbursementNotFoundException;
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

    public Reimbursement updateReimbursement(Long id, Reimbursement reimbursementDetails) {
        Reimbursement reimbursement = reimbursementRepository.findById(id)
                .orElseThrow(() -> new ReimbursementNotFoundException("Reimbursement not found with id: " + id));
        reimbursement.setDescription(reimbursementDetails.getDescription());
        reimbursement.setAmount(reimbursementDetails.getAmount());
        reimbursement.setStatus(reimbursementDetails.getStatus());
        return reimbursementRepository.save(reimbursement);
    }

    public Reimbursement approveReimbursement(Long id) {
        Optional<Reimbursement> reimbursementOpt = reimbursementRepository.findById(id);
        if (reimbursementOpt.isPresent()) {
            Reimbursement reimbursement = reimbursementOpt.get();
            reimbursement.setStatus("APPROVED");
            return reimbursementRepository.save(reimbursement);
        } else {
            throw new ReimbursementNotFoundException("Reimbursement not found with id: " + id);
        }
    }

    public List<Reimbursement> getReimbursementsByUserId(Long userId) {
        return reimbursementRepository.findByUserId(userId);
    }
}
