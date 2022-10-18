package Limor.Almog.restApplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
use exception handling globally
 */
@ControllerAdvice
public class CustomerAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException.class)
    String costumerNotFoundHandler(CustomerNotFoundException customerNotFoundException) {
        return customerNotFoundException.getMessage();
    }
}
