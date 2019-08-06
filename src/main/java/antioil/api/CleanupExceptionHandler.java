package antioil.api;

import antioil.dto.CleanupError;
import antioil.service.NavigationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CleanupExceptionHandler {

    @ExceptionHandler(NavigationException.class)
    protected ResponseEntity<Object> handleNavigationException(NavigationException ex) {
        return new ResponseEntity<>(new CleanupError(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new CleanupError(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
