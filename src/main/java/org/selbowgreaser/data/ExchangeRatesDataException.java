package org.selbowgreaser.data;

public class ExchangeRatesDataException extends RuntimeException {
    public ExchangeRatesDataException() {
    }

    ;

    public ExchangeRatesDataException(String message) {
        super(message);
    }

    public ExchangeRatesDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExchangeRatesDataException(Throwable cause) {
        super(cause);
    }
}
