package org.selbowgreaser;

import org.selbowgreaser.bot.Bot;

public class ExchangeRatesBotApp {

    public static void main(String[] args) {
        Bot bot = new Bot("ExchangeRatePredictorBot", "5431184067:AAEdGqKYmhKqyKmMr6q8GMO8itmoAiaH3aQ");
        bot.botConnect();
    }
}
