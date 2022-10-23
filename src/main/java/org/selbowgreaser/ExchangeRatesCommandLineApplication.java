package org.selbowgreaser;

import org.selbowgreaser.dao.ExchangeRateDaoImpl;
import org.selbowgreaser.service.reader.ExchangeRatesFileReaderImpl;
import org.selbowgreaser.service.manager.CommandLineRequestProcessingManager;
import org.selbowgreaser.service.parser.CommandLineUserRequestParser;
import org.selbowgreaser.service.forecasting.AlgorithmFactory;
import org.selbowgreaser.service.handler.CommandLineOutputHandler;
import org.selbowgreaser.service.handler.DateHandler;
import org.selbowgreaser.service.visualization.Visualizer;

import java.util.Random;
import java.util.Scanner;


public class ExchangeRatesCommandLineApplication {

    public static void main(String[] args) {
        CommandLineRequestProcessingManager app = new CommandLineRequestProcessingManager(
                new Scanner(System.in),
                new CommandLineUserRequestParser(
                        new DateHandler()),
                new AlgorithmFactory(
                        new ExchangeRateDaoImpl(
                                new ExchangeRatesFileReaderImpl()),
                        new Random()),
                new CommandLineOutputHandler(),
                new Visualizer());
        app.processRequest();
    }
}
