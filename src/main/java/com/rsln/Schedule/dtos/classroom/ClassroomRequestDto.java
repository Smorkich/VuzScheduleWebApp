package com.rsln.Schedule.dtos.classroom;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO для создания/обновления аудитории")
public record ClassroomRequestDto(
        @NotBlank(message = "Название аудитории не может быть пустым")
        @Schema(description = "Название аудитории", example = "101А")
        String name
) { }

