package org.selbowgreaser.command;

import org.selbowgreaser.forecasting.Algorithm;

public class CommandLineUserRequest implements UserRequest {
    private final String[] request; //todo поставить private // done
    private final int INDEX_CURRENCY = 1;
    private final int INDEX_PERIOD = 2;

    public CommandLineUserRequest(String request) {
        this.request = parseRequest(request);
    }

    private String[] parseRequest(String request) {
        return request.split(" ");
    }

    @Override
    public String getCurrency() {
        return request[INDEX_CURRENCY];
    } //todo вывести в константу и назвать для чего она // done

    @Override
    public String getPeriod() {
        return request[INDEX_PERIOD];
    } //todo вывести в константу и назвать для чего она // done

    @Override
    public Algorithm getAlgorithm() { //todo не нашел где используется метод
        // Будет использоваться позже
        // done
        return Algorithm.AVERAGE; //todo вывести в константу и назвать для чего она // done
    }
}
