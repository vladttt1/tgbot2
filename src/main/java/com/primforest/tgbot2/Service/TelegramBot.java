package com.primforest.tgbot2.Service;

import com.primforest.tgbot2.config.BotConfig;
import com.primforest.tgbot2.model.User;
import com.primforest.tgbot2.model.UserRepository;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot{
    @Autowired
    UserRepository userRepository;
    private static final String HELP_TEXT = "This bot is created for demonstartion of SprinBoot capabilities" +
            "You can execute commands from the main menu on the left or by typing a command";
    BotConfig config;
   public  TelegramBot (BotConfig config) throws TelegramApiException {this.config=config;

     List<BotCommand>listOfCommands=new ArrayList<>();
     listOfCommands.add(new BotCommand ( "/start","get greeetings"));
     listOfCommands.add(new BotCommand("/mydata", "get all information about me"));
    listOfCommands.add(new BotCommand("/deletedata","delete all information about me"));
    listOfCommands.add(new BotCommand("/help","how to use this bot"));
    listOfCommands.add(new BotCommand("/settings"," Settings your preferences"));
    try{ this.execute(new SetMyCommands(listOfCommands,new BotCommandScopeDefault(),null));}
    catch(TelegramApiException e){e.getMessage();}

   }
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()&update.getMessage().hasText()){
            String messageText=update.getMessage().getText();
            long chatId=update.getMessage().getChatId();

            if (messageText.contains("/send")&&config.getOwnerId()==chatId){
                var textToSend=EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf("")));
                var users=userRepository.findAll();
                for (User user:users){
                    try {
                        sendMessage(user.getChatId(),textToSend);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            switch (messageText) {
                case "/start":

                    try {
                        registerUser(update.getMessage());
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                    break;

                case"/help":
                    try {
                        sendMessage(chatId,HELP_TEXT);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case  "/register":
                    register(chatId);
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

    private void register(long chatId) {
       SendMessage message=new SendMessage();
       message.setChatId(String.valueOf(chatId));
       message.setText("Do your really want to register?");
        InlineKeyboardMarkup markupInline= new InlineKeyboardMarkup() ;
        List<List<InlineKeyboardButton>> rowsInLine=new ArrayList<>();
        List<InlineKeyboardButton> rowInLine=new ArrayList<>();
        var yesButton =new InlineKeyboardButton();
        yesButton.setText("Yes");
        yesButton.setCallbackData("YES_BUTTON");
        var noButton=new InlineKeyboardButton();
        noButton.setText("No");
        noButton.setCallbackData("NO_BUTTON");
        rowInLine.add(yesButton);
        rowInLine.add(noButton);
        rowsInLine.add(rowInLine);
        markupInline.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInline);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
   }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    private  void startCommandReceived(long chatId,String name) throws TelegramApiException {

       String answer= EmojiParser.parseToUnicode("Hi--\n " +  name + "\n---The time is "+ LocalDate.now()+
               "\n nice to meeeet you!" + ":blush:");
    sendMessage(chatId,answer);}
    private void sendMessage(long chatId, String textToSend) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        ReplyKeyboardMarkup keyboardMarkup=new ReplyKeyboardMarkup();
        List<KeyboardRow>keyboardRows=new ArrayList<>();
        KeyboardRow row= new KeyboardRow();
        row.add("Weather");
        row.add("get random joke");
        keyboardRows.add(row);
        row.add("register");
        row.add(" check my mydata");
        row.add("delete my data");
        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup );

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

private void registerUser(Message msg){
       if (userRepository.findById(msg.getChatId()).isEmpty()){
           var chatId=msg.getChatId();
           var chat=msg.getChat();
           User user=new User();
           user.setChatId(chatId);
           user.setFirstName(chat.getFirstName());
           user.setLastName(chat.getLastName());
           user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
           userRepository.save(user);

       }
}

}

