package com.rsln.Schedule.controllers.admin;

import com.rsln.Schedule.dtos.classroom.ClassroomRequestDto;
import com.rsln.Schedule.dtos.classroom.ClassroomResponseDto;
import com.rsln.Schedule.services.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/classrooms")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Classrooms", description = "Управление аудиториями (ADMIN)")
public class AdminClassroomController {

    private final ClassroomService service;

    public AdminClassroomController(ClassroomService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Создать аудиторию")
    public ResponseEntity<ClassroomResponseDto> create(@Valid @RequestBody ClassroomRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить аудиторию")
    public ResponseEntity<ClassroomResponseDto> update(@PathVariable Long id, @Valid @RequestBody ClassroomRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить аудиторию")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}