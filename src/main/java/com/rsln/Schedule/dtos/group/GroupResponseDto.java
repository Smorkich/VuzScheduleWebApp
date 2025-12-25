package com.rsln.Schedule.dtos.group;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO для отображения группы")
public record GroupResponseDto(
        @Schema(description = "ID группы", example = "1")
        Long id,
        @Schema(description = "Название группы", example = "ИВ1")
        String name,
        @Schema(description = "Курс", example = "1")
        int course
) { }

