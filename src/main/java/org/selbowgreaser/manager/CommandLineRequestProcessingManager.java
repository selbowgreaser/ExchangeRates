package org.selbowgreaser.manager;

import org.selbowgreaser.forecasting.AlgorithmFactory;
import org.selbowgreaser.forecasting.ForecastingAlgorithm;
import org.selbowgreaser.handler.OutputHandler;
import org.selbowgreaser.request.CommandLineUserRequestException;
import org.selbowgreaser.request.CommandLineUserRequestParser;
import org.selbowgreaser.request.RequestResult;
import org.selbowgreaser.request.UserRequest;
import org.selbowgreaser.request.parameters.Algorithm;
import org.selbowgreaser.request.parameters.Currency;
import org.selbowgreaser.request.parameters.OutputMode;
import org.selbowgreaser.visualization.Visualizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineRequestProcessingManager implements RequestProcessingManager {
    private final Scanner scannerUserRequest;
    private final AlgorithmFactory algorithmFactory;
    private final OutputHandler outputHandler;
    private final CommandLineUserRequestParser commandLineUserRequestParser;

    public CommandLineRequestProcessingManager(Scanner scannerUserRequest, CommandLineUserRequestParser commandLineUserRequestParser, AlgorithmFactory algorithmFactory, OutputHandler outputHandler) {
        this.scannerUserRequest = scannerUserRequest;
        this.commandLineUserRequestParser = commandLineUserRequestParser;
        this.algorithmFactory = algorithmFactory;
        this.outputHandler = outputHandler;
    }

    @Override
    public void processRequest() {
        while (true) {
            List<RequestResult> requestResults = new ArrayList<>();

            System.out.println("Enter your request in the format \"Rate USD ( -period | -date ) ( week, month | tomorrow, 25.06.2032 ) " +
                    "-alg ( AVG | MYST | LY | LR) -output ( list | graph )\":");

            UserRequest request = getUserRequest(scannerUserRequest);

            for (Currency currency : request.getCurrencies()) {
                for (Algorithm algorithm : request.getAlgorithms()) {
                    ForecastingAlgorithm forecastingAlgorithm = algorithmFactory.createAlgorithm(algorithm);
                    requestResults.add(forecastingAlgorithm.forecast(currency, request.getDates()));
                }
            }

            if (request.getOutputMode().equals(OutputMode.LIST)) {
                for (RequestResult requestResult : requestResults) {
                    System.out.println(outputHandler.processing(requestResult));
                }
            } else {
                Visualizer visualizer = new Visualizer("Exchange Rates Forecast");
                visualizer.createJpeg(requestResults);
            }
        }
    }

    private UserRequest getUserRequest(Scanner userRequest) {
        while (true) {
            try {
                return commandLineUserRequestParser.parseRequest(userRequest.nextLine());
            } catch (CommandLineUserRequestException exception) {
                System.out.println(exception.getMessage());
                System.out.println("Try again!");
            }
        }
    }
}
