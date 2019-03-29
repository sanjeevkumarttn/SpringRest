package com.rest.SpringRest.repositories;

import com.rest.SpringRest.entities.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
}
