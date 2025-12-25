package com.rsln.Schedule.controllers;

import com.rsln.Schedule.dtos.lesson.LessonRequestDto;
import com.rsln.Schedule.dtos.lesson.LessonResponseDto;
import com.rsln.Schedule.models.Lesson;
import com.rsln.Schedule.services.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@Tag(name = "Lessons", description = "Управление занятиями и расписанием")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    @Operation(summary = "Получить список всех занятий")
    public List<LessonResponseDto> getAll() {
        return lessonService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить занятие по ID")
    public LessonResponseDto getById(
            @Parameter(description = "ID занятия") @PathVariable Long id
    ) {
        return lessonService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Создать занятие")
    public LessonResponseDto create(
            @Parameter(description = "Данные занятия")
            @Valid @RequestBody LessonRequestDto dto
    ) {
        return lessonService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить занятие")
    public LessonResponseDto update(
            @Parameter(description = "ID занятия") @PathVariable Long id,
            @Parameter(description = "Обновлённые данные")
            @Valid @RequestBody LessonRequestDto dto
    ) {
        return lessonService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить занятие")
    public void delete(
            @Parameter(description = "ID занятия") @PathVariable Long id
    ) {
        lessonService.delete(id);
    }

    @GetMapping("/by-group/{groupId}")
    @Operation(summary = "Получить все занятия группы (без фильтра по дню)")
    public List<LessonResponseDto> getByGroup(
            @Parameter(description = "ID группы") @PathVariable Long groupId
    ) {
        return lessonService.getByGroupAndDay(groupId, null);
    }

    @GetMapping("/schedule")
    @Operation(summary = "Получить расписание группы на определённый день")
    public List<LessonResponseDto> getSchedule(
            @Parameter(description = "ID группы") @RequestParam Long groupId,
            @Parameter(description = "День недели (MONDAY, TUESDAY, ...)")
            @RequestParam String day
    ) {
        return lessonService.getByGroupAndDay(
                groupId,
                DayOfWeek.valueOf(day.toUpperCase())
        );
    }
}
