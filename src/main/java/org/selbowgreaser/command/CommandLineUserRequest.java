package org.selbowgreaser.command;

import org.selbowgreaser.forecasting.Algorithm;

public class CommandLineUserRequest implements UserRequest {
    private final String[] request; //todo поставить private // done

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
    } //todo вывести в константу и назвать для чего она // done

    @Override
    public String getPeriod() {
        int indexPeriod = 2;
        return request[indexPeriod];
    } //todo вывести в константу и назвать для чего она // done

    @Override
    public Algorithm getAlgorithm() { //todo не нашел где используется метод
        // Будет использоваться позже
        // done
        return Algorithm.AVERAGE; //todo вывести в константу и назвать для чего она // done
    }
}
