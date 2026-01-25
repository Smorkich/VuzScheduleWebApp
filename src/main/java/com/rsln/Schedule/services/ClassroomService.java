package com.rsln.Schedule.services;

import com.rsln.Schedule.dtos.classroom.ClassroomRequestDto;
import com.rsln.Schedule.dtos.classroom.ClassroomResponseDto;
import com.rsln.Schedule.exceptions.NotFoundException;
import com.rsln.Schedule.mappers.ClassroomMapper;
import com.rsln.Schedule.models.Classroom;
import com.rsln.Schedule.repositories.ClassroomRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {

    private final ClassroomRepository repository;
    private final ClassroomMapper mapper;
    private static final Logger log =
            LoggerFactory.getLogger(ClassroomService.class);

    public ClassroomService(ClassroomRepository repository, ClassroomMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ClassroomResponseDto> getAll() {
        log.info("get all Classrooms");
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public ClassroomResponseDto getById(Long id) {
        log.info("get Classroom by id: {}", id);
        Classroom classroom = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Аудитория не найдена"));
        return mapper.toDto(classroom);
    }

    public ClassroomResponseDto create(ClassroomRequestDto dto) {
        log.info("create classroom: name={}", dto.name());
        Classroom classroom = mapper.toEntity(dto);
        repository.save(classroom);
        return mapper.toDto(classroom);
    }

    public ClassroomResponseDto update(Long id, ClassroomRequestDto dto) {
        log.info("update classroom: id={}, name={}", id, dto.name());
        Classroom classroom = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Аудитория не найдена"));
        classroom.setName(dto.name());
        return mapper.toDto(repository.save(classroom));
    }

    public void delete(Long id) {
        log.info("delete classroom: id={}", id);
        Classroom classroom = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Аудитория не найдена"));
        repository.delete(classroom);
    }
}
