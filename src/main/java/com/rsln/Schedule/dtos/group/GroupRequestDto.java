package com.rsln.Schedule.dtos.group;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO для создания/обновления группы")
public record GroupRequestDto(
        @NotBlank(message = "Название группы не может быть пустым")
        @Schema(description = "Название группы", example = "ИВ1")
        String name,
        @NotBlank(message = "курс не может быть пустым")
        @Schema(description = "Курс", example = "1")
        int course
) { }
