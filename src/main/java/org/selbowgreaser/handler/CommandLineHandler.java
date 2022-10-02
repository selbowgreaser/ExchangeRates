package org.selbowgreaser.handler;

import org.selbowgreaser.command.CommandLineUserRequest;
import org.selbowgreaser.command.UserRequest;
import org.selbowgreaser.forecasting.AverageAlgorithm;
import org.selbowgreaser.parser.ExchangeRateData;
import org.selbowgreaser.parser.ExchangeRateFilesReader;

import java.util.List;
import java.util.Scanner;

public class CommandLineHandler implements RequestHandler {
    @Override
    public void processRequest() {
        Scanner userRequest = new Scanner(System.in);

        System.out.println("Введите запрос в формате \"rate USD tomorrow\":");

        UserRequest request = new CommandLineUserRequest(userRequest.nextLine());

        ExchangeRateData exchangeRateData = new ExchangeRateFilesReader(request).parseExchangeRatesFile();

        List<Double> predictions = new AverageAlgorithm().forecast(request, exchangeRateData);

        String result = new OutputHandler().processing(exchangeRateData, predictions); //todo result // done

        System.out.println(result);
    }
}
