package com.kamalova.bot;

import java.util.List;

public class Question {

    private final int number;
    private final String question;
    private final List<String> answers;
    private final String correctAnswer;

    public Question(int number, String question, List<String> answers, String correctAnswer) {
        this.number = number;
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "number=" + number +
                ", question='" + question + '\'' +
                ", answers=" + answers +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }
}
