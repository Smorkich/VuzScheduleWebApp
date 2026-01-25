package com.rsln.Schedule.controllers;

import com.rsln.Schedule.dtos.group.GroupRequestDto;
import com.rsln.Schedule.dtos.group.GroupResponseDto;
import com.rsln.Schedule.services.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@Tag(name = "Groups", description = "Управление учебными группами")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    @Operation(summary = "Получить список всех групп")
    public ResponseEntity<List<GroupResponseDto>> getAll() {
        return ResponseEntity.ok(groupService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить данные группы по ID")
    public ResponseEntity<GroupResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getById(id));
    }
}

