package com.tns.placementmanagment.repositories;

import com.tns.placementmanagment.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    // Returns student IDs ordered ascending. Service scans this to find the first missing sid.
    @Query("SELECT s.sid FROM Student s ORDER BY s.sid")
    List<Integer> findAllIds();
}
