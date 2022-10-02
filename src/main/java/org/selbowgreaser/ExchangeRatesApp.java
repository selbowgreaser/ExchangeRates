package org.selbowgreaser;

import org.selbowgreaser.handler.CommandLineHandler;
import org.selbowgreaser.handler.RequestHandler;


/**
 * Hello world!
 */
public class ExchangeRatesApp {
    public static void main(String[] args) {
        RequestHandler app = new CommandLineHandler();
        app.processRequest();

    }
}
