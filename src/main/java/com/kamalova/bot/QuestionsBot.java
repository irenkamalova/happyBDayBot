package com.kamalova.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionsBot extends TelegramLongPollingBot {
    private final String botToken;
    private final String botName;
    private final Iterator<Question> questions;

    public QuestionsBot(String botToken, String botName, Iterator<Question> questions) {
        this.botToken = botToken;
        this.botName = botName;

        this.questions = questions;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            processCallBack(update.getCallbackQuery());
        } else if (update.hasMessage()) {
            processMessage(update.getMessage());
        }
    }

    private void processMessage(Message message) {
        Long chatId = message.getChatId();
        //if (message.getText().equals("/start")) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Привет! Ответишь на несколько вопросов? Поехали!");
        sendMessage.setReplyMarkup(readyMarkup());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        //}
    }

    private void processCallBack(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        try {
            Message message = callbackQuery.getMessage();
            System.out.println(message.getText());
            System.out.println(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(callbackQuery.getMessage().getChatId());
        if (questions.hasNext()) {
            Question next = questions.next();
            sendMessage.setReplyMarkup(sendInlineKeyBoardMessage(next));
        } else {
            sendMessage.setText("Игра окончена!");
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private InlineKeyboardMarkup sendInlineKeyBoardMessage(Question question) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = question.getAnswers().stream()
                .map(answer -> new InlineKeyboardButton(answer))
                .map(a -> a.setCallbackData(a.getText()))
                .collect(Collectors.toList());
        List<List<InlineKeyboardButton>> keyboardButtons = List.of(buttons);
        inlineKeyboardMarkup.setKeyboard(keyboardButtons);
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup readyMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Готов!");
        List<InlineKeyboardButton> keyboardButtonsRow = List.of(inlineKeyboardButton);
        inlineKeyboardMarkup.setKeyboard(List.of(keyboardButtonsRow));
        return inlineKeyboardMarkup;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
