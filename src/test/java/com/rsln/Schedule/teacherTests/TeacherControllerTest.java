package com.rsln.Schedule.teacherTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rsln.Schedule.controllers.TeacherController;
import com.rsln.Schedule.dtos.teacher.TeacherRequestDto;
import com.rsln.Schedule.dtos.teacher.TeacherResponseDto;
import com.rsln.Schedule.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherController.class)
@AutoConfigureMockMvc(addFilters = false)
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TeacherService teacherService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void getById_ok() throws Exception {
        TeacherResponseDto response = new TeacherResponseDto(1L, "Ivanov I.I.", "Math");

        when(teacherService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/teachers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fullName").value("Ivanov I.I."))
                .andExpect(jsonPath("$.department").value("Math"));
    }

    @Test
    void create_ok() throws Exception {
        TeacherRequestDto request = new TeacherRequestDto("Petrov P.P.", "Physics");
        TeacherResponseDto response = new TeacherResponseDto(1L, "Petrov P.P.", "Physics");

        when(teacherService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/teachers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fullName").value("Petrov P.P."))
                .andExpect(jsonPath("$.department").value("Physics"));
    }

    @Test
    void update_ok() throws Exception {
        TeacherRequestDto request = new TeacherRequestDto("Sidorov S.S.", "Chemistry");
        TeacherResponseDto response = new TeacherResponseDto(1L, "Sidorov S.S.", "Chemistry");

        when(teacherService.update(1L, request)).thenReturn(response);

        mockMvc.perform(put("/api/teachers/{id}", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fullName").value("Sidorov S.S."))
                .andExpect(jsonPath("$.department").value("Chemistry"));
    }

    @Test
    void delete_ok() throws Exception {
        doNothing().when(teacherService).delete(1L);

        mockMvc.perform(delete("/api/teachers/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void getAll_ok() throws Exception {
        TeacherResponseDto t1 = new TeacherResponseDto(1L, "Ivanov I.I.", "Math");
        TeacherResponseDto t2 = new TeacherResponseDto(2L, "Petrov P.P.", "Physics");

        when(teacherService.getAll()).thenReturn(List.of(t1, t2));

        mockMvc.perform(get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Ivanov I.I."))
                .andExpect(jsonPath("$[1].fullName").value("Petrov P.P."));
    }
}
