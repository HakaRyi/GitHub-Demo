package com.example.identity_service.dto.response;

import com.example.identity_service.dto.request.AuthenticationRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthencationResponse {
    String token;
    boolean authenticated;
}
