package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Error response object")
public class ErrorResponse extends RepresentationModel<ErrorResponse> {

    @Schema(description = "Timestamp when error occurred", example = "2024-01-15T10:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "Error type", example = "Not Found")
    private String error;

    @Schema(description = "Error message", example = "User not found")
    private String message;

    @Schema(description = "Request path", example = "/api/v1/users/999")
    private String path;
}
