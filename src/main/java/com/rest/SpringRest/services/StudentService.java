package com.rest.SpringRest.services;

import com.rest.SpringRest.entities.Student;
import com.rest.SpringRest.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    // Question 1
    public void saveStudent(Student student){
        studentRepository.save(student);
    }

    public List<Student> getAllStudents(){
        return (List<Student>)studentRepository.findAll();
    }

    public Student getStudentById(Integer id){
        Optional<Student> optional = studentRepository.findById(id);
        return optional.isPresent() ? optional.get(): null;
    }

    public void deleteStudentById(Integer id){
        studentRepository.deleteById(id);
    }

    //Question 3
   /* public void saveStudent(Student student){
        studentRepository.save(student);
    }*/
}
