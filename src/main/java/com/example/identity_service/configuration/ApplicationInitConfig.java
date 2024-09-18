package com.example.identity_service.configuration;

import com.example.identity_service.Entity.Permission;
import com.example.identity_service.Entity.Role;
import com.example.identity_service.Entity.User;
import com.example.identity_service.Repository.PermissionRepository;
import com.example.identity_service.Repository.RoleRepository;
import com.example.identity_service.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class ApplicationInitConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(userRepository.findByName("ADMIN").isEmpty()){

                Role role_Repos = roleRepository.findById("ADMIN").orElseGet(
                        () -> {
                            Role role = new Role();
                            role.setName("ADMIN");
                            role.setDescription("admin account");
                            role.setPermissions(new HashSet<>(permissionRepository.findAll()));
                            return roleRepository.save(role);
                        }
                );
                Set<Role> roles = new HashSet<Role>();
                roles.add(role_Repos);
                User user = User.builder()
                        .name("ADMIN")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("Admin user have been created with default password: admin, Please change it!!");
            }
        };
    }
}
