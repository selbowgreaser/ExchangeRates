package org.selbowgreaser;

import org.selbowgreaser.bot.Bot;

public class ExchangeRatesBotApp {

    public static void main(String[] args) {
        Bot bot = new Bot("ExchangeRatePredictorBot", "5431184067:AAGeBn0I1mzK4vAxmMuDWXGJ_I_7WOz9M5E");
        bot.botConnect();
    }
}
