package com.callsign.ticketly.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class BaseResponseDto<T> {
    private HttpStatus httpStatus;
    private T data;
    private String message;

    public static <T>ResponseEntity<BaseResponseDto<T>> created(String message, T data) {
        BaseResponseDto<T> baseResponseDto = new BaseResponseDto<>();
        baseResponseDto.httpStatus = HttpStatus.CREATED;
        baseResponseDto.message = message;
        baseResponseDto.data = data;
        return new ResponseEntity<>(baseResponseDto, baseResponseDto.httpStatus);
    }
}
