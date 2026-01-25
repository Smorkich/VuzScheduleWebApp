package com.rsln.Schedule.mappers;

import com.rsln.Schedule.dtos.teacher.TeacherRequestDto;
import com.rsln.Schedule.dtos.teacher.TeacherResponseDto;
import com.rsln.Schedule.models.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

    public Teacher toEntity(TeacherRequestDto dto) {
        Teacher t = new Teacher();
        t.setFullName(dto.fullName());
        t.setDepartment(dto.department());
        return t;
    }

    public TeacherResponseDto toDto(Teacher entity) {
        return new TeacherResponseDto(
                entity.getId(),
                entity.getFullName(),
                entity.getDepartment()
        );
    }
}
