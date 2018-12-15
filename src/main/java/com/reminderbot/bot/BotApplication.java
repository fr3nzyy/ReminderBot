package com.reminderbot.bot;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@SpringBootApplication
public class BotApplication {

    private static final String PROXY_HOST = "80.11.200.161";
    private static final int PROXY_PORT = 9999;

    public static void main(String[] args) {
//        SpringApplication.run(BotApplication.class, args);
        BasicConfigurator.configure();
        // Initializes dependencies necessary for the base bot - Guice
        ApiContextInitializer.init();
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        botOptions.setProxyHost(PROXY_HOST);
        botOptions.setProxyPort(PROXY_PORT);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS4);

//        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new ReminderBot(botOptions));
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

}

