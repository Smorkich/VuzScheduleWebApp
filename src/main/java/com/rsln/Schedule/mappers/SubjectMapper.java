package com.rsln.Schedule.mappers;

import com.rsln.Schedule.dtos.subject.SubjectRequestDto;
import com.rsln.Schedule.dtos.subject.SubjectResponseDto;
import com.rsln.Schedule.models.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {

    public Subject toEntity(SubjectRequestDto dto) {
        Subject s = new Subject();
        s.setName(dto.name());
        return s;
    }

    public SubjectResponseDto toDto(Subject entity) {
        return new SubjectResponseDto(
                entity.getId(),
                entity.getName()
        );
    }
}
