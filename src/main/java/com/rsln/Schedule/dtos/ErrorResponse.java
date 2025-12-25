package com.rsln.Schedule.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Ответ при ошибке")
public record ErrorResponse(
        @Schema(description = "Время ошибки")
        LocalDateTime timestamp,

        @Schema(description = "HTTP статус", example = "404")
        int status,

        @Schema(description = "Сообщение об ошибке")
        String message
) { }
