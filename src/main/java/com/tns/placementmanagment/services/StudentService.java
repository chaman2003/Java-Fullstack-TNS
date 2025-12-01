package com.tns.placementmanagment.services;

import com.tns.placementmanagment.entities.Student;

import java.util.List;

public interface StudentService {
    Student createStudent(Student student);
    List<Student> getStudent();
    Student updateStudent(Student student);
    void deleteStudent(Integer id);
}
