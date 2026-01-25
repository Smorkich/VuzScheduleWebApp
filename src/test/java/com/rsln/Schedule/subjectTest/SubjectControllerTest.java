package com.rsln.Schedule.subjectTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rsln.Schedule.controllers.SubjectController;
import com.rsln.Schedule.dtos.subject.SubjectRequestDto;
import com.rsln.Schedule.dtos.subject.SubjectResponseDto;
import com.rsln.Schedule.services.SubjectService;
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

@WebMvcTest(SubjectController.class)
@AutoConfigureMockMvc(addFilters = false)
class SubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubjectService subjectService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void getById_ok() throws Exception {
        SubjectResponseDto response = new SubjectResponseDto(1L, "Math");

        when(subjectService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/subjects/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Math"));
    }

    @Test
    void create_ok() throws Exception {
        SubjectRequestDto request = new SubjectRequestDto("Physics");
        SubjectResponseDto response = new SubjectResponseDto(1L, "Physics");

        when(subjectService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/subjects")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Physics"));
    }

    @Test
    void update_ok() throws Exception {
        SubjectRequestDto request = new SubjectRequestDto("Chemistry");
        SubjectResponseDto response = new SubjectResponseDto(1L, "Chemistry");

        when(subjectService.update(1L, request)).thenReturn(response);

        mockMvc.perform(put("/api/subjects/{id}", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Chemistry"));
    }

    @Test
    void delete_ok() throws Exception {
        doNothing().when(subjectService).delete(1L);

        mockMvc.perform(delete("/api/subjects/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void getAll_ok() throws Exception {
        SubjectResponseDto s1 = new SubjectResponseDto(1L, "Math");
        SubjectResponseDto s2 = new SubjectResponseDto(2L, "Physics");

        when(subjectService.getAll()).thenReturn(List.of(s1, s2));

        mockMvc.perform(get("/api/subjects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Math"))
                .andExpect(jsonPath("$[1].name").value("Physics"));
    }
}
