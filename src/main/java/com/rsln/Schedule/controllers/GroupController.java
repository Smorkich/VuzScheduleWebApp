package com.rsln.Schedule.controllers;

import com.rsln.Schedule.models.Group;
import com.rsln.Schedule.services.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<Group> getAll() {
        return groupService.getAll();
    }

    @GetMapping("/{id}")
    public Group getById(@PathVariable Long id) {
        return groupService.getById(id);
    }

    @PostMapping
    public Group create(@RequestBody Group group) {
        return groupService.create(group);
    }

    @PutMapping("/{id}")
    public Group update(@PathVariable Long id, @RequestBody Group updated) {
        return groupService.update(id, updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        groupService.delete(id);
    }
}

