package com.tns.placementmanagment.services.impl;

import com.tns.placementmanagment.entities.Admin;
import com.tns.placementmanagment.repositories.AdminRepository;
import com.tns.placementmanagment.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    public AdminRepository adminRepository;

    // Finds the first missing positive ID (1,2,3...) by scanning ordered IDs.
    // This enforces gap-free sequential IDs so deleted slots get reused.
    private Long findNextAvailableId() {
        List<Long> existingIds = adminRepository.findAllIds();
        long nextId = 1;
        for (Long id : existingIds) {
            if (id != nextId) {
                return nextId;
            }
            nextId++;
        }
        return nextId;
    }

    @Override
    public Admin createAdmin(Admin admin) {
        admin.setId(findNextAvailableId());
        return adminRepository.save(admin);
    }

    @Override
    public List<Admin> getAdmin() {
        return (List<Admin>) adminRepository.findAll();
    }

    @Override
    public Admin updateAdmin(Admin admin) {
        // Check if the admin exists before updating
        if (admin.getId() != null && adminRepository.existsById(admin.getId())) {
            return adminRepository.save(admin);
        }
        return null;
    }

    @Override
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}
