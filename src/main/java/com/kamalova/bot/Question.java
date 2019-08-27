package com.kamalova.bot;

import java.util.List;

public class Answer {
    private final String question;
    private final List<String> answers;
    private final String correctAnswer;

    public Answer(String question, List<String> answers, String correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }
}
