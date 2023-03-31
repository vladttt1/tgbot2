package com.primforest.tgbot2.Service;

import com.primforest.tgbot2.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot{
    BotConfig config;
   public  TelegramBot (BotConfig config){this.config=config;}
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()&update.getMessage().hasText()){
            String messageText=update.getMessage().getText();
            long chatId=update.getMessage().getChatId();
            switch (messageText) {
                case "/start":

                    try {
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                default:{

                    try {
                        sendMessage(chatId,"Sorry, bro");
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                }
                }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    private  void startCommandReceived(long chatId,String name) throws TelegramApiException {String answer="Hi " +  "Olga   " + name +" nice to meeeet you!";
    sendMessage(chatId,answer);}
    private void sendMessage(long chatId, String textToSend) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }
@Override public String getBotToken(){return  config.getToken();}
    @Override
    public void onRegister() {
        super.onRegister();
    }



    }

