package com.AcortaLink.acortaLink.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class RestExceptionHandler {

    record ValidationError(String field, String messege){}
    record ValidationErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            List<ValidationError> errors,
            String path){}

    //esto devuelve un 400 con detalles en el campo
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(MethodArgumentNotValidException ex, WebRequest request){
        List<ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> new ValidationError(fe.getField(),fe.getDefaultMessage()))
                .toList();


        ValidationErrorResponse body = new ValidationErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validacion Fallida",
                errors,
                ((ServletWebRequest)request).getRequest().getRequestURI()
        );

        return  ResponseEntity.badRequest().body(body);

    }

    //devuelve un 404 con mensaje claro
    @ExceptionHandler(CodeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handelNotFound(
            CodeNotFoundException ex,
            WebRequest request
    ){
        // crea  una coleccion clave valor, el linkedHashMap se encarga de que se mantenga ese orden.
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error","Recurso no encontrado");
        body.put("messege", ex.getMessage());
        body.put("path", ((ServletWebRequest)request).getRequest().getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // guarda un fallback para errores no previsto  devuelve un json con los errores no previsto.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(
            Exception ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error","Error interno");
        body.put("messege", ex.getMessage());
        body.put("path", ((ServletWebRequest)request).getRequest().getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
