package com.rsln.Schedule.controllers;

import com.rsln.Schedule.dtos.lesson.LessonRequestDto;
import com.rsln.Schedule.dtos.lesson.LessonResponseDto;
import com.rsln.Schedule.models.Lesson;
import com.rsln.Schedule.services.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
    @Operation(summary = "Получить все занятия")
    public ResponseEntity<List<LessonResponseDto>> getAll() {
        return ResponseEntity.ok(lessonService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.getById(id));
    }

    @GetMapping("/by-group/{groupId}")
    public ResponseEntity<List<LessonResponseDto>> getByGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(lessonService.getByGroupAndDay(groupId, null));
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<LessonResponseDto>> getSchedule(
            @RequestParam Long groupId,
            @RequestParam(required = false) DayOfWeek day,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date != null) {
            return ResponseEntity.ok(lessonService.getByGroupAndDate(groupId, date));
        }
        return ResponseEntity.ok(lessonService.getByGroupAndDay(groupId, day));
    }

    @GetMapping("/schedule/teacher")
    public ResponseEntity<List<LessonResponseDto>> getTeacherSchedule(
            @RequestParam Long teacherId,
            @RequestParam(required = false) DayOfWeek day) {
        return ResponseEntity.ok(lessonService.getScheduleForTeacher(teacherId, day));
    }
}
