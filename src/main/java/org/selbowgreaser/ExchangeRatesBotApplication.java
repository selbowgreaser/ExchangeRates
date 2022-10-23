package org.selbowgreaser;

import org.selbowgreaser.controller.TelegramBot;
import org.selbowgreaser.service.manager.BotRequestProcessingManager;
import org.selbowgreaser.repository.dao.ExchangeRateDaoImpl;
import org.selbowgreaser.service.reader.ExchangeRatesFileReaderImpl;
import org.selbowgreaser.service.parser.BotUserRequestParser;
import org.selbowgreaser.service.forecasting.AlgorithmFactory;
import org.selbowgreaser.service.handler.BotOutputHandler;
import org.selbowgreaser.service.handler.DateHandler;
import org.selbowgreaser.service.visualization.Visualizer;

import java.util.Map;
import java.util.Random;

public class ExchangeRatesBotApplication {
    private static final Map<String, String> getenv = System.getenv();

    public static void main(String[] args) {
        TelegramBot telegramBot = new TelegramBot(
                getenv.get("BOT_NAME"),
                getenv.get("BOT_TOKEN"),
                new BotRequestProcessingManager(
                        new BotUserRequestParser(
                                new DateHandler()),
                        new AlgorithmFactory(
                                new ExchangeRateDaoImpl(
                                        new ExchangeRatesFileReaderImpl()),
                                new Random())),
                new BotOutputHandler(),
                new Visualizer());
        telegramBot.botConnect();
    }
}
