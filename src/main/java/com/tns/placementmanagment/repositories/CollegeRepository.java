package com.tns.placementmanagment.repositories;

import com.tns.placementmanagment.entities.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {
    @Query("SELECT c.id FROM College c ORDER BY c.id")
    List<Long> findAllIds();
}
