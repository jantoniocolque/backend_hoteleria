package nido.backnido.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionGlobalHandler {

    private static final Logger logger = Logger.getLogger(ExceptionGlobalHandler.class);

    @ExceptionHandler(CustomBaseException.class)
    public ResponseEntity<CustomBaseException> validationErrors(CustomBaseException exception){
        logger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }

    @ExceptionHandler(CustomBindingException.class)
    public ResponseEntity<CustomBindingException> validationBindingErrors(CustomBindingException exception){
        logger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }
}
