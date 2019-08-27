package com.kamalova.bot;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Data {

    public static List<Question> init() {
        List<Question> questions = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder()
                .withSeparator('|')
                .build();

        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader("q.csv"))
                .withSkipLines(0)
                .withCSVParser(parser)
                .build()) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                String questionBody = values[0];
                List<String> answers = List.of(values[1], values[2], values[3]);
                String correctAnswer = values[4];
                Question question = new Question(questionBody, answers, correctAnswer);
                questions.add(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }
}
