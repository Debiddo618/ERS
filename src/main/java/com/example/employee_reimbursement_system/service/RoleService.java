
package com.example.employee_reimbursement_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employee_reimbursement_system.model.Role;
import com.example.employee_reimbursement_system.model.Role.RoleName;
import com.example.employee_reimbursement_system.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

}
