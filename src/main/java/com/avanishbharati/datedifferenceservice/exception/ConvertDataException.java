package com.avanishbharati.datedifferenceservice.exception;

public class ConvertDataException extends RuntimeException {

    public ConvertDataException(String message) {
        super(message);
    }

    public ConvertDataException(Exception ex) {
        super(ex);
    }
}
