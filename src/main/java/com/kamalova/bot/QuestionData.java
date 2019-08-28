package com.kamalova.bot;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class QuestionData {

    public static List<Question> init(InputStream data) {
        List<Question> questions = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder()
                .withSeparator('|')
                .build();

        try (CSVReader csvReader = new CSVReaderBuilder((new InputStreamReader(data)))
                .withSkipLines(0)
                .withCSVParser(parser)
                .build()) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                try {
                    int number = Integer.valueOf(values[0]);
                    String questionBody = values[1];
                    List<String> answers = Arrays.asList(values[2], values[3], values[4]);
                    String correctAnswer = values[5];
                    Question question = new Question(number, questionBody, answers, correctAnswer);
                    questions.add(question);
                } catch (Exception e) {
                    System.out.println(values);
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
             }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }
}
