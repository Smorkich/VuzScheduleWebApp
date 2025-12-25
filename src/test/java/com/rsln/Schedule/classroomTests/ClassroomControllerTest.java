package com.rsln.Schedule.classroomTests;

import com.rsln.Schedule.controllers.ClassroomController;
import com.rsln.Schedule.dtos.classroom.ClassroomRequestDto;
import com.rsln.Schedule.dtos.classroom.ClassroomResponseDto;
import com.rsln.Schedule.exceptions.NotFoundException;
import com.rsln.Schedule.mappers.ClassroomMapper;
import com.rsln.Schedule.services.ClassroomService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ClassroomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClassroomService classroomService;

    @MockitoBean
    private ClassroomMapper classroomMapper;

    @Test
    void getAll_shouldReturnListOfDtos() throws Exception {
        ClassroomResponseDto dto1 = new ClassroomResponseDto(1L, "A-101");
        ClassroomResponseDto dto2 = new ClassroomResponseDto(2L, "B-202");

        when(classroomService.getAll()).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/api/classrooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("A-101"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("B-202"));
    }

    @Test
    void getById_shouldReturn200() throws Exception {
        ClassroomResponseDto dto = new ClassroomResponseDto(1L, "A-101");

        when(classroomService.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/classrooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("A-101"));
    }
    @Test
    void getById_shouldReturn404_whenNotFound() throws Exception {
        when(classroomService.getById(1L))
                .thenThrow(new NotFoundException("Аудитория не найдена"));

        mockMvc.perform(get("/api/classrooms/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_shouldReturn201() throws Exception {
        ClassroomRequestDto request = new ClassroomRequestDto("B-202");
        ClassroomResponseDto dto = new ClassroomResponseDto(2L, "B-202");

        when(classroomService.create(any())).thenReturn(dto);

        mockMvc.perform(post("/api/classrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"B-202\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("B-202"));
    }

    @Test
    void update_shouldReturn200() throws Exception {
        ClassroomRequestDto request = new ClassroomRequestDto("C-303");
        ClassroomResponseDto dto = new ClassroomResponseDto(3L, "C-303");

        when(classroomService.update(eq(3L), any())).thenReturn(dto);

        mockMvc.perform(put("/api/classrooms/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"C-303\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("C-303"));
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        doNothing().when(classroomService).delete(4L);

        mockMvc.perform(delete("/api/classrooms/4"))
                .andExpect(status().isNoContent());
    }

}
