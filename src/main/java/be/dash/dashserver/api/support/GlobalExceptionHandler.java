package be.dash.dashserver.api.support;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import be.dash.dashserver.api.exception.DashApiException;
import be.dash.dashserver.api.exception.ErrorMessage;
import be.dash.dashserver.core.auth.UnAuthorizedException;
import be.dash.dashserver.core.exception.ForbiddenException;
import be.dash.dashserver.core.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.info("handleHttpMessageNotReadableException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("handleMethodArgumentNotValidException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.info("handleMethodArgumentTypeMismatchException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorMessage> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.info("handleHttpRequestMethodNotSupportedException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorMessage> handleNoResourceFoundException(NoResourceFoundException e) {
        log.info("handleNoResourceFoundException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorMessage> handleUnAuthorizedException(UnAuthorizedException e) {
        log.info("handleUnAuthorizedException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorMessage> handleForbiddenException(ForbiddenException e) {
        log.info("handleForbiddenException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException e) {
        log.info("handleNotFoundException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(DashApiException.class)
    public ResponseEntity<ErrorMessage> handleDashApiException(DashApiException e) {
        log.info("handleDashApiException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        log.error("handleException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.internalServerError().body(new ErrorMessage(e.getMessage()));
    }
}
