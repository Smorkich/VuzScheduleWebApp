package com.rsln.Schedule.dtos.subject;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO для создания/обновления предмета")
public record SubjectRequestDto(
        @NotBlank(message = "Название предмета не может быть пустым")
        @Schema(description = "Название предмета", example = "Математика")
        String name
) { }
