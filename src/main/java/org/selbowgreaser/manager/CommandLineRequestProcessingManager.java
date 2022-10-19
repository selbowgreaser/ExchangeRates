package org.selbowgreaser.manager;

import org.selbowgreaser.forecasting.*;
import org.selbowgreaser.handler.CommandLineOutputHandler;
import org.selbowgreaser.handler.OutputHandler;
import org.selbowgreaser.request.CommandLineUserRequest;
import org.selbowgreaser.request.CommandLineUserRequestException;
import org.selbowgreaser.request.RequestResult;
import org.selbowgreaser.request.UserRequest;
import org.selbowgreaser.request.parameters.Algorithm;
import org.selbowgreaser.request.parameters.Currency;
import org.selbowgreaser.request.parameters.OutputMode;
import org.selbowgreaser.visualization.Visualizer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineRequestProcessingManager implements RequestProcessingManager {
    private final Scanner scannerUserRequest = new Scanner(System.in);
    private final OutputHandler outputHandler = new CommandLineOutputHandler();

    @Override
    public void processRequest() {
        List<RequestResult> requestResults = new ArrayList<>();

        System.out.println("Enter your request in the format \"Rate USD ( -period | -date ) ( week, month | tomorrow, 25.06.2032 ) " +
                "-alg ( AVG | MYST | LY | LR) -output ( list | graph )\":");

        UserRequest request = getUserRequest(scannerUserRequest);

        for (Currency currency : request.getCurrencies()) {
            for (Algorithm algorithm : request.getAlgorithms()) {
                ForecastingAlgorithm forecastingAlgorithm = chooseAlgorithm(algorithm, currency, request.getDates());
                requestResults.add(forecastingAlgorithm.forecast());
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

    private ForecastingAlgorithm chooseAlgorithm(Algorithm algorithm, Currency currency, List<LocalDate> dates) {
        if (algorithm.equals(Algorithm.LAST_YEAR)) {
            return new LastYearAlgorithm(currency, dates);
        } else if (algorithm.equals(Algorithm.LINEAR_REGRESSION)) {
            return new LinearRegressionAlgorithm(currency, dates);
        } else if (algorithm.equals(Algorithm.MYSTICAL)) {
            return new MysticalAlgorithm(currency, dates);
        } else {
            return new AverageAlgorithm(currency, "nan");
        }
    }
}
