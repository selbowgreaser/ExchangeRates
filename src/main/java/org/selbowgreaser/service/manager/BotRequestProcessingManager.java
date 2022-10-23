package org.selbowgreaser.service.manager;

import org.selbowgreaser.model.BotResponse;
import org.selbowgreaser.model.RequestResult;
import org.selbowgreaser.model.UserRequest;
import org.selbowgreaser.model.parameter.Algorithm;
import org.selbowgreaser.model.parameter.Currency;
import org.selbowgreaser.service.parser.BotUserRequestParser;
import org.selbowgreaser.service.forecasting.AlgorithmFactory;
import org.selbowgreaser.service.forecasting.ForecastingAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class BotRequestProcessingManager {
    private final AlgorithmFactory algorithmFactory;
    private final BotUserRequestParser botUserRequestParser;

    public BotRequestProcessingManager(BotUserRequestParser botUserRequestParser, AlgorithmFactory algorithmFactory) {
        this.botUserRequestParser = botUserRequestParser;
        this.algorithmFactory = algorithmFactory;
    }

    public BotResponse processRequest(String messageText) {
        List<RequestResult> requestResults = new ArrayList<>();
        UserRequest request;

        try {
            request = botUserRequestParser.parseRequest(messageText);
        } catch (BotUserRequestException botUserRequestException) {
            return new BotResponse(botUserRequestException.getMessage());
        }

        for (Currency currency : request.getCurrencies()) {
            for (Algorithm algorithm : request.getAlgorithms()) {
                ForecastingAlgorithm forecastingAlgorithm = algorithmFactory.createAlgorithm(algorithm);
                requestResults.add(forecastingAlgorithm.forecast(currency, request.getDates()));
            }
        }

        return new BotResponse(request.getOutputMode(), requestResults);
    }
}