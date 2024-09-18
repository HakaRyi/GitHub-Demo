package com.example.identity_service.Service;

import com.example.identity_service.Entity.User;
import com.example.identity_service.Enums.Role;
import com.example.identity_service.Exception.AppException;
import com.example.identity_service.Exception.ErrorCode;
import com.example.identity_service.Mapper.RoleMapper;
import com.example.identity_service.Mapper.UserMapper;
import com.example.identity_service.Repository.RoleRepository;
import com.example.identity_service.Repository.UserRepository;
import com.example.identity_service.dto.request.UpdateRequest;
import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.dto.response.UserResponse;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMapper roleMapper;

    public UserResponse createRequest(UserCreationRequest request){
        User user = new User();

        if(userRepository.existsByName(request.getName())) throw new AppException(ErrorCode.USER_EXISTED);
        user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

//        user.setRole(roles);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole(admin)")
    public List<User> getUsers(){
        List<User> userList = null;
        userList = userRepository.findAll();
        return userList;
    }

    public UserResponse getUser(String id){
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND)));
    }

    public User updateUser(String userId, UpdateRequest request){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));

        userMapper.updateUser(user,request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userRepository.save(user);
    }

    public void deleteById(String id){
        userRepository.deleteById(id);
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        var userName = context.getAuthentication().getName();
        User user = userRepository.findByName(userName).
                orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        UserResponse result = userMapper.toUserResponse(user);
        result.setRoles(
                user.getRoles().stream()
                        .map(roleMapper::toRoleResponse)  // Chuyển đổi mỗi Role sang RoleResponse
                        .collect(Collectors.toSet())      // Thu thập kết quả thành Set
        );

        return result;
    }
}
