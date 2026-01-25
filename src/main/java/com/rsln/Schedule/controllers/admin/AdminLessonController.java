package com.rsln.Schedule.controllers.admin;

import com.rsln.Schedule.dtos.lesson.LessonRequestDto;
import com.rsln.Schedule.dtos.lesson.LessonResponseDto;
import com.rsln.Schedule.services.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/lessons")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Lessons", description = "Управление расписанием (только для ADMIN)")
public class AdminLessonController {
    private final LessonService lessonService;

    public AdminLessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping
    @Operation(summary = "Создать занятие")
    public ResponseEntity<LessonResponseDto> create(@Valid @RequestBody LessonRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить занятие")
    public ResponseEntity<LessonResponseDto> update(@PathVariable Long id, @Valid @RequestBody LessonRequestDto dto) {
        return ResponseEntity.ok(lessonService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить занятие")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lessonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
