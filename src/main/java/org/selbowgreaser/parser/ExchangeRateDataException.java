package org.selbowgreaser.parser;

public class ExchangeRateDataException extends RuntimeException {
    public ExchangeRateDataException() {};

    public ExchangeRateDataException(String message) {
        super(message);
    }

    public ExchangeRateDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExchangeRateDataException(Throwable cause) {
        super(cause);
    }
}
