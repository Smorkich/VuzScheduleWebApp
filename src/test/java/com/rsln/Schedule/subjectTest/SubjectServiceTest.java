package com.rsln.Schedule.subjectTest;

import com.rsln.Schedule.dtos.subject.SubjectRequestDto;
import com.rsln.Schedule.dtos.subject.SubjectResponseDto;
import com.rsln.Schedule.exceptions.NotFoundException;
import com.rsln.Schedule.mappers.SubjectMapper;
import com.rsln.Schedule.models.Subject;
import com.rsln.Schedule.repositories.SubjectRepository;
import com.rsln.Schedule.services.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private SubjectMapper subjectMapper;

    @InjectMocks
    private SubjectService subjectService;

    private Subject subject;
    private SubjectResponseDto responseDto;
    private SubjectRequestDto requestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        subject = new Subject();
        subject.setId(1L);
        subject.setName("Math");

        responseDto = new SubjectResponseDto(1L, "Math");
        requestDto = new SubjectRequestDto("Math");
    }

    @Test
    void getAll_shouldReturnList() {
        when(subjectRepository.findAll()).thenReturn(List.of(subject));
        when(subjectMapper.toDto(subject)).thenReturn(responseDto);

        List<SubjectResponseDto> result = subjectService.getAll();
        assertEquals(1, result.size());
        assertEquals("Math", result.get(0).name());
    }

    @Test
    void getById_found() {
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(subjectMapper.toDto(subject)).thenReturn(responseDto);

        SubjectResponseDto result = subjectService.getById(1L);
        assertEquals("Math", result.name());
    }

    @Test
    void getById_notFound() {
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> subjectService.getById(1L));
    }

    @Test
    void create_shouldReturnDto() {
        when(subjectMapper.toEntity(requestDto)).thenReturn(subject);
        when(subjectRepository.save(subject)).thenReturn(subject);
        when(subjectMapper.toDto(subject)).thenReturn(responseDto);

        SubjectResponseDto result = subjectService.create(requestDto);
        assertEquals("Math", result.name());
    }

    @Test
    void update_found() {
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(subjectRepository.save(subject)).thenReturn(subject);
        when(subjectMapper.toDto(subject)).thenReturn(responseDto);

        SubjectResponseDto result = subjectService.update(1L, requestDto);
        assertEquals("Math", result.name());
        verify(subjectRepository).save(subject);
    }

    @Test
    void update_notFound() {
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> subjectService.update(1L, requestDto));
    }

    @Test
    void delete_shouldCallRepository() {
        doNothing().when(subjectRepository).deleteById(1L);
        subjectService.delete(1L);
        verify(subjectRepository, times(1)).deleteById(1L);
    }
}
