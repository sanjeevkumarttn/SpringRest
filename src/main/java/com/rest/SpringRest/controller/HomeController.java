package com.rest.SpringRest.controller;


import com.rest.SpringRest.entities.*;
import com.rest.SpringRest.exceptions.StudentNotFoundException;
import com.rest.SpringRest.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Locale;

@RestController
public class HomeController {

    @Autowired
    StudentService studentService;
    @Autowired
    MessageSource messageSource;

    //Question 1
    @PostMapping("/students")
    Student saveStudent(@RequestBody Student student) {
        studentService.saveStudent(student);
        return student;
    }

    @GetMapping("/students")
    List<Student> getStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/student/{id}")
    Student getStudentById(@PathVariable Integer id) {
        Student student = studentService.getStudentById(id);
        if (student == null)
            throw new StudentNotFoundException("Student not found..");
        return student;
    }

    @DeleteMapping("/student/{id}")
    void deleteStudent(@PathVariable Integer id) {
        Student student = studentService.getStudentById(id);
        if (student == null)
            throw new StudentNotFoundException("Student not found..");
        studentService.deleteStudentById(id);

    }

    //Question 3
    //@PostMapping("/students")
 /*   ResponseEntity<Student> saveStudent(@Valid @RequestBody Student student) {
        studentService.saveStudent(student);
        //return student;
        URI uri= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}").buildAndExpand(student.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }*/

    //Question 4
    @GetMapping("/")
    String helloWorld(@RequestHeader(name = "Accept-Language",required = false) Locale locale){
        System.out.println("hello world");
        System.out.println(locale.getLanguage());
        return messageSource.getMessage("good.morning.message",null,locale);
    }

    //Question 7

    //URI Versioning

    @GetMapping("/student/V1")
    StudentV1 getStudentV1(){
        return new StudentV1("Peter Parker");
    }

    @GetMapping("/student/V2")
    StudentV2 getStudentV2(){
        return new StudentV2(new Name("Peter", "Parker"));
    }

    //Parameter Versioning

    @GetMapping(value = "/student/param",params = "version=1")
    StudentV1 getStudentParam1() {
        return new StudentV1("Peter Parker");
    }

    @GetMapping(value = "/student/param",params = "version=2")
    StudentV2 getStudentParam2() {
        return new StudentV2(new Name("Peter", "Parker"));
    }

    //Header Versioning

    @GetMapping(value = "/student/header",headers = "API-VERSION=1")
    StudentV1 getStudentHeader1() {
        return new StudentV1("Peter Parker");
    }

    @GetMapping(value = "/student/header",headers = "API-VERSION=2")
    StudentV2 getStudentHeader2() {
        return new StudentV2(new Name("Peter", "Parker"));
    }

    //Question 8

    @GetMapping("/postObject")
    public Post getPostObject(){
        RestTemplate restTemplate = new RestTemplate();
        String url="https://jsonplaceholder.typicode.com/posts/1";
        ResponseEntity<Post> response = restTemplate.getForEntity(url,Post.class);
        System.out.println(response.getStatusCode().toString());
        System.out.println(response.getHeaders().toString());
        return response.getBody();
    }

    @PostMapping("/createPost")
    public ResponseEntity<Post> createPost(){
        String url="https://jsonplaceholder.typicode.com/posts";
        RestTemplate restTemplate= new RestTemplate();
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.add("Content-type","application/json; charset=UTF-8");
        HttpEntity<Post> request=new HttpEntity<>(new Post(10000L,10000L,"title1","description1"),httpHeaders);
        Post post=restTemplate.postForObject(url,request,Post.class);
        System.out.println(post);
        return new ResponseEntity<Post>(post, HttpStatus.CREATED);
    }
    @PutMapping("/updatePost")
    public ResponseEntity<Post> updatePost(){
        String url="https://jsonplaceholder.typicode.com/posts/1";
        RestTemplate restTemplate= new RestTemplate();
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.add("Content-type","application/json; charset=UTF-8");
        HttpEntity<Post> request=new HttpEntity<>(new Post(1L,100L,"title1","description1"),httpHeaders);
        return restTemplate.exchange(url, HttpMethod.PUT,request,Post.class);
    }
    @DeleteMapping("/deletePost")
    public void deletePost(){
        String url="https://jsonplaceholder.typicode.com/posts/1";
        RestTemplate restTemplate= new RestTemplate();
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.add("Content-type","application/json; charset=UTF-8");
        HttpEntity<Post> request=new HttpEntity<>(httpHeaders);
        restTemplate.exchange(url,HttpMethod.DELETE,request,Post.class);
    }

}
