package com.rsln.Schedule.services;

import com.rsln.Schedule.models.Group;
import com.rsln.Schedule.repositories.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    public Group getById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }

    public Group create(Group group) {
        return groupRepository.save(group);
    }

    public Group update(Long id, Group updated) {
        Group group = getById(id);
        group.setName(updated.getName());
        group.setCourse(updated.getCourse());
        return groupRepository.save(group);
    }

    public void delete(Long id) {
        groupRepository.deleteById(id);
    }
}
