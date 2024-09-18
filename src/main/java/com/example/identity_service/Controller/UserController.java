package com.example.identity_service.Controller;

import com.example.identity_service.Entity.User;
import com.example.identity_service.Exception.AppException;
import com.example.identity_service.Exception.ErrorCode;
import com.example.identity_service.Repository.UserRepository;
import com.example.identity_service.Service.UserService;
import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UpdateRequest;
import com.example.identity_service.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse <UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
//        apiResponse.setResult(userService.createRequest(request));
        return ApiResponse.<UserResponse>builder().
                result(userService.createRequest(request))
                .build();
    }

    @GetMapping
    List<User> getUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return userService.getUsers();
    }

    @GetMapping("/{id}")
    ApiResponse<UserResponse> getUser(@PathVariable String id) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUser(id));
        return apiResponse;
    }

    @PutMapping("/{id}")
         User updateUser(@PathVariable String id, @RequestBody UpdateRequest request) {
            return userService.updateUser(id, request);
    }

    @DeleteMapping("{id}")
    String deleteUser(@PathVariable String id) {
        userService.deleteById(id);
        return "User have been deleted";
    }

    @GetMapping("/myinfo")
    ApiResponse<UserResponse> getMyInformation(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }
}

