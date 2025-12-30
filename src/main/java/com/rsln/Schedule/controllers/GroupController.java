package com.rsln.Schedule.controllers;

import com.rsln.Schedule.dtos.group.GroupRequestDto;
import com.rsln.Schedule.dtos.group.GroupResponseDto;
import com.rsln.Schedule.services.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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
    public List<GroupResponseDto> getAll() {
        return groupService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить данные группы по ID")
    public GroupResponseDto getById(
            @Parameter(description = "ID группы") @PathVariable Long id
    ) {
        return groupService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать новую группу")
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResponseDto create(
            @Parameter(description = "Данные новой группы") @RequestBody GroupRequestDto group
    ) {
        return groupService.create(group);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить данные группы")
    public GroupResponseDto update(
            @Parameter(description = "ID группы") @PathVariable Long id,
            @Parameter(description = "Обновлённые данные") @RequestBody GroupRequestDto updated
    ) {
        return groupService.update(id, updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить группу")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "ID группы") @PathVariable Long id
    ) {
        groupService.delete(id);
    }
}

