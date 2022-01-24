package com.callsign.ticketly.configuration;

import com.callsign.ticketly.dto.ExceptionResponseDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class TicketlyExceptionHandler {

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    ResponseEntity<ExceptionResponseDTO> handleBaseException(Exception e)
    {
        e.printStackTrace();
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO();
        exceptionResponseDTO.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        exceptionResponseDTO.setMessage(e.getMessage());
        return new ResponseEntity<>(exceptionResponseDTO, exceptionResponseDTO.getHttpStatus());
    }

    @ExceptionHandler({DataIntegrityViolationException.class, SQLIntegrityConstraintViolationException.class})
    public @ResponseBody ResponseEntity<ExceptionResponseDTO> productIdNotFoundException(Exception e)
    {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO();
        exceptionResponseDTO.setHttpStatus(HttpStatus.NOT_FOUND);
        exceptionResponseDTO.setMessage(e.getMessage());
        return new ResponseEntity<>(exceptionResponseDTO, exceptionResponseDTO.getHttpStatus());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public @ResponseBody ResponseEntity<ExceptionResponseDTO> invalidInputArgument(Exception e)
    {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO();
        exceptionResponseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
        exceptionResponseDTO.setMessage(e.getMessage());
        return new ResponseEntity<>(exceptionResponseDTO, exceptionResponseDTO.getHttpStatus());
    }

}
