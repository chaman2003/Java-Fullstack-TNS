package com.tns.placementmanagment.repositories;

import com.tns.placementmanagment.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT s.sid FROM Student s ORDER BY s.sid")
    List<Integer> findAllIds();
}
