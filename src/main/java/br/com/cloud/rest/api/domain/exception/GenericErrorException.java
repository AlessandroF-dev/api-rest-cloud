package br.com.cloud.rest.api.domain.exception;

public class GenericErrorException extends RuntimeException {

    public GenericErrorException(String message) {
        super(message);
    }
}
