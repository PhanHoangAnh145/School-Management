package vn.edu.ptit.PhanHoangAnh.student_management.helper;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handelNotFound(EntityNotFoundException ex) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handelNotFound(MethodArgumentTypeMismatchException ex) {
        String message = String.format("Tham so '%s' co gia tri '%s' khong dung dinh dang ", ex.getName(), ex.getValue());
        return ApiResponse.error(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        String errors = String.join("; ", errorList);

        ApiResponse<Object> response = new ApiResponse<>(HttpStatus.BAD_REQUEST, errors, null, "VALIDATION_ERROR");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handelBadCredentialsException(BadCredentialsException ex) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST, "sai mat khau");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public  ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
        return ApiResponse.error(HttpStatus.FORBIDDEN, "Khong co quyen");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
        return ApiResponse.error(HttpStatus.UNAUTHORIZED, "Chua xac thuc hoac sai Token");
    }
}
