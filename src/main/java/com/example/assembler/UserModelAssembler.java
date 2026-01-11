package com.example.assembler;

import com.example.controller.UserController;
import com.example.dto.UserResponseDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserResponseDto, EntityModel<UserResponseDto>> {

    @Override
    public EntityModel<UserResponseDto> toModel(UserResponseDto user) {
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel()
                        .andAffordance(afford(methodOn(UserController.class).updateUser(user.getId(), null)))
                        .andAffordance(afford(methodOn(UserController.class).deleteUser(user.getId()))),

                linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"),

                linkTo(methodOn(UserController.class).updateUser(user.getId(), null)).withRel("update"),

                linkTo(methodOn(UserController.class).deleteUser(user.getId())).withRel("delete"),

                linkTo(methodOn(UserController.class).getUserById(user.getId())).slash("events").withRel("events"),

                linkTo(UserController.class).withRel("root")
        );
    }

    @Override
    public CollectionModel<EntityModel<UserResponseDto>> toCollectionModel(Iterable<? extends UserResponseDto> entities) {
        CollectionModel<EntityModel<UserResponseDto>> collectionModel = RepresentationModelAssembler.super.toCollectionModel(entities);

        collectionModel.add(
                linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel(),
                linkTo(methodOn(UserController.class).createUser(null)).withRel("create-user"),
                linkTo(UserController.class).withRel("root")
        );

        return collectionModel;
    }
}
