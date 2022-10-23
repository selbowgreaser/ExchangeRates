package org.selbowgreaser.service.manager;

import org.selbowgreaser.model.RequestResult;
import org.selbowgreaser.model.UserRequest;
import org.selbowgreaser.model.parameter.Algorithm;
import org.selbowgreaser.model.parameter.Currency;
import org.selbowgreaser.model.parameter.OutputMode;
import org.selbowgreaser.service.parser.CommandLineUserRequestParser;
import org.selbowgreaser.service.forecasting.AlgorithmFactory;
import org.selbowgreaser.service.forecasting.ForecastingAlgorithm;
import org.selbowgreaser.service.handler.OutputHandler;
import org.selbowgreaser.service.visualization.Visualizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineRequestProcessingManager {
    private final Scanner scannerUserRequest;
    private final AlgorithmFactory algorithmFactory;
    private final OutputHandler outputHandler;
    private final CommandLineUserRequestParser commandLineUserRequestParser;
    private final Visualizer visualizer;

    public CommandLineRequestProcessingManager(Scanner scannerUserRequest, CommandLineUserRequestParser commandLineUserRequestParser, AlgorithmFactory algorithmFactory, OutputHandler outputHandler, Visualizer visualizer) {
        this.scannerUserRequest = scannerUserRequest;
        this.commandLineUserRequestParser = commandLineUserRequestParser;
        this.algorithmFactory = algorithmFactory;
        this.outputHandler = outputHandler;
        this.visualizer = visualizer;
    }

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
                visualizer.createInputStream(requestResults);
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
