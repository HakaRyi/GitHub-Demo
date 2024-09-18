package com.example.identity_service.Mapper;

import com.example.identity_service.Entity.User;
import com.example.identity_service.dto.request.UpdateRequest;
import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UpdateRequest request);

    UserResponse toUserResponse(User user);
}
