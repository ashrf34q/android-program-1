package com.example.program1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView p1_Total;
    TextView p2_Total;
    TextView currentPlayerView;
    TextView jackpot;
    ImageView dieImage;
    TextView problem;
    EditText userInput;
    Button checkButton;


    // ****** State variables *****
    Player player1;
    Player player2;
    enum CurrPlayer {P1, P2};
    enum RewardOptions {SINGLE, DOUBLE, JACKPOT};
    enum QuestionType {ADDITION, SUBTRACTION, MULTIPLICATION};
    CurrPlayer currentPlayer;
    QuestionType questionType;
    RewardOptions rewardOptions;
//    HelperClass helperClass;
    int problemId;
    int currentJackpot;

    //   ******************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1 = new Player();
        player2 = new Player();
        currentPlayer = CurrPlayer.P1;
        currentJackpot = 5;
        problem = findViewById(R.id.problem);
        dieImage = findViewById(R.id.imageView);
        jackpot = findViewById(R.id.jackpot);
        currentPlayerView = findViewById(R.id.currentPlayer);
        p1_Total = findViewById(R.id.p1_total);
        p2_Total = findViewById(R.id.p2_total);
        userInput = findViewById(R.id.userInput);

//        On start of the activity, the user can't write an answer
//        userInput.setFocusable(false);
        userInput.setEnabled(false);
        checkButton.setEnabled(false);
    }


    public void rollDie(View view) {
        Addition addition;
        Subtraction subtraction;
        Multiplication multiplication;
        Random random = new Random();

        int randomInt = random.nextInt(6) + 1; // Random number between 1 and 6
        problemId = random.nextInt(5);
        switch (randomInt) {
            case 1: // addition
                dieImage.setImageResource(R.drawable.die1);
                addition = new Addition();
                String additionProblem = addition.getQuestionById(problemId);
                problem.setText(additionProblem);
                questionType = QuestionType.ADDITION;
                userInput.setEnabled(true);
                checkButton.setEnabled(true);
                break;
            case 2: // subtraction
                dieImage.setImageResource(R.drawable.die2);
                subtraction = new Subtraction();
                String subtractionProblem = subtraction.getQuestionById(problemId);
                problem.setText(subtractionProblem);
                questionType = QuestionType.SUBTRACTION;
                userInput.setEnabled(true);
                checkButton.setEnabled(true);
                break;
            case 3: // multiplication
                dieImage.setImageResource(R.drawable.die3);
                multiplication = new Multiplication();
                String multiplicationProblem = multiplication.getQuestionById(problemId);
                problem.setText(multiplicationProblem);
                questionType = QuestionType.MULTIPLICATION;
                userInput.setEnabled(true);
                checkButton.setEnabled(true);
                break;
            case 4: // Try again for a double reward
                dieImage.setImageResource(R.drawable.die4);
                rewardOptions = RewardOptions.DOUBLE;
                Toast.makeText(this, "Try again, earn 2x points if you get it right!", Toast.LENGTH_LONG).show();
                break;
            case 5: // Lose a turn
                dieImage.setImageResource(R.drawable.die5);
                if(currentPlayer == CurrPlayer.P1) currentPlayer = CurrPlayer.P2;
                else currentPlayer = CurrPlayer.P1;
                break;
            case 6: // Try for jackpot
                dieImage.setImageResource(R.drawable.die6);
                rewardOptions = RewardOptions.JACKPOT;
                Toast.makeText(this, "Try for jackpot", Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }
    }

    public void checkAnswer(View view) {
//    After implementing DOUBLE and JACKPOT, reset it to SINGLE
        Addition addition;
        Subtraction subtraction;
        Multiplication multiplication;

        if(userInput.getText().toString().equals("")) {
//            Empty input
            userInput.setError("Provide an answer!");
        }
        else {
            userInput.setEnabled(false);
            checkButton.setEnabled(false);
//            Now we can check the answer
            userInput.setEnabled(false);
            switch (questionType) {
                case ADDITION:
//                    We know it's an addition problem, and we have the problem id. Fetch the correct answer for that problem and check answer.
                    addition = new Addition();
                    if(currentPlayer == CurrPlayer.P1) { // Player 1's turn
                        if(!player1.checkAddition(Integer.parseInt(userInput.getText().toString()), problemId, addition, currentJackpot))
                            currentJackpot += 1; // Wrong answer
                        else{
                            // Player 1 answered correctly, update their total on the UI
                            p1_Total.setText(player1.getTotal());
                            currentPlayer = CurrPlayer.P2;
                        }
                    }
                    else { // Player 2's turn
                        if(!player2.checkAddition(Integer.parseInt(userInput.getText().toString()), problemId, addition, currentJackpot))
                            currentJackpot += 1; // Wrong answer
                        else {
                            p2_Total.setText(player2.getTotal());
                            currentPlayer = CurrPlayer.P1;
                        }
                    }
                    break;
                case SUBTRACTION:
                    subtraction = new Subtraction();
                    if(currentPlayer == CurrPlayer.P1) {
                        if(!player1.checkSubtraction(Integer.parseInt(userInput.getText().toString()), problemId, subtraction, currentJackpot))
                            currentJackpot += 2;
                        else {
                            p1_Total.setText(player1.getTotal());
                            currentPlayer = CurrPlayer.P2;
                        }
                    }
                    else {
                        if(!player2.checkSubtraction(Integer.parseInt(userInput.getText().toString()), problemId, subtraction, currentJackpot))
                            currentJackpot += 2;
                        else {
                            p2_Total.setText(player2.getTotal());
                            currentPlayer = CurrPlayer.P1;
                        }
                    }
                    break;
                case MULTIPLICATION:
                    multiplication = new Multiplication();
                    if(currentPlayer == CurrPlayer.P1) {
                        if(!player1.checkMultiplication(Integer.parseInt(userInput.getText().toString()), problemId, multiplication, currentJackpot))
                            currentJackpot += 3;
                        else {
                            currentPlayer = CurrPlayer.P2;
                            p1_Total.setText(player1.getTotal());
                        }
                    }
                    else {
                        if(!player2.checkMultiplication(Integer.parseInt(userInput.getText().toString()), problemId, multiplication, currentJackpot))
                            currentJackpot += 3;
                        else {
                            currentPlayer = CurrPlayer.P1;
                            p2_Total.setText(player2.getTotal());
                        }
                    }
                    break;

                default:
                    break;

            }
            problem.setText("");
            userInput.setText("");

        }


    }
}

/*
class HelperClass {
    private int p1Total, p2Total, jackPot;

    public HelperClass() {
        this.p1Total = 0;
        this.p2Total = 0;
        this.jackPot = 5;
    }

    public int getP1Total() {
        return p1Total;
    }

    public void setP1Total(int p1Total) {
        this.p1Total += p1Total;
    }

    public int getP2Total() {
        return p2Total;
    }

    public void setP2Total(int p2Total) {
        this.p2Total += p2Total;
    }

    public int getJackPot() {
        return jackPot;
    }

    public void setJackPot(int jackPot) {
        this.jackPot = jackPot;
    }
}
 */