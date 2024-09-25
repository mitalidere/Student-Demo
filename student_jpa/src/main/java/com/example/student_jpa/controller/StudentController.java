package com.example.student_jpa.controller;

import com.example.student_jpa.model.Address;
import com.example.student_jpa.model.Student;
import com.example.student_jpa.service.StudentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    StudentService studentService;

    StudentController(StudentService studentService) {
        this.studentService=studentService;
    }

    @GetMapping
    public List<Student> getStudent() {
        return studentService.getAllStudent();
    }

    @PostMapping
    public Student addStudent(@RequestParam("name") String name,
                              @RequestParam("city") String city,
                              @RequestParam("pincode") int pincode,
                              @RequestParam("photo") MultipartFile photo) throws Exception {
        return studentService.addStudent(name, city, pincode, photo);
    }

    @PutMapping
    public Student updateStudent(@RequestParam int id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }
//
//    @DeleteMapping
//    public String deleteStudent(@RequestParam int id) {
//        return studentService.deleteStudent(id);
//    }
}
