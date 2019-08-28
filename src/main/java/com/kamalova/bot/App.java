package com.kamalova.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class App {

    public static void main(String[] args) {
        try (InputStream secretIn = App.class.getClassLoader()
                .getResourceAsStream("secrets.properties");
             InputStream generalIn = App.class.getClassLoader()
                     .getResourceAsStream("general.properties")) {

            Properties secretProperties = new Properties();
            secretProperties.load(secretIn);
            String botToken = secretProperties.getProperty("bot.token");

            Properties generalProperties = new Properties();
            generalProperties.load(generalIn);
            String botName = generalProperties.getProperty("bot.name");

            InputStream data = App.class.getClassLoader()
                    .getResourceAsStream("q.csv");

            List<Question> questions = QuestionData.init(data);
            questions.forEach(System.out::println);

            ApiContextInitializer.init();
            TelegramBotsApi botsApi = new TelegramBotsApi();
            try {
                QuestionsBot bot = new QuestionsBot(botToken, botName, questions.iterator());
                botsApi.registerBot(bot);
                System.out.println("Registered successfully");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Failed to read properties");
        }
    }
}
