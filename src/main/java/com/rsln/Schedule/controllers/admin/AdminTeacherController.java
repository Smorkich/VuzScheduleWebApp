package com.rsln.Schedule.controllers.admin;

import com.rsln.Schedule.dtos.teacher.TeacherRequestDto;
import com.rsln.Schedule.dtos.teacher.TeacherResponseDto;
import com.rsln.Schedule.services.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/teachers")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Teachers", description = "Управление преподавателями (ADMIN)")
public class AdminTeacherController {

    private final TeacherService teacherService;

    public AdminTeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    @Operation(summary = "Добавить преподавателя")
    public ResponseEntity<TeacherResponseDto> create(@Valid @RequestBody TeacherRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные преподавателя")
    public ResponseEntity<TeacherResponseDto> update(@PathVariable Long id, @Valid @RequestBody TeacherRequestDto dto) {
        return ResponseEntity.ok(teacherService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить преподавателя")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}