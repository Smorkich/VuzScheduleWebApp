package com.rsln.Schedule.controllers;

import com.rsln.Schedule.dtos.teacher.TeacherRequestDto;
import com.rsln.Schedule.dtos.teacher.TeacherResponseDto;
import com.rsln.Schedule.models.Teacher;
import com.rsln.Schedule.services.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@Tag(name = "Teachers", description = "Управление преподавателями")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    @Operation(summary = "Получить список преподавателей")
    public List<TeacherResponseDto> getAll() {
        return teacherService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить преподавателя по ID")
    public TeacherResponseDto getById(
            @Parameter(description = "ID преподавателя") @PathVariable Long id
    ) {
        return teacherService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Добавить преподавателя")
    public TeacherResponseDto create(
            @Parameter(description = "Данные преподавателя")
            @Valid @RequestBody TeacherRequestDto dto
    ) {
        return teacherService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные преподавателя")
    public TeacherResponseDto update(
            @Parameter(description = "ID преподавателя") @PathVariable Long id,
            @Parameter(description = "Обновлённые данные")
            @Valid @RequestBody TeacherRequestDto dto
    ) {
        return teacherService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить преподавателя")
    public void delete(
            @Parameter(description = "ID преподавателя") @PathVariable Long id
    ) {
        teacherService.delete(id);
    }
}

