package org.selbowgreaser;

import org.selbowgreaser.data.ExchangeRateDao;
import org.selbowgreaser.data.ExchangeRateDaoImpl;
import org.selbowgreaser.data.reader.ExchangeRatesFileReader;
import org.selbowgreaser.data.reader.ExchangeRatesFileReaderImpl;
import org.selbowgreaser.forecasting.AlgorithmFactory;
import org.selbowgreaser.handler.CommandLineOutputHandler;
import org.selbowgreaser.handler.DateHandler;
import org.selbowgreaser.handler.OutputHandler;
import org.selbowgreaser.manager.CommandLineRequestProcessingManager;
import org.selbowgreaser.manager.RequestProcessingManager;
import org.selbowgreaser.request.CommandLineUserRequestParser;

import java.util.Random;
import java.util.Scanner;


public class ExchangeRatesCommandLineApp {
    private static final Scanner scannerUserRequest = new Scanner(System.in);
    private static final DateHandler dateHandler = new DateHandler();
    private static final CommandLineUserRequestParser commandLineUserRequestParser = new CommandLineUserRequestParser(dateHandler);
    private static final ExchangeRatesFileReader reader = new ExchangeRatesFileReaderImpl();
    private static final ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl(reader);
    private static final Random random = new Random();
    private static final AlgorithmFactory algorithmFactory = new AlgorithmFactory(exchangeRateDao, random);
    private static final OutputHandler outputHandler = new CommandLineOutputHandler();

    public static void main(String[] args) {
        RequestProcessingManager app = new CommandLineRequestProcessingManager(scannerUserRequest, commandLineUserRequestParser, algorithmFactory, outputHandler);
        app.processRequest();
    }
}
