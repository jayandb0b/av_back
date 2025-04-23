package com.sw.av.application.dto;

import lombok.Data;

@Data
public class ErrorResponseDto {
    private String message;
    private String internalErrorMessage;
    private Object data;
}