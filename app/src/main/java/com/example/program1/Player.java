package com.example.program1;


enum Reward {SINGLE, DOUBLE, JACKPOT};

public class Player {
    private int total;

    private final MainActivity mainActivity = new MainActivity();

    public Player() {
        this.total = 0;
        this.reward = Reward.SINGLE;
    }
    private Reward reward;

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean checkAddition(int userAnswer, int problemId, Addition addition, int currentJackpot) {
        if(userAnswer == addition.getCorrectAnswer(problemId)){
            if(this.reward == Reward.DOUBLE) this.total += 2;
            else if(this.reward == Reward.JACKPOT) { this.total += currentJackpot; mainActivity.resetJackpot();}
            else this.total += 1;

            return true;
        }
        else {
            // Wrong answer
           return false;
        }

    }

    public boolean checkSubtraction(int userAnswer, int problemId, Subtraction subtraction, int currentJackpot) {
        if(userAnswer == subtraction.getCorrectAnswer(problemId)){
            if(this.reward == Reward.DOUBLE) this.total += 4;
            else if(this.reward == Reward.JACKPOT) { this.total += currentJackpot; mainActivity.resetJackpot(); }
            else this.total += 2;

            return true;
        }
        else {
            // Wrong answer
            return false;
        }

    }

    public boolean checkMultiplication(int userAnswer, int problemId, Multiplication multiplication, int currentJackpot) {
        if(userAnswer == multiplication.getCorrectAnswerById(problemId)){
            if(this.reward == Reward.DOUBLE) this.total += 6;
            else if(this.reward == Reward.JACKPOT) { this.total += currentJackpot; mainActivity.resetJackpot(); }
            else this.total += 3;

            return true;
        }
        else {
            // Wrong answer
            return false;
        }
    }
}
