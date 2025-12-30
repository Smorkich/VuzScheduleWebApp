package com.rsln.Schedule.teacherTests;

import com.rsln.Schedule.dtos.teacher.TeacherRequestDto;
import com.rsln.Schedule.dtos.teacher.TeacherResponseDto;
import com.rsln.Schedule.exceptions.NotFoundException;
import com.rsln.Schedule.mappers.TeacherMapper;
import com.rsln.Schedule.models.Teacher;
import com.rsln.Schedule.repositories.TeacherRepository;
import com.rsln.Schedule.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TeacherMapper teacherMapper;

    @InjectMocks
    private TeacherService teacherService;

    private Teacher teacher;
    private TeacherResponseDto responseDto;
    private TeacherRequestDto requestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFullName("Ivanov I.I.");
        teacher.setDepartment("Math");

        responseDto = new TeacherResponseDto(1L, "Ivanov I.I.", "Math");
        requestDto = new TeacherRequestDto("Ivanov I.I.", "Math");
    }

    @Test
    void getAll_shouldReturnList() {
        when(teacherRepository.findAll()).thenReturn(List.of(teacher));
        when(teacherMapper.toDto(teacher)).thenReturn(responseDto);

        List<TeacherResponseDto> result = teacherService.getAll();
        assertEquals(1, result.size());
        assertEquals("Ivanov I.I.", result.get(0).fullName());
    }

    @Test
    void getById_found() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherMapper.toDto(teacher)).thenReturn(responseDto);

        TeacherResponseDto result = teacherService.getById(1L);
        assertEquals("Ivanov I.I.", result.fullName());
    }

    @Test
    void getById_notFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> teacherService.getById(1L));
    }

    @Test
    void create_shouldReturnDto() {
        when(teacherMapper.toEntity(requestDto)).thenReturn(teacher);
        when(teacherRepository.save(teacher)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(responseDto);

        TeacherResponseDto result = teacherService.create(requestDto);
        assertEquals("Ivanov I.I.", result.fullName());
    }

    @Test
    void update_found() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(teacher)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(responseDto);

        TeacherResponseDto result = teacherService.update(1L, requestDto);
        assertEquals("Ivanov I.I.", result.fullName());
        verify(teacherRepository).save(teacher);
    }

    @Test
    void update_notFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> teacherService.update(1L, requestDto));
    }

    @Test
    void delete_shouldCallRepository() {
        doNothing().when(teacherRepository).deleteById(1L);
        teacherService.delete(1L);
        verify(teacherRepository, times(1)).deleteById(1L);
    }
}
