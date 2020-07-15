package com.example.TestMVC;

import com.example.TestMVC.controller.DoctorController;
import com.example.TestMVC.model.Appointment;
import com.example.TestMVC.model.Doctor;
import com.example.TestMVC.service.AppointmentService;
import com.example.TestMVC.service.DoctorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.util.Collections;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = DoctorController.class)
class TestMvcApplicationTests {


    Appointment mockAppointment = new Appointment(2, "Hanna D.", "2020-08-01 14:20", 1);
    Appointment mockAppointment2 = new Appointment(3, "Dana D.", "2020-08-01 15:20", 1);

    Doctor mockDoctor = new Doctor(1, "Alan Carter", "surgeon",
            Arrays.asList(mockAppointment));


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DoctorService doctorService;
    @MockBean
    private AppointmentService appointmentService;

    @Test
    public void getDoctorDetails() throws Exception {
        Mockito.when(doctorService.findDoctorById(Mockito.anyInt())).thenReturn(mockDoctor);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/doctors/1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"doctorId\":1,\"name\":\"Alan Carter\",\"specialization\":\"surgeon\"," +
                "\"appointments\":[{\"id\":2,\"patientName\":\"Hanna D.\",\"dateAndTime\":\"2020-08-01 " +
                "14:20\",\"doctorId\":1}]}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

    }

    @Test
    public void getDoctorByName() throws Exception {
        Mockito.when(doctorService.findDoctorByName(Mockito.anyString())).thenReturn(mockDoctor);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/search?name=Carter")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"doctorId\":1,\"name\":\"Alan Carter\",\"specialization\":\"surgeon\"," +
                "\"appointments\":[{\"id\":2,\"patientName\":\"Hanna D.\",\"dateAndTime\":\"2020-08-01 " +
                "14:20\",\"doctorId\":1}]}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

    }

    @Test
    public void createDoctor() throws Exception {
        Mockito.when(
                doctorService.saveDoctor(Mockito.any(Doctor.class))).thenReturn(mockDoctor);
        String expected = "{\"doctorId\":1,\"name\":\"Alan Carter\",\"specialization\":\"surgeon\"," +
                "\"appointments\":[{\"id\":2,\"patientName\":\"Hanna D.\",\"dateAndTime\":\"2020-08-01 " +
                "14:20\",\"doctorId\":1}]}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/doctors")
                .accept(MediaType.APPLICATION_JSON).content(expected)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(expected, result.getResponse().getContentAsString());
    }


    @Test
    public void createAppoinment() throws Exception {
        Mockito.when(
                appointmentService.saveAppointment(Mockito.any(Appointment.class))).thenReturn(mockAppointment);
        String expected = "{\"id\":2,\"patientName\":\"Hanna D.\",\"dateAndTime\":\"2020-08-01 " +
                "14:20\",\"doctorId\":1}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/appointments")
                .accept(MediaType.APPLICATION_JSON).content(expected)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void getDoctorsList()  {
        Mockito.when(doctorService.findAllDoctors()).thenReturn(Collections.singletonList(mockDoctor));
        assertEquals(1, doctorService.findAllDoctors().size());
    }

    @Test
    public void getAppointmentList()  {
        Mockito.when(appointmentService.findAllAppointments()).thenReturn(Arrays.asList(mockAppointment,
                mockAppointment2));
        assertEquals(2, appointmentService.findAllAppointments().size());
    }

    @Test
    public void saveAppointment() {
        when(appointmentService.saveAppointment(mockAppointment)).thenReturn(mockAppointment);
        assertEquals(mockAppointment, appointmentService.saveAppointment(mockAppointment));
    }

    @Test
    public void savesDoctor() {
        when(doctorService.saveDoctor(mockDoctor)).thenReturn(mockDoctor);
        assertEquals(mockDoctor, doctorService.saveDoctor(mockDoctor));
    }

    @Test
    public void getAppointmentListByDoctorId() throws Exception {
        Mockito.when(appointmentService.findAppoinmentByDoctorId(Mockito.anyInt())).thenReturn(Arrays.asList(mockAppointment,
                mockAppointment2));

        mockMvc.perform(get("/appointments/1"))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].patientName", is("Hanna D.")))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].patientName", is("Dana D.")));
        verify(appointmentService, times(1)).findAppoinmentByDoctorId(1);
        verifyNoMoreInteractions(appointmentService);
       assertEquals(2, appointmentService.findAppoinmentByDoctorId(mockAppointment.getDoctorId()).size());
    }


}
