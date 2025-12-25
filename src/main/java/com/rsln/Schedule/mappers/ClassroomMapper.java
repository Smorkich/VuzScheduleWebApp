package com.rsln.Schedule.mappers;

import com.rsln.Schedule.dtos.classroom.ClassroomRequestDto;
import com.rsln.Schedule.dtos.classroom.ClassroomResponseDto;
import com.rsln.Schedule.models.Classroom;
import org.springframework.stereotype.Component;

@Component
public class ClassroomMapper {

    public Classroom toEntity(ClassroomRequestDto dto) {
        Classroom c = new Classroom();
        c.setName(dto.name());
        return c;
    }

    public ClassroomResponseDto toDto(Classroom entity) {
        return new ClassroomResponseDto(
                entity.getId(),
                entity.getName()
        );
    }
}
