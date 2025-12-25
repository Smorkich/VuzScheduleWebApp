package com.rsln.Schedule.dtos.subject;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO для отображения предмета")
public record SubjectResponseDto(
        @Schema(description = "ID предмета", example = "1")
        Long id,
        @Schema(description = "Название предмета", example = "Математика")
        String name
) { }
