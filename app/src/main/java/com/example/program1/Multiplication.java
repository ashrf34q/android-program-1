package com.example.program1;

public class Multiplication {
    private final String[] questions;
    private final int[] correctAnswers;

    public Multiplication() {
        this.questions = new String[]{"9 x 26 = ", "5 x 104 = ", "1 x 11 = ", "91 x 5 = ", "8 x 59 = "};
        this.correctAnswers = new int[]{234, 520, 1, 455, 472};
    }

    public String getQuestionById(int id) {
        return questions[id];
    }

    public int getCorrectAnswerById(int id) {
        return correctAnswers[id];
    }
}
