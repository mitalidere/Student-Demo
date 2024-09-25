package com.example.student_jpa.service;

import com.example.student_jpa.model.Address;
import com.example.student_jpa.model.Student;
import com.example.student_jpa.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class StudentService {
    StudentRepository studentRepository;


    StudentService(StudentRepository studentRepository) {
        this.studentRepository=studentRepository;
    }

    @Value("${file.upload-dir}")
    String uploadDir;

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Student addStudent(String name, String city, int pincode, MultipartFile photo) throws Exception {
        Student student=new Student();
        Address address=new Address();

        address.setCity(city);
        address.setPincode(pincode);

        student.setName(name);
        student.setAddress(address);

        String fileName= UUID.randomUUID()+"_"+ photo.getOriginalFilename();
        Path filePath= Paths.get(uploadDir+fileName);
        photo.transferTo(filePath);
        student.setPhotoPath(filePath.toString());
        return studentRepository.save(student);
    }

    public Student updateStudent(int id, Student student) {
        return studentRepository.save(new Student(id, student.getName(), student.getAddress()));
    }
//
//    public String deleteStudent(int id) {
//        studentRepository.deleteById(id);
//        return "Record deleted";
//    }
}
