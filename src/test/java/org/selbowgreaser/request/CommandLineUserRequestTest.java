package org.selbowgreaser.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.selbowgreaser.request.parameters.Currency;

import java.util.Arrays;
import java.util.List;

class CommandLineUserRequestTest {

    @Test
    void getCurrencyOk() {
        System.out.println("TEST GET CURRENCY OK");
        UserRequest userRequest = new CommandLineUserRequest("rate usd -period week");
        Assertions.assertEquals(List.of(Currency.USD), userRequest.getCurrencies());
    }

    @Test
    void getCurrencyWrongOrder() {
        System.out.println();
        UserRequest userRequest = new CommandLineUserRequest("rate ssd tomorrow");
        Assertions.assertThrows(CommandLineUserRequestException.class, userRequest::getCurrencies);
    }

    @Test
    void getPeriod() {
        System.out.println("");
        List<String> test = Arrays.asList("hello world -mean".split(" "));

        System.out.println(test.indexOf("-mean"));
    }

    @Test
    void getAlgorithm() {
    }
}