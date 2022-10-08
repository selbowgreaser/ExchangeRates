package org.selbowgreaser.request;

import lombok.Getter;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommandLineUserRequest implements UserRequest {
    private static final String DATE_PATTERN = "dd.MM.yyyy";
    private final int INDEX_OF_RATE = 0;
    private final int INDEX_OF_CURRENCY = 1;
    private final String DELIMITER = ",";
    private final String PARAMETER_PERIOD = "-PERIOD";
    private final String PARAMETER_DATE = "-DATE";
    private final String PARAMETER_ALGORITHMS = "-ALG";
    private final String PARAMETER_OUTPUT = "-OUTPUT";

    private final List<String> parsedRequest;
    private final OutputMode outputMode;

    private List<Currency> currencies;
    private String periodOrDate;
    private List<Algorithm> algorithms;

    public CommandLineUserRequest(String request) {
        this.parsedRequest = parseRequest(request);
        this.currencies = parseCurrencies(parsedRequest);
        this.periodOrDate = parsePeriodOrDate(parsedRequest);
        this.algorithms = parseAlgorithms(parsedRequest);
        this.outputMode = parseOutputMode(parsedRequest);
    }

    private List<String> parseRequest(String request) {
        String firstWordOfRequest = "RATE";

        List<String> parsedRequest = Arrays.asList(request.toUpperCase().split(" "));

        if (!parsedRequest.get(INDEX_OF_RATE).equals(firstWordOfRequest)) {
            throw new CommandLineUserRequestException(MessageFormat.format(
                    "Expected \"RATE\" received \"{0}\"", parsedRequest.get(INDEX_OF_RATE)));
        }

        return parsedRequest;
    }

    @Override
    public List<Currency> getCurrencies() {
        return currencies;
    }

    @Override
    public String getPeriodOrDate() {
        return periodOrDate;
    }

    @Override
    public List<Algorithm> getAlgorithms() {
        return algorithms;
    }

    @Override
    public OutputMode getOutputMode() {
        return outputMode;
    }

    private List<Currency> parseCurrencies(List<String> parsedRequest) {
        List<String> parsedCurrencies;

        try {
            parsedCurrencies = Arrays.asList(parsedRequest
                    .get(INDEX_OF_CURRENCY)
                    .split(DELIMITER));
        } catch (IndexOutOfBoundsException exception) {
            throw new CommandLineUserRequestException("Expected currency(-ies)");
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
            throw new CommandLineUserRequestException(MessageFormat.format(
                    "There is no data for the \"{0}\"", currency));
        }
    }

    private String parsePeriodOrDate(List<String> parsedRequest) {
        int indexPeriodOrDate;
        if (parsedRequest.contains(PARAMETER_PERIOD)) {
            indexPeriodOrDate = parsedRequest.indexOf(PARAMETER_PERIOD) + 1;
        } else if (parsedRequest.contains(PARAMETER_DATE)) {
            indexPeriodOrDate = parsedRequest.indexOf(PARAMETER_DATE) + 1;
        } else throw new CommandLineUserRequestException("Expected keyword \"-period\" or \"-date\"");

        try {
            periodOrDate = parsedRequest.get(indexPeriodOrDate);
        } catch (IndexOutOfBoundsException exception) {
            throw new CommandLineUserRequestException("Expected period or date");
        }

        try {
            Period.valueOf(periodOrDate);
            return periodOrDate;
        } catch (IllegalArgumentException exceptionNotFixPeriod) {
            try {
                LocalDate.parse(periodOrDate, DateTimeFormatter.ofPattern(DATE_PATTERN));
            } catch (DateTimeParseException exceptionWrongPeriod) {
                throw new CommandLineUserRequestException(MessageFormat.format(
                        "\"{0}\" - date or period is incorrect", periodOrDate));
            }
        }
        return periodOrDate;
    }

    private List<Algorithm> parseAlgorithms(List<String> parsedRequest) {
        int indexAlgorithms;
        if (parsedRequest.contains(PARAMETER_ALGORITHMS)) {
            indexAlgorithms = parsedRequest.indexOf(PARAMETER_ALGORITHMS) + 1;
        } else return Collections.singletonList(Algorithm.AVERAGE);


        List<String> parsedAlgorithms;
        try {
            parsedAlgorithms = Arrays.asList(parsedRequest
                    .get(indexAlgorithms)
                    .split(DELIMITER)
            );
        } catch (IndexOutOfBoundsException exception) {
            throw new CommandLineUserRequestException("Expected algorithm(-s)");
        }

        return parsedAlgorithms
                .stream()
                .filter(this::checkForAlgorithm)
                .map(Algorithm::valueOf)
                .collect(Collectors.toList());
    }

    private boolean checkForAlgorithm(String algorithm) {
        try {
            Algorithm.valueOf(algorithm);
            return true;
        } catch (IllegalArgumentException exception) {
            throw new CommandLineUserRequestException(MessageFormat.format(
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
            throw new CommandLineUserRequestException("Expected output mode");
        }

        try {
            return OutputMode.valueOf(outputMode);
        } catch (IllegalArgumentException exceptionNotFixPeriod) {
            throw new CommandLineUserRequestException(MessageFormat.format(
                    "\"{0}\" - output mode is incorrect", outputMode));
        }
    }
}
