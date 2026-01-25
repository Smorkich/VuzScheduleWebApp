package com.rsln.Schedule.controllers.admin;

import com.rsln.Schedule.dtos.group.GroupRequestDto;
import com.rsln.Schedule.dtos.group.GroupResponseDto;
import com.rsln.Schedule.services.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/groups")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Groups", description = "Управление группами (ADMIN)")
public class AdminGroupController {

    private final GroupService groupService;

    public AdminGroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    @Operation(summary = "Создать новую группу")
    public ResponseEntity<GroupResponseDto> create(@RequestBody GroupRequestDto group) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.create(group));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные группы")
    public ResponseEntity<GroupResponseDto> update(@PathVariable Long id, @RequestBody GroupRequestDto updated) {
        return ResponseEntity.ok(groupService.update(id, updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить группу")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
