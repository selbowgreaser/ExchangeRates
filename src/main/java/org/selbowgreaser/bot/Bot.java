package org.selbowgreaser.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Bot extends TelegramLongPollingBot {
    private static final long RECONNECT_PAUSE = 10000;
    private final String botName;
    private final String botToken;

    public Bot(String botName, String botToken) {
        super();
        this.botName = botName;
        this.botToken = botToken;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();
        String inputText = update.getMessage().getText();
        System.out.println(inputText);

        if (inputText.startsWith("/start")) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Enter your request in the format\n`Rate USD ( -period | -date ) ( week, month | tomorrow, 25.06.2032 ) -alg ( AVG | MYST | LY | LR) -output ( list | graph )`");
            message.setParseMode("Markdown");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Зашли в елсе");
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(new BotCommandHandler().processRequest(update));
            message.setParseMode("Markdown");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    public void botConnect() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException telegramApiException) {
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                return;
            }
            botConnect();
        }
    }
}
