package com.rsln.Schedule.dtos.teacher;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO для отображения преподавателя")
public record TeacherResponseDto(
        @Schema(description = "ID преподавателя", example = "1")
        Long id,

        @Schema(description = "Полное имя преподавателя", example = "Иванов Иван Иванович")
        String fullName,

        @Schema(description = "Кафедра преподавателя", example = "Математика")
        String department
) { }
