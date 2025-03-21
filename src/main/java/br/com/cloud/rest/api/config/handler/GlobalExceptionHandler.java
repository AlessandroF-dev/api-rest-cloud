package br.com.cloud.rest.api.config.handler;

import br.com.cloud.rest.api.domain.exception.DuplicateDataException;
import br.com.cloud.rest.api.domain.exception.GenericErrorException;
import br.com.cloud.rest.api.domain.exception.ResultDataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger  = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<String> handleBusinessException(DuplicateDataException businessException) {
        return new ResponseEntity<>(businessException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ResultDataNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(ResultDataNotFoundException notFoundException) {
        return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenericErrorException.class)
    public ResponseEntity<String> handleUnexpectedException(GenericErrorException unexpectedException) {
        var message = "Unexpected server error, see the logs.";
        logger.error(message, unexpectedException);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
