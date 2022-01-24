package com.callsign.ticketly.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

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

    public static <T> ResponseEntity<BaseResponseDto<T>> success(String message, Optional<T> data){
        BaseResponseDto<T> baseResponseDTO = new BaseResponseDto<>();
        baseResponseDTO.httpStatus = HttpStatus.OK;
        data.ifPresent(t -> baseResponseDTO.data = t);
        baseResponseDTO.message = message;
        return new ResponseEntity<>(baseResponseDTO, baseResponseDTO.httpStatus);
    }
}
