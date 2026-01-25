package com.rsln.Schedule.lessonTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rsln.Schedule.controllers.LessonController;
import com.rsln.Schedule.dtos.classroom.ClassroomResponseDto;
import com.rsln.Schedule.dtos.group.GroupResponseDto;
import com.rsln.Schedule.dtos.lesson.LessonRequestDto;
import com.rsln.Schedule.dtos.lesson.LessonResponseDto;
import com.rsln.Schedule.dtos.subject.SubjectResponseDto;
import com.rsln.Schedule.dtos.teacher.TeacherResponseDto;
import com.rsln.Schedule.services.LessonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LessonController.class)
@AutoConfigureMockMvc(addFilters = false)
class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LessonService lessonService;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }
    @Test
    void getById_ok() throws Exception {
        LessonResponseDto response = lessonResponseDto();

        when(lessonService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/lessons/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.subject.name").value("Math"))
                .andExpect(jsonPath("$.group.name").value("IV-1"))
                .andExpect(jsonPath("$.dayOfWeek").value("MONDAY"));
    }

    @Test
    void create_ok() throws Exception {
        LessonRequestDto request = new LessonRequestDto(1L,
                1L, 1L, 1L,"Лекция", DayOfWeek.MONDAY,
                LocalTime.of(10, 0),
                LocalTime.of(11, 30)
        );

        LessonResponseDto response = lessonResponseDto();

        when(lessonService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.subject.name").value("Math"))
                .andExpect(jsonPath("$.group.name").value("IV-1"));

    }

    @Test
    void create_validationError() throws Exception {
        LessonRequestDto invalidRequest = new LessonRequestDto(
                null, 1L, 1L, 1L, "Лекция",
                DayOfWeek.MONDAY,
                LocalTime.of(10,0),
                LocalTime.of(11,30)
        );

        mockMvc.perform(post("/api/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void getSchedule_ok() throws Exception {
        LessonResponseDto lesson = lessonResponseDto();

        when(lessonService.getByGroupAndDay(2L, DayOfWeek.FRIDAY))
                .thenReturn(List.of(lesson));

        mockMvc.perform(get("/api/lessons/schedule")
                        .param("groupId", "2")
                        .param("day", "friday"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].subject.name").value("Math"))
                .andExpect(jsonPath("$[0].group.name").value("IV-1"));

    }
    @Test
    void delete_ok() throws Exception {
        doNothing().when(lessonService).delete(1L);

        mockMvc.perform(delete("/api/lessons/{id}", 1L))
                .andExpect(status().isOk());

        verify(lessonService).delete(1L);
    }

    private GroupResponseDto groupDto() {
        return new GroupResponseDto(1L, "IV-1", 3);
    }

    private SubjectResponseDto subjectDto() {
        return new SubjectResponseDto(1L, "Math");
    }

    private TeacherResponseDto teacherDto() {
        return new TeacherResponseDto(1L, "Ivanov I.I.", "Math");
    }

    private ClassroomResponseDto classroomDto() {
        return new ClassroomResponseDto(1L, "101A");
    }
    private LessonResponseDto lessonResponseDto() {
        return new LessonResponseDto(
                1L,
                groupDto(),
                subjectDto(),
                teacherDto(),
                classroomDto(),
                "LECTURE",
                DayOfWeek.MONDAY,
                LocalTime.of(10, 0),
                LocalTime.of(11, 30)
        );
    }

}

