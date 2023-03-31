package com.primforest.tgbot2;

import com.primforest.tgbot2.Service.TelegramBot;
import com.primforest.tgbot2.config.BotConfig;
import com.primforest.tgbot2.config.BotInitialazer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class Tgbot2Application {

	public static void main(String[] args) throws TelegramApiException {
		SpringApplication.run(Tgbot2Application.class, args);

	}}