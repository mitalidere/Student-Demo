package com.example.student_httpclient.service;

import com.example.student_httpclient.model.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Service
public class StudentService {
    HttpClient httpClient;
    ObjectMapper objectMapper;

    @Value("${external.api.base-url}")
    private String baseUrl;

    StudentService() {
        this.httpClient=HttpClient.newHttpClient();
        this.objectMapper=new ObjectMapper();
    }

    public List<Student> getAllStudents() throws Exception {
        HttpRequest httpRequest= HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .GET()
                .build();

        HttpResponse<String> httpResponse=httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
        return Arrays.asList(objectMapper.readValue(httpResponse.body(),Student[].class));
    }

    public Student addStudent(Student student) throws Exception {
        String studentJSON=objectMapper.writeValueAsString(student);

        HttpRequest httpRequest=HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(studentJSON))
                .build();

        System.out.println(httpRequest);
        HttpResponse<String> httpResponse=httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(httpResponse.body(),Student.class);
    }

    public Student updateStudent(int id, Student student) throws Exception {
        String studentJson=objectMapper.writeValueAsString(student);

        HttpRequest httpRequest= HttpRequest.newBuilder()
                .uri(URI.create(baseUrl+"?id="+id))
                .header("Content-type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(studentJson))
                .build();

        HttpResponse<String> httpResponse=httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(httpResponse.body(), Student.class);
    }

    public String deleteStudent(int id) throws Exception {
        HttpRequest httpRequest= HttpRequest.newBuilder()
                .uri(URI.create(baseUrl+"?id="+id))
                .DELETE()
                .build();

        HttpResponse<String> httpResponse=httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return httpResponse.body();
    }

    public String databaseToCsv(String CsvFilePath) throws Exception {
        List<Student> studentList=getAllStudents();
        File file=new File(CsvFilePath);
        try(FileWriter fw = new FileWriter(file, true)) {
            if(file.length()==0) {
                fw.append("Id, Name, Address Id, City, Pincode\n");
            }
            for(Student student: studentList) {
                fw.append(student.getId()+","+student.getName()+","+student.getAddress().getId()+","+student.getAddress().getCity()+","+student.getAddress().getPincode()+"\n");
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Database not converted to CSV");
        }
        return "Database converted to CSV";
    }
}
