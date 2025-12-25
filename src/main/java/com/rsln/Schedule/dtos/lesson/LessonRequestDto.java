package com.rsln.Schedule.dtos.lesson;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Schema(description = "DTO для создания/обновления занятия")
public record LessonRequestDto(
        @NotNull(message = "ID группы обязателен")
        @Schema(description = "ID группы", example = "1")
        Long groupId,

        @NotNull(message = "ID предмета обязателен")
        @Schema(description = "ID предмета", example = "1")
        Long subjectId,

        @NotNull(message = "ID преподавателя обязателен")
        @Schema(description = "ID преподавателя", example = "1")
        Long teacherId,

        @NotNull(message = "ID аудитории обязателен")
        @Schema(description = "ID аудитории", example = "1")
        Long classroomId,

        @Schema(description = "Тип занятия", example = "Лекция")
        String lessonType,

        @NotNull(message = "День недели обязателен")
        @Schema(description = "День недели", example = "MONDAY")
        DayOfWeek dayOfWeek,

        @NotNull(message = "Время начала обязателено")
        @Schema(description = "Время начала", example = "09:00")
        LocalTime startTime,

        @NotNull(message = "Время окончания обязателено")
        @Schema(description = "Время окончания", example = "10:30")
        LocalTime endTime
) { }