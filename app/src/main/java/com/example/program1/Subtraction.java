package com.example.program1;

public class Subtraction {
    private final String[] questions;
    private final int[] correctAnswers;

    public Subtraction() {
        this.questions = new String[]{"9 - 8 = ", "9 - 3 = ", "7 - 2 = ", "18 - 6 = ", "6 - 4 = "};
        this.correctAnswers = new int[]{1, 6, 5, 12, 2};
    }

    public String getQuestionById(int id) {
        return questions[id];
    }

    public int getCorrectAnswer(int id) {
        return correctAnswers[id];
    }
}
