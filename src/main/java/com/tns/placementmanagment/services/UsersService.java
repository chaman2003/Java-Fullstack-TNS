package com.tns.placementmanagment.services;

import com.tns.placementmanagment.entities.Users;

import java.util.List;

public interface UsersService {
    Users createUsers(Users users);
    List<Users> getUsers();
    Users updateUsers(Users users);
    void deleteUsers(Long id);
}
