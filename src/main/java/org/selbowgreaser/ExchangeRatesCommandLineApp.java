package org.selbowgreaser;

import org.selbowgreaser.manager.CommandLineRequestProcessingManager;
import org.selbowgreaser.manager.RequestProcessingManager;


public class ExchangeRatesCommandLineApp {
    public static void main(String[] args) {
        RequestProcessingManager app = new CommandLineRequestProcessingManager();
        while (true) {
            app.processRequest();
        }
    }
}
