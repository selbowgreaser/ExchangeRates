package org.selbowgreaser;

import org.selbowgreaser.bot.Bot;
import org.selbowgreaser.bot.BotRequestProcessingManager;
import org.selbowgreaser.bot.MessageSenderFactory;
import org.selbowgreaser.model.dao.ExchangeRateDao;
import org.selbowgreaser.model.dao.ExchangeRateDaoImpl;
import org.selbowgreaser.model.reader.ExchangeRatesFileReader;
import org.selbowgreaser.model.reader.ExchangeRatesFileReaderImpl;
import org.selbowgreaser.forecasting.AlgorithmFactory;
import org.selbowgreaser.handler.BotOutputHandler;
import org.selbowgreaser.handler.DateHandler;
import org.selbowgreaser.handler.OutputHandler;
import org.selbowgreaser.request.parsers.BotUserRequestParser;

import java.util.Map;
import java.util.Random;

public class ExchangeRatesBotApp {
    private static final Map<String, String> getenv = System.getenv();

    private static final DateHandler dateHandler = new DateHandler();
    private static final BotUserRequestParser botUserRequestParser = new BotUserRequestParser(dateHandler);
    private static final ExchangeRatesFileReader reader = new ExchangeRatesFileReaderImpl();
    private static final ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl(reader);
    private static final Random random = new Random();
    private static final AlgorithmFactory algorithmFactory = new AlgorithmFactory(exchangeRateDao, random);
    private static final OutputHandler outputHandler = new BotOutputHandler();
    private static final BotRequestProcessingManager botRequestProcessingManager = new BotRequestProcessingManager(botUserRequestParser, algorithmFactory);
    private static final MessageSenderFactory messageSenderFactory = new MessageSenderFactory();


    public static void main(String[] args) {
        Bot bot = new Bot(getenv.get("BOT_NAME"), getenv.get("BOT_TOKEN"), messageSenderFactory, botRequestProcessingManager, exchangeRateDao, algorithmFactory, outputHandler);
        bot.botConnect();
    }
}
