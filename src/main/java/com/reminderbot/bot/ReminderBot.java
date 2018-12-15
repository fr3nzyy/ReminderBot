package com.reminderbot.bot;


import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalTime;
import java.util.logging.Level;

import static org.telegram.telegrambots.meta.logging.BotLogger.log;

/**
 * Created by fr3nzy on 12.12.2018.
 */
public class ReminderBot extends TelegramLongPollingBot {
    private static String BOT_TOKEN = "729204167:AAHl-ZwXs37NIftj48jS-0cUsk2zkPk37VI";
    private static String BOT_USERNAME = "Podboatbot";
    private static long HOUR = 16;
    private static long MINUTE = 26;

    public int creatorId() {
        return 201664263;
    }

    public ReminderBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(), message);
    }

    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage()
                .enableMarkdown(true)
                .setChatId(chatId)
                .setText(s);
        try {
//            while (true) {
            LocalTime time = LocalTime.now();
            execute(sendMessage.setText(time.toString()));
            long hoursToWait = HOUR - time.getHour();
            if (hoursToWait < 0)
                hoursToWait += 24;
            execute(sendMessage.setText(String.valueOf(hoursToWait)));
            long minutesToWait = MINUTE - time.getMinute();
            if (minutesToWait < 0)
                minutesToWait += 60;
            execute(sendMessage.setText(String.valueOf(minutesToWait)));
//            Thread.sleep(Long.valueOf(hoursToWait * 60 * 60 * 1000 + minutesToWait * 60 * 1000));
            execute(sendMessage.setText("Сообщение, которое нужно отправить"));
//            }
        } catch (TelegramApiException e) {
            log(Level.SEVERE, "Exception: ", e.toString());
        }
//         catch (InterruptedException e) {
//            log(Level.SEVERE, "Exception: ", e.toString());
//        }

    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
