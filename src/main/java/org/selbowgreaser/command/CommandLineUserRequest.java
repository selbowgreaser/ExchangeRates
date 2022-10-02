package org.selbowgreaser.command;

public class CommandLineUserRequest implements UserRequest {
    String[] request; //todo поставить private

    public CommandLineUserRequest(String request) {
        this.request = parseRequest(request);
    }

    private String[] parseRequest(String request) {
        return request.split(" ");
    }

    @Override
    public String getCurrency() {
        return request[1];
    } //todo вывести в константу и назвать для чего она

    @Override
    public String getPeriod() {
        return request[2];
    } //todo вывести в константу и назвать для чего она

    @Override
    public String getAlgorithm() { //todo не нашел где используется метод
        return "avg"; //todo вывести в константу и назвать для чего она
    }
}
