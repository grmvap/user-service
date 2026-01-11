package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request object for creating/updating a user")
public class UserRequestDto {
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(
            description = "Unique username",
            example = "john_doe",
            minLength = 3,
            maxLength = 50,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Schema(
            description = "User email address",
            example = "john.doe@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @Pattern(regexp = "\\+7\\d{10}",
            message = "Phone number should be in format +7XXXXXXXXXX")
    private String phone;
}