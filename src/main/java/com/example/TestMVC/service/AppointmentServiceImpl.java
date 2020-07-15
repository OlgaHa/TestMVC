package com.example.TestMVC.service;

import com.example.TestMVC.dao.AppointmentRepository;
import com.example.TestMVC.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;


    @Override
    public List<Appointment> findAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        appointmentRepository.findAll().forEach(appointments::add);
        return appointments;
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAppoinmentByDoctorId(int doctorId) {
            List<Appointment> appointments = new ArrayList<>();
            appointmentRepository.findAppointmentByDoctorId(doctorId).forEach(appointments::add);
            return appointments;
    }


}
