package com.example.identity_service.dto.response;

import com.example.identity_service.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    String id;
    String name;
    String firstName;
    String lastName;
    LocalDate dob;

    Set<RoleResponse> roles;
}
