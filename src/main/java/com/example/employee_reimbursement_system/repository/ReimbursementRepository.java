package com.example.employee_reimbursement_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.employee_reimbursement_system.model.Reimbursement;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Long> {

}
