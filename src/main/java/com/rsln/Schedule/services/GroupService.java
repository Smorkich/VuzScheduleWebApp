package com.rsln.Schedule.services;

import com.rsln.Schedule.dtos.group.GroupRequestDto;
import com.rsln.Schedule.dtos.group.GroupResponseDto;
import com.rsln.Schedule.exceptions.NotFoundException;
import com.rsln.Schedule.mappers.GroupMapper;
import com.rsln.Schedule.models.Classroom;
import com.rsln.Schedule.models.Group;
import com.rsln.Schedule.repositories.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private static final Logger log =
            LoggerFactory.getLogger(GroupService.class);

    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    public List<GroupResponseDto> getAll() {
        log.info("get all groups");
        return groupRepository.findAll().stream().map(groupMapper::toDto).toList();
    }

    public GroupResponseDto getById(Long id) {
        log.info("get Group by id: {}", id);
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Группа не найдена"));
        return groupMapper.toDto(group);
    }

    public GroupResponseDto create(GroupRequestDto dto) {
        log.info("create group: name={}, course={}", dto.name(), dto.course());
        Group group = groupMapper.toEntity(dto);
        Group saved = groupRepository.save(group);
        return groupMapper.toDto(saved);
        //groupRepository.save(group);
        //return groupMapper.toDto(group);
    }

    public GroupResponseDto update(Long id, GroupRequestDto updated) {
        log.info("update group: id={}, name={}, course={}", id, updated.name(), updated.course());
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Группа не найдена"));
        group.setName(updated.name());
        return groupMapper.toDto(groupRepository.save(group));
    }

    public void delete(Long id) {
        log.info("delete group: id={}", id);
        groupRepository.deleteById(id);
    }
}
