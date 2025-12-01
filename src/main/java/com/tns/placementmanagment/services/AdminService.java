package com.tns.placementmanagment.services;

import com.tns.placementmanagment.entities.Admin;

import java.util.List;

public interface AdminService {
    Admin createAdmin(Admin admin);
    List<Admin> getAdmin();
    Admin updateAdmin(Admin admin);
    void deleteAdmin(Long sid);
}
