package org.selbowgreaser.controller;

import lombok.SneakyThrows;
import org.selbowgreaser.service.manager.BotRequestProcessingManager;
import org.selbowgreaser.model.BotResponse;
import org.selbowgreaser.model.RequestResult;
import org.selbowgreaser.model.parameter.OutputMode;
import org.selbowgreaser.service.handler.OutputHandler;
import org.selbowgreaser.service.visualization.Visualizer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.InputStream;

public class TelegramBot extends TelegramLongPollingBot {
    private static final long RECONNECT_PAUSE = 10000;
    public static final String PLEASE_ENTER_REQUEST = "Enter your request in the format\n`Rate USD ( -period | -date ) ( week, month | tomorrow, 25.06.2032 ) -alg ( MYST | LY | LR) -output ( list | graph )`";
    public static final String START_WORD_OF_REQUEST = "RATE ";
    private final String botName;
    private final String botToken;
    private final OutputHandler outputHandler;
    private final BotRequestProcessingManager botRequestProcessingManager;
    private final Visualizer visualizer;

    public TelegramBot(String botName, String botToken, BotRequestProcessingManager botRequestProcessingManager, OutputHandler outputHandler, Visualizer visualizer) {
        this.botName = botName;
        this.botToken = botToken;
        this.outputHandler = outputHandler;
        this.botRequestProcessingManager = botRequestProcessingManager;
        this.visualizer = visualizer;
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
        String inputText = update.getMessage().getText().toUpperCase();

        if (isStartInputTextStartWordOfRequest(inputText)) {
            SendMessage message = new SendMessage();

            message.setChatId(chatId);
            message.setText(PLEASE_ENTER_REQUEST);
            message.setParseMode("Markdown");

            executeSendMessage(message);
        } else {
            BotResponse botResponse = botRequestProcessingManager.processRequest(inputText);

            if (botResponse.getMessage().equals("")) {
                if (isOutputModeList(botResponse.getOutputMode())) {
                    SendMessage message = new SendMessage();

                    message.setChatId(chatId);

                    StringBuilder answer = new StringBuilder();
                    for (RequestResult requestResult : botResponse.getRequestResults()) {
                        answer.append(outputHandler.processing(requestResult));
                    }
                    message.setText(answer.toString());

                    executeSendMessage(message);
                } else {
                    SendPhoto message = new SendPhoto();

                    InputStream inputStream = visualizer.createInputStream(botResponse.getRequestResults());

                    message.setPhoto(new InputFile(inputStream, "Hello"));
                    message.setChatId(chatId);

                    executeSendPhoto(message);
                }
            } else {
                SendMessage message = new SendMessage();

                message.setChatId(chatId);
                message.setText(botResponse.getMessage());

                executeSendMessage(message);
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

    @SneakyThrows
    private void executeSendPhoto(SendPhoto message) {
        execute(message);
    }

    @SneakyThrows
    private void executeSendMessage(SendMessage message) {
        execute(message);
    }

    private static boolean isStartInputTextStartWordOfRequest(String inputText) {
        return !inputText.startsWith(START_WORD_OF_REQUEST);
    }

    private boolean isOutputModeList(OutputMode outputMode) {
        return outputMode.equals(OutputMode.LIST);
    }
}