package com.tns.placementmanagment.repositories;

import com.tns.placementmanagment.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query("SELECT a.id FROM Admin a ORDER BY a.id")
    List<Long> findAllIds();
}
