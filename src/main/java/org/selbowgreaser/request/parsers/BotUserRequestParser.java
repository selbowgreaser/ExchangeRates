package org.selbowgreaser.request.parsers;

import org.selbowgreaser.handler.DateHandler;
import org.selbowgreaser.request.BotUserRequest;
import org.selbowgreaser.request.UserRequest;
import org.selbowgreaser.request.exceptions.BotUserRequestException;
import org.selbowgreaser.request.parameters.Algorithm;
import org.selbowgreaser.request.parameters.Currency;
import org.selbowgreaser.request.parameters.OutputMode;
import org.selbowgreaser.request.parameters.Period;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BotUserRequestParser {
    private static final String FIRST_WORD_OF_REQUEST = "RATE";
    private static final int INDEX_OF_RATE = 0;
    private static final int INDEX_OF_CURRENCY = 1;
    private static final String DELIMITER = ",";
    private static final String PARAMETER_PERIOD = "-PERIOD";
    private static final String PARAMETER_DATE = "-DATE";
    private static final String PARAMETER_ALGORITHMS = "-ALG";
    private static final String PARAMETER_OUTPUT = "-OUTPUT";
    private final DateHandler dateHandler;
    private List<String> parsedRequest;


    public BotUserRequestParser(DateHandler dateHandler) {
        this.dateHandler = dateHandler;
    }

    public UserRequest parseRequest(String request) {
        parsedRequest = Arrays.asList(request.toUpperCase().split(" "));

        if (!parsedRequest.get(INDEX_OF_RATE).equals(FIRST_WORD_OF_REQUEST)) {
            throw new BotUserRequestException(MessageFormat.format(
                    "Expected \"RATE\" received \"{0}\"", parsedRequest.get(INDEX_OF_RATE)));
        }

        List<Currency> currencies = parseCurrencies(parsedRequest);

        List<LocalDate> dates = parsePeriodOrDate(parsedRequest);

        List<Algorithm> algorithms = parseAlgorithms(parsedRequest);

        OutputMode outputMode = parseOutputMode(parsedRequest);

        return new BotUserRequest(currencies, dates, algorithms, outputMode);
    }

    private List<Currency> parseCurrencies(List<String> parsedRequest) {
        List<String> parsedCurrencies;

        try {
            parsedCurrencies = Arrays.asList(parsedRequest
                    .get(INDEX_OF_CURRENCY)
                    .split(DELIMITER));
        } catch (IndexOutOfBoundsException exception) {
            throw new BotUserRequestException("Expected currency(-ies)");
        }

        return parsedCurrencies
                .stream()
                .filter(this::checkForCurrency)
                .map(Currency::valueOf)
                .collect(Collectors.toList());
    }

    private boolean checkForCurrency(String currency) {
        try {
            Currency.valueOf(currency);
            return true;
        } catch (IllegalArgumentException exception) {
            throw new BotUserRequestException(MessageFormat.format(
                    "There is no data for the \"{0}\"", currency));
        }
    }

    private List<LocalDate> parsePeriodOrDate(List<String> parsedRequest) {
        int indexPeriodOrDate;
        String periodOrDate;
        if (parsedRequest.contains(PARAMETER_PERIOD)) {
            indexPeriodOrDate = parsedRequest.indexOf(PARAMETER_PERIOD) + 1;
        } else if (parsedRequest.contains(PARAMETER_DATE)) {
            indexPeriodOrDate = parsedRequest.indexOf(PARAMETER_DATE) + 1;
        } else throw new BotUserRequestException("Expected keyword \"-period\" or \"-date\"");

        try {
            periodOrDate = parsedRequest.get(indexPeriodOrDate);
        } catch (IndexOutOfBoundsException exception) {
            throw new BotUserRequestException("Expected period or date");
        }

        try {
            Period.valueOf(periodOrDate);
        } catch (IllegalArgumentException exceptionNotFixPeriod) {
            try {
                return dateHandler.processPeriodOrDate(periodOrDate);
            } catch (DateTimeParseException exceptionWrongPeriod) {
                throw new BotUserRequestException(MessageFormat.format(
                        "\"{0}\" - date or period is incorrect", periodOrDate));
            }
        }
        return dateHandler.processPeriodOrDate(periodOrDate);
    }

    private List<Algorithm> parseAlgorithms(List<String> parsedRequest) {
        int indexAlgorithms;
        if (parsedRequest.contains(PARAMETER_ALGORITHMS)) {
            indexAlgorithms = parsedRequest.indexOf(PARAMETER_ALGORITHMS) + 1;
        } else {
            throw new BotUserRequestException("Expected keyword \"-alg\"");
        }
        ;


        List<String> parsedAlgorithms;
        try {
            parsedAlgorithms = Arrays.asList(parsedRequest
                    .get(indexAlgorithms)
                    .split(DELIMITER)
            );
        } catch (IndexOutOfBoundsException exception) {
            throw new BotUserRequestException("Expected algorithm(-s)");
        }

        return parsedAlgorithms
                .stream()
                .filter(this::checkForAlgorithm)
                .map(Algorithm::valueOfLabel)
                .collect(Collectors.toList());
    }

    private boolean checkForAlgorithm(String algorithm) {
        try {
            Algorithm.valueOfLabel(algorithm);
            return true;
        } catch (IllegalArgumentException exception) {
            throw new BotUserRequestException(MessageFormat.format(
                    "\"{0}\" does not exist", algorithm));
        }
    }

    private OutputMode parseOutputMode(List<String> parsedRequest) {
        int indexOutputMode;
        if (parsedRequest.contains(PARAMETER_OUTPUT)) {
            indexOutputMode = parsedRequest.indexOf(PARAMETER_OUTPUT) + 1;
        } else return OutputMode.LIST;


        String outputMode;

        try {
            outputMode = parsedRequest.get(indexOutputMode);
        } catch (IndexOutOfBoundsException exception) {
            throw new BotUserRequestException("Expected output mode");
        }

        try {
            return OutputMode.valueOf(outputMode);
        } catch (IllegalArgumentException exceptionNotFixPeriod) {
            throw new BotUserRequestException(MessageFormat.format(
                    "\"{0}\" - output mode is incorrect", outputMode));
        }
    }
}
