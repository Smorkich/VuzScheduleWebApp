package com.rsln.Schedule.controllers;

import com.rsln.Schedule.dtos.classroom.ClassroomRequestDto;
import com.rsln.Schedule.dtos.classroom.ClassroomResponseDto;
import com.rsln.Schedule.services.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
@Tag(name = "Classrooms", description = "Управление аудиториями")
public class ClassroomController {

    private final ClassroomService service;

    public ClassroomController(ClassroomService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Получить все аудитории")
    public List<ClassroomResponseDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить аудиторию по ID")
    public ClassroomResponseDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать аудиторию")
    @ResponseStatus(HttpStatus.CREATED)
    public ClassroomResponseDto create(@Valid @RequestBody ClassroomRequestDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить аудиторию")
    public ClassroomResponseDto update(@PathVariable Long id, @Valid @RequestBody ClassroomRequestDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить аудиторию")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
