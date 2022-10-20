package org.selbowgreaser.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

public class MessageSenderFactory {
    public SendMessage createSendMessage() {
        return new SendMessage();
    }
}
