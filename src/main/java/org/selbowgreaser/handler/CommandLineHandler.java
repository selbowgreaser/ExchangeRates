package org.selbowgreaser.handler;

import org.selbowgreaser.request.CommandLineUserRequest;
import org.selbowgreaser.request.CommandLineUserRequestException;
import org.selbowgreaser.request.UserRequest;
import org.selbowgreaser.forecasting.AverageAlgorithm;
import org.selbowgreaser.parser.ExchangeRateData;
import org.selbowgreaser.parser.ExchangeRateFilesReader;

import java.util.List;
import java.util.Scanner;

public class CommandLineHandler implements RequestHandler {
    @Override
    public void processRequest() {
        Scanner userRequest = new Scanner(System.in);

        System.out.println("Enter your request in the format \"Rate USD ( -period | -date ) ( week, month | tomorrow, 25.06.2032 ) " +
                "-alg ( AVG | MYST | LY | LR) -output ( list | graph )\":");

        UserRequest request = getUserRequest(userRequest);

        List<ExchangeRateData> exchangeRateData = new ExchangeRateFilesReader(request).parseExchangeRatesFile();

        List<List<Double>> predictions = new AverageAlgorithm().forecast(request, exchangeRateData);

        String result = new OutputHandler().processing(exchangeRateData, predictions);

        System.out.println("Привет ");

        System.out.println(result);
    }

    private UserRequest getUserRequest(Scanner userRequest) {
        UserRequest request;
        while (true) {
            try {
                request = new CommandLineUserRequest(userRequest.nextLine());
                return request;
            } catch (CommandLineUserRequestException exception) {
                System.out.println(exception.getMessage());
                System.out.println("Try again!");
            }
        }
    }
}
