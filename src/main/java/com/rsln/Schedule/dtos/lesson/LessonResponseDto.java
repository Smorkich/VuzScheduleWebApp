package com.rsln.Schedule.dtos.lesson;

import com.rsln.Schedule.dtos.subject.SubjectResponseDto;
import com.rsln.Schedule.dtos.teacher.TeacherResponseDto;
import com.rsln.Schedule.dtos.classroom.ClassroomResponseDto;
import com.rsln.Schedule.dtos.group.GroupResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Schema(description = "DTO для отображения занятия")
public record LessonResponseDto(
        @Schema(description = "ID занятия", example = "1")
        Long id,

        @Schema(description = "Группа")
        GroupResponseDto group,

        @Schema(description = "Предмет")
        SubjectResponseDto subject,

        @Schema(description = "Преподаватель")
        TeacherResponseDto teacher,

        @Schema(description = "Аудитория")
        ClassroomResponseDto classroom,

        @Schema(description = "Тип занятия", example = "Лекция")
        String lessonType,

        @Schema(description = "День недели", example = "MONDAY")
        DayOfWeek dayOfWeek,

        @Schema(description = "Время начала", example = "09:00")
        LocalTime startTime,

        @Schema(description = "Время окончания", example = "10:30")
        LocalTime endTime
) { }
