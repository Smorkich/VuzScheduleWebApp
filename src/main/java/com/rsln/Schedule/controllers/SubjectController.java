package com.rsln.Schedule.controllers;

import com.rsln.Schedule.dtos.subject.SubjectRequestDto;
import com.rsln.Schedule.dtos.subject.SubjectResponseDto;
import com.rsln.Schedule.models.Subject;
import com.rsln.Schedule.services.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@Tag(name = "Subjects", description = "Управление учебными предметами")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    @Operation(summary = "Получить список предметов")
    public List<SubjectResponseDto> getAll() {
        return subjectService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить предмет по ID")
    public SubjectResponseDto getById(
            @Parameter(description = "ID предмета") @PathVariable Long id
    ) {
        return subjectService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать новый предмет")
    public SubjectResponseDto create(
            @Parameter(description = "Данные предмета")
            @Valid @RequestBody SubjectRequestDto dto
    ) {
        return subjectService.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить предмет")
    public SubjectResponseDto update(
            @Parameter(description = "ID предмета") @PathVariable Long id,
            @Parameter(description = "Обновлённые данные")
            @Valid @RequestBody SubjectRequestDto dto
    ) {
        return subjectService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить предмет")
    public void delete(
            @Parameter(description = "ID предмета") @PathVariable Long id
    ) {
        subjectService.delete(id);
    }
}
