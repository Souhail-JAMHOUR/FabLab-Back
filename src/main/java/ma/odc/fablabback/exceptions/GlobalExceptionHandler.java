package ma.odc.fablabback.exceptions;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorObject> handleUnsatisfiedRequirement(
      Exception ex, WebRequest request) {

    ErrorObject errorObject = ErrorObject.builder()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message(ex.getMessage())
            .timestamp(new Date())
            .build();
    return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
