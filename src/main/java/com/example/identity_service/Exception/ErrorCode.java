package com.example.identity_service.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED(9999, "UNCATGORIZED Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1000, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1001,"User Existed", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1002, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "UserName must be at least 3 characters", HttpStatus.BAD_REQUEST),
    ID_NOT_FOUND(1004, "User is not existed or ID is not available", HttpStatus.NOT_FOUND),
    USER_NOT_EXIST(1005, "User is not Exist",HttpStatus.NOT_FOUND),
    NOT_AUTHENTICATED(1006, "Not Authenticated", HttpStatus.UNAUTHORIZED),
    CANNOT_CREATE_TOKEN(1007, "Cannot create token", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1008, "Do not have permission", HttpStatus.FORBIDDEN),
    AUTHENTICATIONFAILURE(1009, "Fail to access", HttpStatus.UNAUTHORIZED);

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }


}
