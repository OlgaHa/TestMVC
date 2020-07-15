package com.example.TestMVC.controller;

import com.example.TestMVC.model.Appointment;
import com.example.TestMVC.model.Doctor;
import com.example.TestMVC.service.AppointmentService;
import com.example.TestMVC.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;


    @GetMapping("/doctors")
    public List<Doctor> getDoctors() {
        return doctorService.findAllDoctors();
    }


    @GetMapping("/doctors/{id}")
    public Doctor getDoctorByid(@PathVariable int id) {
        Doctor doctor;
        try {
            doctor = doctorService.findDoctorById(id);
        } catch (Throwable t) {
            throw new RuntimeException("Sorry, it seems, we don't have doctor by this id  " + t.getMessage());
        }
        return doctor;
    }


    @GetMapping("/search")
    public Doctor getDoctorByName(@RequestParam String name) {
        Doctor doctor;
        try {
            doctor = doctorService.findDoctorByName(name);
        } catch (Throwable t) {
            throw new RuntimeException("Sorry, it seems, we don't have doctor by this name " + t.getMessage());
        }
        return doctor;
    }

    @PostMapping("/doctors")
    public Doctor saveDoctor(@RequestBody Doctor doctor) {
        doctorService.saveDoctor(doctor);
        return doctor;
    }

    @GetMapping("/appointments")
    public List<Appointment> getAppointments() {
        return appointmentService.findAllAppointments();
    }

    @GetMapping("/appointments/{id}")
    public List<Appointment> getDoctorAppointments(@PathVariable int id) {
        return appointmentService.findAppoinmentByDoctorId(id);
    }

    @PostMapping("/appointments")
    public Appointment saveAppointment(@RequestBody Appointment appointment) {
        appointmentService.saveAppointment(appointment);
        return appointment;
    }
}
