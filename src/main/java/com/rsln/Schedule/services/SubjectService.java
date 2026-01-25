package com.rsln.Schedule.services;

import com.rsln.Schedule.dtos.subject.SubjectRequestDto;
import com.rsln.Schedule.dtos.subject.SubjectResponseDto;
import com.rsln.Schedule.exceptions.NotFoundException;
import com.rsln.Schedule.mappers.SubjectMapper;
import com.rsln.Schedule.models.Lesson;
import com.rsln.Schedule.models.Subject;
import com.rsln.Schedule.repositories.SubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private static final Logger log =
            LoggerFactory.getLogger(SubjectService.class);

    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    public List<SubjectResponseDto> getAll() {
        log.info("get all subjects");
        return subjectRepository.findAll().stream().map(subjectMapper::toDto).toList();
    }

    public SubjectResponseDto getById(Long id) {
        log.info("get subject by id: {}", id);
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));
        return subjectMapper.toDto(subject);
    }

    public SubjectResponseDto create(SubjectRequestDto dto) {
        log.info("create subject: name={}", dto.name());
        Subject subject = subjectMapper.toEntity(dto);
        subjectRepository.save(subject);
        return subjectMapper.toDto(subject);
    }

    public SubjectResponseDto update(Long id, SubjectRequestDto dto) {
        log.info("update subject: id={}, name={}", id, dto.name());
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));

        subject.setName(dto.name());

        return subjectMapper.toDto(subjectRepository.save(subject));
    }

    public void delete(Long id) {
        log.info("delete subject: id={}", id);
        subjectRepository.deleteById(id);
    }
}
