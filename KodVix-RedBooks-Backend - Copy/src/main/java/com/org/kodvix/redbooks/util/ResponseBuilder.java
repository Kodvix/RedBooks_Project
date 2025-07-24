package com.org.kodvix.redbooks.util;

import com.org.kodvix.redbooks.dto.ApiResponse;

public class ResponseBuilder {

    private ResponseBuilder() {
        // Utility class, no instance
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .message("Request processed successfully.")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> withMessage(String message) {
        return ApiResponse.<T>builder()
                .message(message)
                .build();
    }
}
