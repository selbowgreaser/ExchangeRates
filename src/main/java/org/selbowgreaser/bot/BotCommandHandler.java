package org.selbowgreaser.bot;

import org.selbowgreaser.forecasting.AverageAlgorithm;
import org.selbowgreaser.handler.OutputHandler;
import org.selbowgreaser.parser.ExchangeRateData;
import org.selbowgreaser.parser.ExchangeRateFilesReader;
import org.selbowgreaser.request.CommandLineUserRequest;
import org.selbowgreaser.request.CommandLineUserRequestException;
import org.selbowgreaser.request.UserRequest;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Scanner;

public class BotCommandHandler{

    public String processRequest(Update update) {
        UserRequest request = getUserRequest(update);

        List<ExchangeRateData> exchangeRateData = new ExchangeRateFilesReader(request).parseExchangeRatesFile();

        List<List<Double>> predictions = new AverageAlgorithm().forecast(request, exchangeRateData);

        return new OutputHandler().processing(exchangeRateData, predictions);
    }

    private UserRequest getUserRequest(Update update) {
        UserRequest request;
        request = new CommandLineUserRequest(update.getMessage().getText());
        return request;
    }
}
