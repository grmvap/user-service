package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@Relation(collectionRelation = "users", itemRelation = "user")
@Schema(description = "Response object containing user details")
public class UserResponseDto extends RepresentationModel<UserResponseDto> {

    @Schema(
            description = "Unique identifier of the user",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "Unique username",
            example = "john_doe",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private String username;

    @Schema(
            description = "User email address",
            example = "john.doe@example.com",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private String email;
    private String phone;

    @Schema(
            description = "Timestamp when user was created",
            example = "2024-01-15T10:30:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Timestamp when user was last updated",
            example = "2024-01-15T10:30:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime updatedAt;
}
