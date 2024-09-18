package com.example.identity_service.CustomHandler;

import com.example.identity_service.Exception.ErrorCode;
import com.example.identity_service.dto.request.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        // Ghi log lỗi
        ApiResponse apiResponse = new ApiResponse<>(errorCode.getCode(), errorCode.getMessage(), errorCode.getHttpStatusCode());

        // Trả về mã lỗi 403 và thông báo tùy chỉnh
        response.setStatus(errorCode.getHttpStatusCode().value());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        response
                .getOutputStream()
                .println(
                        objectMapper.writeValueAsString(apiResponse)
                );
    }
}
