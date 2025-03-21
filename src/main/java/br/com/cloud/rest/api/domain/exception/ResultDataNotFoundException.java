package br.com.cloud.rest.api.domain.exception;

public class ResultDataNotFoundException extends RuntimeException {

    public ResultDataNotFoundException(String message) {
        super(message);
    }
}
