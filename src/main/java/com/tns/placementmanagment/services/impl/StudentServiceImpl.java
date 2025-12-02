package com.tns.placementmanagment.services.impl;

import com.tns.placementmanagment.entities.Student;
import com.tns.placementmanagment.repositories.StudentRepository;
import com.tns.placementmanagment.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    public StudentRepository studentRepository;

    // Finds the first missing positive student ID (1,2,3...) by scanning ordered sids.
    // This enforces gap-free sequential student IDs so deleted slots get reused.
    private Integer findNextAvailableId() {
        List<Integer> existingIds = studentRepository.findAllIds();
        int nextId = 1;
        for (Integer id : existingIds) {
            if (id != nextId) {
                return nextId;
            }
            nextId++;
        }
        return nextId;
    }

    @Override
    public Student createStudent(Student student) {
        student.setSid(findNextAvailableId());
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getStudent() {
        return (List<Student>) studentRepository.findAll();
    }

    @Override
    public Student updateStudent(Student student) {
        // Check if the student exists before updating
        if (student.getSid() != null && studentRepository.existsById(student.getSid())) {
            return studentRepository.save(student);
        }
        return null;
    }

    @Override
    public void deleteStudent(Integer id) {
        studentRepository.deleteById(id);
    }
}
