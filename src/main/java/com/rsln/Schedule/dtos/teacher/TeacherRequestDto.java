package com.rsln.Schedule.dtos.teacher;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO для создания/обновления преподавателя")
public record TeacherRequestDto(
        @NotBlank(message = "ФИО преподавателя не может быть пустым")
        @Schema(description = "Полное имя преподавателя", example = "Иванов Иван Иванович")
        String fullName,

        @Schema(description = "Кафедра преподавателя", example = "Математика")
        String department
) { }