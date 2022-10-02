package org.selbowgreaser.command;

import org.selbowgreaser.forecasting.Algorithm;

public class CommandLineUserRequest implements UserRequest {
    private final String[] request;

    public CommandLineUserRequest(String request) {
        this.request = parseRequest(request);
    }

    private String[] parseRequest(String request) {
        return request.split(" ");
    }

    @Override
    public String getCurrency() {
        int indexCurrency = 1;
        return request[indexCurrency];
    }

    @Override
    public String getPeriod() {
        int indexPeriod = 2;
        return request[indexPeriod];
    }

    @Override
    public Algorithm getAlgorithm() {
        return Algorithm.AVERAGE;
    }
}
