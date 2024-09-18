package com.example.identity_service.CustomHandler;

import com.example.identity_service.Exception.ErrorCode;
import com.example.identity_service.dto.request.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.AUTHENTICATIONFAILURE;

        ApiResponse apiResponse = new ApiResponse<>(errorCode.getCode(), errorCode.getMessage(), errorCode.getHttpStatusCode());

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        response
                .getOutputStream()
                .println(
                        objectMapper.writeValueAsString(apiResponse)
                );
    }
}
