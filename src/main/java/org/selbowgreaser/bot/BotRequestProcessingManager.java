package org.selbowgreaser.bot;

import org.selbowgreaser.forecasting.AlgorithmFactory;
import org.selbowgreaser.forecasting.ForecastingAlgorithm;
import org.selbowgreaser.request.RequestResult;
import org.selbowgreaser.request.UserRequest;
import org.selbowgreaser.request.exceptions.BotUserRequestException;
import org.selbowgreaser.request.parameters.Algorithm;
import org.selbowgreaser.request.parameters.Currency;
import org.selbowgreaser.request.parsers.BotUserRequestParser;

import java.util.ArrayList;
import java.util.List;

public class BotRequestProcessingManager {
    private final AlgorithmFactory algorithmFactory;
    private final BotUserRequestParser botUserRequestParser;

    public BotRequestProcessingManager(BotUserRequestParser botUserRequestParser, AlgorithmFactory algorithmFactory) {
        this.botUserRequestParser = botUserRequestParser;
        this.algorithmFactory = algorithmFactory;
    }

    public Answer processRequest(String messageText) {
        List<RequestResult> requestResults = new ArrayList<>();
        UserRequest request;

        try {
            request = botUserRequestParser.parseRequest(messageText);
        } catch (BotUserRequestException botUserRequestException) {
            return new Answer(botUserRequestException.getMessage());
        }

        for (Currency currency : request.getCurrencies()) {
            for (Algorithm algorithm : request.getAlgorithms()) {
                ForecastingAlgorithm forecastingAlgorithm = algorithmFactory.createAlgorithm(algorithm);
                requestResults.add(forecastingAlgorithm.forecast(currency, request.getDates()));
            }
        }

        return new Answer(request.getOutputMode(), requestResults);

//        if (request.getOutputMode().equals(OutputMode.LIST)) {
//            for (RequestResult requestResult : requestResults) {
//                System.out.println(outputHandler.processing(requestResult));
//            }
//        } else {
//            Visualizer visualizer = new Visualizer("Exchange Rates Forecast");
//            visualizer.createJpeg(requestResults);
//        }
    }
}