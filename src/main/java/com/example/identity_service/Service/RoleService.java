package com.example.identity_service.Service;

import com.example.identity_service.Entity.Permission;
import com.example.identity_service.Entity.Role;
import com.example.identity_service.Mapper.PermissionMapper;
import com.example.identity_service.Mapper.RoleMapper;
import com.example.identity_service.Repository.PermissionRepository;
import com.example.identity_service.Repository.RoleRepository;
import com.example.identity_service.dto.request.PermissionRequest;
import com.example.identity_service.dto.request.RoleRequest;
import com.example.identity_service.dto.response.PermissionResponse;
import com.example.identity_service.dto.response.RoleResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
        PermissionRepository permissionRepository;
        RoleRepository roleRepository;
        RoleMapper roleMapper;

        public RoleResponse create(RoleRequest roleRequest){
            Role role = roleMapper.toRole(roleRequest);
            var permissions = permissionRepository.findAllById(roleRequest.getPermissions());
            role.setPermissions(new HashSet<>(permissions));

            roleRepository.save(role);
            return roleMapper.toRoleResponse(role);
        }

        public List<RoleResponse> getAll(){
            return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
        }

        public void delete(String permissionName){
            permissionRepository.deleteById(permissionName);
        }
}
