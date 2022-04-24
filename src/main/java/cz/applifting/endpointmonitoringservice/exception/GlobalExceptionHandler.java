package cz.applifting.endpointmonitoringservice.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MonitoredEndpointNotFoundException.class)
    public ResponseEntity<?> monitoredEndpointNotFound(MonitoredEndpointNotFoundException ex) {

        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.name())
                .message(ex.getMessage()).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

 @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ExceptionResponse> handleConstraintViolation(
            ConstraintViolationException ex,WebRequest request)
    {
        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Constraint Violations")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now()).build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {

        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Argument Type Mismatch")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now()).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> argumentNotValid(MethodArgumentNotValidException ex) {

        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Argument Type value is wrong")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now()).build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
