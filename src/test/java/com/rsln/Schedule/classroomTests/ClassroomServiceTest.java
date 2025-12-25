package com.rsln.Schedule.classroomTests;

import com.rsln.Schedule.dtos.classroom.ClassroomRequestDto;
import com.rsln.Schedule.dtos.classroom.ClassroomResponseDto;
import com.rsln.Schedule.exceptions.NotFoundException;
import com.rsln.Schedule.mappers.ClassroomMapper;
import com.rsln.Schedule.models.Classroom;
import com.rsln.Schedule.repositories.ClassroomRepository;
import com.rsln.Schedule.services.ClassroomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassroomServiceTest {

    @Mock
    private ClassroomRepository classroomRepository;

    @Mock
    private ClassroomMapper classroomMapper;

    @InjectMocks
    private ClassroomService classroomService;


    @Test
    void getAll_shouldReturnListOfDtos() {
        Classroom c1 = new Classroom(); c1.setId(1L); c1.setName("A-101");
        Classroom c2 = new Classroom(); c2.setId(2L); c2.setName("B-202");

        ClassroomResponseDto dto1 = new ClassroomResponseDto(1L, "A-101");
        ClassroomResponseDto dto2 = new ClassroomResponseDto(2L, "B-202");

        when(classroomRepository.findAll()).thenReturn(List.of(c1, c2));
        when(classroomMapper.toDto(c1)).thenReturn(dto1);
        when(classroomMapper.toDto(c2)).thenReturn(dto2);

        List<ClassroomResponseDto> result = classroomService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(dto1, dto2);
    }


    @Test
    void getById_shouldReturnClassroom_whenExists() {
        Classroom classroom = new Classroom();
        classroom.setId(1L);
        classroom.setName("A-101");

        ClassroomResponseDto dto = new ClassroomResponseDto(1L, "A-101");

        when(classroomRepository.findById(1L)).thenReturn(Optional.of(classroom));
        when(classroomMapper.toDto(classroom)).thenReturn(dto);

        ClassroomResponseDto result = classroomService.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("A-101");
    }

    @Test
    void getById_shouldThrowException_whenNotFound() {
        when(classroomRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> classroomService.getById(1L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void create_shouldReturnDto() {
        ClassroomRequestDto request = new ClassroomRequestDto("C-303");
        Classroom entity = new Classroom(); entity.setName("C-303");
        ClassroomResponseDto dto = new ClassroomResponseDto(3L, "C-303");

        when(classroomMapper.toEntity(request)).thenReturn(entity);
        when(classroomRepository.save(entity)).thenReturn(entity);
        when(classroomMapper.toDto(entity)).thenReturn(dto);

        ClassroomResponseDto result = classroomService.create(request);

        assertThat(result.id()).isEqualTo(3L);
        assertThat(result.name()).isEqualTo("C-303");
    }

    @Test
    void update_shouldReturnUpdatedDto() {
        ClassroomRequestDto request = new ClassroomRequestDto("D-404");
        Classroom entity = new Classroom(); entity.setId(4L); entity.setName("Old");
        ClassroomResponseDto dto = new ClassroomResponseDto(4L, "D-404");

        when(classroomRepository.findById(4L)).thenReturn(Optional.of(entity));
        when(classroomRepository.save(entity)).thenReturn(entity);
        when(classroomMapper.toDto(entity)).thenReturn(dto);

        ClassroomResponseDto result = classroomService.update(4L, request);

        assertThat(result.id()).isEqualTo(4L);
        assertThat(result.name()).isEqualTo("D-404");
    }

    @Test
    void update_shouldThrowNotFound_whenIdNotExist() {
        ClassroomRequestDto request = new ClassroomRequestDto("D-404");
        when(classroomRepository.findById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> classroomService.update(5L, request))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void delete_shouldCallRepositoryDelete() {
        Classroom entity = new Classroom(); entity.setId(6L); entity.setName("E-505");

        when(classroomRepository.findById(6L)).thenReturn(Optional.of(entity));

        classroomService.delete(6L);

        verify(classroomRepository).delete(entity);
    }

    @Test
    void delete_shouldThrowNotFound_whenIdNotExist() {
        when(classroomRepository.findById(7L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> classroomService.delete(7L))
                .isInstanceOf(NotFoundException.class);
    }

}
