package com.example.TestMVC.service;

import com.example.TestMVC.model.Doctor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface DoctorService {

     List<Doctor> findAllDoctors();
     Doctor findDoctorById(Integer id);
     Doctor findDoctorByName(String name);
     Doctor saveDoctor(Doctor doctor);

}
