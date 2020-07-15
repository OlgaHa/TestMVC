package com.example.TestMVC.dao;

import com.example.TestMVC.model.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Integer> {


}
