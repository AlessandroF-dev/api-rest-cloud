package br.com.cloud.rest.api.domain.exception;

public class DuplicateDataException extends RuntimeException {

    public DuplicateDataException(String message) {
        super(message);
    }
}
