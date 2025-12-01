package com.tns.placementmanagment.repositories;

import com.tns.placementmanagment.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query("SELECT u.id FROM Users u ORDER BY u.id")
    List<Long> findAllIds();
}
