package be.dash.dashserver.api.support;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import be.dash.dashserver.api.exception.DashApiException;
import be.dash.dashserver.api.exception.ErrorMessage;
import be.dash.dashserver.core.auth.UnAuthorizedException;
import be.dash.dashserver.core.exception.BadRequestException;
import be.dash.dashserver.core.exception.ConflictException;
import be.dash.dashserver.core.exception.ForbiddenException;
import be.dash.dashserver.core.exception.NotFoundException;
import be.dash.dashserver.core.exception.PaymentClientException;
import be.dash.dashserver.core.log.LogForm;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("handleHttpMessageNotReadableException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("handleMethodArgumentNotValidException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.warn("handleMethodArgumentTypeMismatchException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorMessage> handleMethodMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.warn("handleMissingServletRequestParameterException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorMessage> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn("handleHttpRequestMethodNotSupportedException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(BadRequestException e) {
        log.warn("handleBadRequestException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorMessage> handleNoResourceFoundException(NoResourceFoundException e) {
        log.warn("handleNoResourceFoundException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorMessage> handleUnAuthorizedException(UnAuthorizedException e) {
        log.warn("handleUnAuthorizedException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorMessage> handleForbiddenException(ForbiddenException e) {
        log.warn("handleForbiddenException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException e) {
        log.warn("handleNotFoundException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorMessage> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.warn("handleMaxUploadSizeExceededException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(S3Exception.class)
    public ResponseEntity<ErrorMessage> handleS3Exception(S3Exception e) {
        log.warn("handleS3Exception in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorMessage> handleForbiddenException(ConflictException e) {
        log.warn("handleForbiddenException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(PaymentClientException.class)
    public ResponseEntity<ErrorMessage> handlePaymentClientException(PaymentClientException e) {
        log.warn("handlePaymentClientException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(DashApiException.class)
    public ResponseEntity<ErrorMessage> handleDashApiException(DashApiException e) {
        log.warn("handleDashApiException in GlobalExceptionHandler throw {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        log.error(LogForm.ERROR_LOGGING_FORM, e.getClass(), e.getMessage(), e.getStackTrace());
        return ResponseEntity.internalServerError().body(new ErrorMessage(e.getMessage()));
    }
}
