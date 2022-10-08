package org.selbowgreaser;

import org.selbowgreaser.handler.CommandLineHandler;
import org.selbowgreaser.handler.RequestHandler;


public class ExchangeRatesCommandLineApp {
    public static void main(String[] args) {
        RequestHandler app = new CommandLineHandler();
        app.processRequest();

    }
}
