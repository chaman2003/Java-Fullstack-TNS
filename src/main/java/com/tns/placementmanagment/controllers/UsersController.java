package com.tns.placementmanagment.controllers;

import com.tns.placementmanagment.entities.Users;
import com.tns.placementmanagment.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
@CrossOrigin(origins = "*")
public class UsersController {

    @Autowired
    public UsersService usersService;

    @GetMapping
    public List<Users> getUsers() {
        return usersService.getUsers();
    }

    @PostMapping
    public Users createUsers(@RequestBody Users users) {
        return usersService.createUsers(users);
    }

    @PutMapping
    public Users updateUsers(@RequestBody Users users){
        Users updated = usersService.updateUsers(users);
        if (updated == null) {
            throw new IllegalArgumentException("User not found with ID: " + users.getId());
        }
        return updated;
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUsers(@PathVariable(name = "id") Long id) {
        usersService.deleteUsers(id);
    }

}
