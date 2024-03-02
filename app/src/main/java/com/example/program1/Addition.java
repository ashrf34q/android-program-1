package com.example.program1;

public class Addition {
    private final String[] questions;
    private final int[] correctAnswers;

    public Addition() {
        this.questions = new String[]{"2 + 4 = ", "7 + 2 = ", "9 + 7 = ", "6 + 8 = ", "4 + 6 "};
        this.correctAnswers = new int[]{6, 9, 16, 14, 10};
    }

    public String getQuestionById(int id) {
        return questions[id];
    }

    public int getCorrectAnswer(int id) {
        return correctAnswers[id];
    }
}
