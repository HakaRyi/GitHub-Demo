package com.example.identity_service.Mapper;

import com.example.identity_service.Entity.Permission;
import com.example.identity_service.Entity.User;
import com.example.identity_service.dto.request.PermissionRequest;
import com.example.identity_service.dto.request.UpdateRequest;
import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.response.PermissionResponse;
import com.example.identity_service.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
