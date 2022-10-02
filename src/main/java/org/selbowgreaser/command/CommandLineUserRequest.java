package org.selbowgreaser.command;

public class CommandLineUserRequest implements UserRequest {
    String[] request;

    public CommandLineUserRequest(String request) {
        this.request = parseRequest(request);
    }

    private String[] parseRequest(String request) {
        return request.split(" ");
    }

    @Override
    public String getCurrency() {
        return request[1];
    }

    @Override
    public String getPeriod() {
        return request[2];
    }

    @Override
    public String getAlgorithm() {
        return "avg";
    }
}
