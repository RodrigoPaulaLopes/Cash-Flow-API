package com.rodrigo.cashflowapi.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ControllerExceptions {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity argumentNotValid(MethodArgumentNotValidException ex){
        var fields = ex.getFieldErrors();

        return ResponseEntity.badRequest().body(fields.stream().map(Validacao::new).toList());

    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity business(BusinessException ex){
        return ResponseEntity.badRequest().body(new BusinesDTO(ex.getMessage()));
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity forbidden(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ForbiddenDTO("Acesso negado!"));
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity tokenValidation(JWTVerificationException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ForbiddenDTO(ex.getMessage()));
    }

    public record BusinesDTO(String message){

    }
    public record Validacao(String campo, String mensagem){
        public Validacao(FieldError campos){
            this(campos.getField(), campos.getDefaultMessage());
        }
    }



    public record ForbiddenDTO(String message){

    }
}
