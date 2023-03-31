package com.primforest.tgbot2.Service;

import com.primforest.tgbot2.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot{
    BotConfig config;
   public  TelegramBot (BotConfig config) throws TelegramApiException {this.config=config;}
/*
     List<BotCommand>listOfCommands=new ArrayList<>();
     listOfCommands.add(new BotCommand ( "/start","get greeetings"));
     listOfCommands.add(new BotCommand("/mydata", "get all information about me"));
    listOfCommands.add(new BotCommand("/deletedata","delete all information about me"));
    listOfCommands.add(new BotCommand("/help","how to use this bot"));
    listOfCommands.add(new BotCommand("/settings"," Settings for bot"));
    try{ this.execute(new SetMyCommands((listOfCommands,new BotCommandScopeDefault(),null));}
    catch(TelegramApiException e){log.error("Error setting bot s command list" + e.getMessage());}

 */
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

    private  void startCommandReceived(long chatId,String name) throws TelegramApiException {String answer="Hi-- " +  name + "---The time is "+LocalTime.now()+
        " nice to meeeet you!";
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

