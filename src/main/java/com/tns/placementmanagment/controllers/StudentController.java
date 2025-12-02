package com.tns.placementmanagment.controllers;

import com.tns.placementmanagment.entities.Student;
import com.tns.placementmanagment.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/student")
@CrossOrigin(origins = "*")
public class StudentController {

    /**
     * Student REST controller (concise flow):
     * - Receives HTTP requests for /api/student and delegates to StudentService
     */

    @Autowired
    public StudentService studentService;

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudent();
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public Student updateStudent(@RequestBody Student student){
        Student updated = studentService.updateStudent(student);
        if (updated == null) {
            throw new IllegalArgumentException("Student not found with ID: " + student.getSid());
        }
        return updated;
    }

    @DeleteMapping(path = "/{id}")
    public void deleteStudent(@PathVariable(name = "id") Integer id) {
        studentService.deleteStudent(id);
    }

}
