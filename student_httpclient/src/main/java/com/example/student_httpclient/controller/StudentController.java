package com.example.student_httpclient.controller;

import com.example.student_httpclient.model.Student;
import com.example.student_httpclient.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    StudentService studentService;

    StudentController(StudentService studentService) {
        this.studentService=studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        try {
            return ResponseEntity.ok(studentService.getAllStudents());
        }
        catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        try {
            return ResponseEntity.ok(studentService.addStudent(student));
        }
        catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestParam int id, @RequestBody Student student) {
        try {
            return ResponseEntity.ok(studentService.updateStudent(id, student));
        }
        catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> updateStudent(@RequestParam int id) {
        try {
            return ResponseEntity.ok(studentService.deleteStudent(id));
        }
        catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/toCSV")
    public ResponseEntity<String> databaseToCsv(@RequestParam String CsvFilePath) {
        try {
            return ResponseEntity.ok(studentService.databaseToCsv(CsvFilePath));
        }
        catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
