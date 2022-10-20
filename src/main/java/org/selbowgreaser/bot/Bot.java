package org.selbowgreaser.bot;

import org.selbowgreaser.forecasting.AlgorithmFactory;
import org.selbowgreaser.handler.OutputHandler;
import org.selbowgreaser.model.dao.ExchangeRateDao;
import org.selbowgreaser.request.RequestResult;
import org.selbowgreaser.request.parameters.OutputMode;
import org.selbowgreaser.visualization.Visualizer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Bot extends TelegramLongPollingBot {
    private static final long RECONNECT_PAUSE = 10000;
    public static final String PLEASE_ENTER_REQUEST = "Enter your request in the format\n`Rate USD ( -period | -date ) ( week, month | tomorrow, 25.06.2032 ) -alg ( MYST | LY | LR) -output ( list | graph )`";
    public static final String START_WORD_OF_REQUEST = "RATE ";
    private final String botName;
    private final String botToken;
    private final ExchangeRateDao exchangeRateDao;
    private final OutputHandler outputHandler;
    private final AlgorithmFactory algorithmFactory;
    private final MessageSenderFactory messageSenderFactory;
    private final BotRequestProcessingManager botRequestProcessingManager;
    private Answer answer;

    public Bot(String botName, String botToken, MessageSenderFactory messageSenderFactory, BotRequestProcessingManager botRequestProcessingManager, ExchangeRateDao exchangeRateDao, AlgorithmFactory algorithmFactory, OutputHandler outputHandler) {
        this.botName = botName;
        this.botToken = botToken;
        this.exchangeRateDao = exchangeRateDao;
        this.algorithmFactory = algorithmFactory;
        this.outputHandler = outputHandler;
        this.messageSenderFactory = messageSenderFactory;
        this.botRequestProcessingManager = botRequestProcessingManager;
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

        if (!inputText.startsWith(START_WORD_OF_REQUEST)) {
            SendMessage messageSender = messageSenderFactory.createSendMessage();
            messageSender.setChatId(chatId);
            messageSender.setText(PLEASE_ENTER_REQUEST);
            messageSender.setParseMode("Markdown");
            try {
                execute(messageSender);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            answer = botRequestProcessingManager.processRequest(inputText);
            if (answer.getMessage().equals("")) {
                if (answer.getOutputMode().equals(OutputMode.LIST)) {
                    SendMessage sendMessage = messageSenderFactory.createSendMessage();
                    sendMessage.setChatId(chatId);
                    StringBuilder message = new StringBuilder();
                    for (RequestResult requestResult : answer.getRequestResults()) {
                        message.append(outputHandler.processing(requestResult));
                    }
                    sendMessage.setText(message.toString());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    SendPhoto sendPhoto = new SendPhoto();
                    Visualizer visualizer = new Visualizer("Exchange Rates Forecast");
                    byte[] outputStream = visualizer.createOutputStream(answer.getRequestResults()).toByteArray();
                    InputStream inputStream = new ByteArrayInputStream(outputStream);
                    sendPhoto.setPhoto(new InputFile(inputStream, "Hello"));
                    sendPhoto.setChatId(chatId);
                    try {
                        execute(sendPhoto);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                SendMessage messageSender = messageSenderFactory.createSendMessage();
                messageSender.setChatId(chatId);
                messageSender.setText(answer.getMessage());
                messageSender.setText("asdasd");
                try {
                    execute(messageSender);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
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
