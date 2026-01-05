package com.rsln.Schedule.services;

import com.rsln.Schedule.dtos.teacher.TeacherRequestDto;
import com.rsln.Schedule.dtos.teacher.TeacherResponseDto;
import com.rsln.Schedule.exceptions.NotFoundException;
import com.rsln.Schedule.mappers.TeacherMapper;
import com.rsln.Schedule.models.Teacher;
import com.rsln.Schedule.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private static final Logger log =
            LoggerFactory.getLogger(TeacherService.class);

    public TeacherService(TeacherRepository teacherRepository,
                          TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    public List<TeacherResponseDto> getAll() {
        log.info("get all Teachers");
        return teacherRepository.findAll()
                .stream()
                .map(teacherMapper::toDto)
                .toList();
    }

    public TeacherResponseDto getById(Long id) {
        log.info("get teacher by id: {}", id);
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Преподаватель не найден"));
        return teacherMapper.toDto(teacher);
    }

    public TeacherResponseDto create(TeacherRequestDto dto) {
        log.info("create teacher: name={}, department={}", dto.fullName(), dto.department());
        Teacher teacher = teacherMapper.toEntity(dto);
        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    @Transactional
    public TeacherResponseDto update(Long id, TeacherRequestDto dto) {
        log.info("update teacher: id={}, name={}, department={}", id, dto.fullName(), dto.department());
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Преподаватель не найден"));

        teacher.setFullName(dto.fullName());
        teacher.setDepartment(dto.department());

        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    public void delete(Long id) {
        log.info("delete teacher: id={}", id);
        teacherRepository.deleteById(id);
    }
}
