package com.rsln.Schedule.dtos.classroom;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO для отображения аудитории")
public record ClassroomResponseDto(
        @Schema(description = "ID аудитории", example = "1")
        Long id,
        @Schema(description = "Название аудитории", example = "101А")
        String name
) { }
