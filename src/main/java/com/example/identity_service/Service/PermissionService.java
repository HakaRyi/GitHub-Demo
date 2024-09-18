package com.example.identity_service.Service;

import com.example.identity_service.Entity.Permission;
import com.example.identity_service.Mapper.PermissionMapper;
import com.example.identity_service.Repository.PermissionRepository;
import com.example.identity_service.dto.request.PermissionRequest;
import com.example.identity_service.dto.response.PermissionResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

        PermissionRepository permissionRepository;
        PermissionMapper permissionMapper;

        public PermissionResponse  create(PermissionRequest permissionRequest){
            Permission permission = permissionMapper.toPermission(permissionRequest);
            permissionRepository.save(permission);
            return permissionMapper.toPermissionResponse(permission);
        }

        public List<PermissionResponse> getAll(){
            return permissionRepository.findAll().stream().map(permissionMapper::toPermissionResponse).toList();
        }

        public void delete(String permissionName){
            permissionRepository.deleteById(permissionName);
        }
}
