package com.tns.placementmanagment.services.impl;

import com.tns.placementmanagment.entities.Users;
import com.tns.placementmanagment.repositories.UsersRepository;
import com.tns.placementmanagment.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    public UsersRepository usersRepository;

    // Finds the first missing positive ID (1,2,3...) by scanning ordered IDs.
    // This enforces gap-free sequential IDs so deleted slots get reused.
    private Long findNextAvailableId() {
        List<Long> existingIds = usersRepository.findAllIds();
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
    public Users createUsers(Users users) {
        users.setId(findNextAvailableId());
        return usersRepository.save(users);
    }

    @Override
    public List<Users> getUsers() {
        return (List<Users>) usersRepository.findAll();
    }

    @Override
    public Users updateUsers(Users users) {
        // Check if the user exists before updating
        if (users.getId() != null && usersRepository.existsById(users.getId())) {
            return usersRepository.save(users);
        }
        return null;
    }

    @Override
    public void deleteUsers(Long id) {
        usersRepository.deleteById(id);
    }
}
