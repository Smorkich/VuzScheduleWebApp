package com.rsln.Schedule.mappers;

import com.rsln.Schedule.dtos.group.GroupRequestDto;
import com.rsln.Schedule.dtos.group.GroupResponseDto;
import com.rsln.Schedule.models.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {

    public Group toEntity(GroupRequestDto dto) {
        Group g = new Group();
        g.setName(dto.name());
        g.setCourse(dto.course());
        return g;
    }

    public GroupResponseDto toDto(Group entity) {
        return new GroupResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getCourse()
        );
    }
}

