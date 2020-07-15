package com.example.TestMVC.service;

import com.example.TestMVC.model.Appointment;
import com.example.TestMVC.model.Doctor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AppointmentService {

     List<Appointment> findAllAppointments();
     Appointment saveAppointment(Appointment appointment);
     List<Appointment> findAppoinmentByDoctorId(int doctorId);

}
