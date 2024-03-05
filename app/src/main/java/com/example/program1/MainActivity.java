package com.example.program1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private Player player1;
    Player player2;
    enum CurrPlayer {P1, P2}
    enum QuestionType {ADDITION, SUBTRACTION, MULTIPLICATION, NONE}
    CurrPlayer currentPlayer;
    QuestionType questionType;
    int problemId;
    int currentJackpot;
    //   **********************

    public void resetJackpot() {
        this.currentJackpot = 5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        problemId = -1;
        player1 = new Player();
        player2 = new Player();
        questionType = QuestionType.NONE;
        currentPlayer = CurrPlayer.P1;
        currentJackpot = 5;
        problem = findViewById(R.id.problem);
        dieImage = findViewById(R.id.imageView);
        jackpot = findViewById(R.id.jackpot);
        currentPlayerView = findViewById(R.id.currentPlayer);
        p1_Total = findViewById(R.id.p1_total);
        p2_Total = findViewById(R.id.p2_total);
        userInput = findViewById(R.id.userInput);
        checkButton = findViewById(R.id.checkButton);

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
                if(currentPlayer == CurrPlayer.P1) player1.setReward(Reward.DOUBLE);
                else player2.setReward(Reward.DOUBLE);
                Toast.makeText(this, "Try again, earn 2x points if you get it right!", Toast.LENGTH_SHORT).show();
                break;
            case 5: // Lose a turn
                Toast.makeText(this, "Lose a turn!", Toast.LENGTH_SHORT).show();
                dieImage.setImageResource(R.drawable.die5);
                if(currentPlayer == CurrPlayer.P1) { currentPlayer = CurrPlayer.P2; currentPlayerView.setText("P2"); }
                else { currentPlayer = CurrPlayer.P1; currentPlayerView.setText("P1"); }
                problem.setText("");
                userInput.setText("");
                userInput.setEnabled(false);
                checkButton.setEnabled(false);
                break;
            case 6: // Try for jackpot
                dieImage.setImageResource(R.drawable.die6);
                if(currentPlayer == CurrPlayer.P1) player1.setReward(Reward.JACKPOT);
                else player2.setReward(Reward.JACKPOT);
                Toast.makeText(this, "Try for jackpot", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    public void checkAnswer(View view) {
        Addition addition;
        Subtraction subtraction;
        Multiplication multiplication;

        if(userInput.getText().toString().equalsIgnoreCase("")) {
//            Empty input
            userInput.setError("Provide an answer!");
        }
        else {
            Log.w("program1", "I'm working so far!");
            userInput.setEnabled(false);
            checkButton.setEnabled(false);
//            Now we can check the answer
            switch (questionType) {
                case ADDITION:
                    Log.w("program1", "addition");
//                    We know it's an addition problem, and we have the problem id. Fetch the correct answer for that problem and check answer.
                    addition = new Addition();
                    if(currentPlayer == CurrPlayer.P1) { // Player 1's turn
                        if(!player1.checkAddition(Integer.parseInt(userInput.getText().toString()), problemId, addition, currentJackpot)) {
                            currentJackpot += 1; // Wrong answer
                            Log.w("program1", "Player 1, Wrong answer");
                        }
                        else{
                            // Player 1 answered correctly, update their total on the UI
                            p1_Total.setText(String.valueOf(player1.getTotal()));

                            Log.w("program1", "Player 1, Correct answer");
                        }
                        currentPlayer = CurrPlayer.P2;
                    }
                    else { // Player 2's turn

                        if(!player2.checkAddition(Integer.parseInt(userInput.getText().toString()), problemId, addition, currentJackpot)) {
                            currentJackpot += 1; // Wrong answer
                            Log.w("program1", "Player 2, Wrong answer");
                        }
                        else {
                            p2_Total.setText(String.valueOf(player2.getTotal()));
                            Log.w("program1", "Player 2, Correct answer");
                        }
                        currentPlayer = CurrPlayer.P1;
                    }
                    break;
                case SUBTRACTION:
                    Log.w("program1", "subtraction");
                    subtraction = new Subtraction();
                    if(currentPlayer == CurrPlayer.P1) {
                        if(!player1.checkSubtraction(Integer.parseInt(userInput.getText().toString()), problemId, subtraction, currentJackpot)) {
                            currentJackpot += 2;
                            Log.w("program1", "Player 1, Wrong answer");
                        }
                        else {
                            p1_Total.setText(String.valueOf(player1.getTotal()));
                            Log.w("program1", "Player 1, Correct answer");
                        }
                        currentPlayer = CurrPlayer.P2;
                    }
                    else {
                        if(!player2.checkSubtraction(Integer.parseInt(userInput.getText().toString()), problemId, subtraction, currentJackpot)) {
                            currentJackpot += 2;
                            Log.w("program1", "Player 2, Wrong answer");
                        }
                        else {
                            p2_Total.setText(String.valueOf(player2.getTotal()));

                            Log.w("program1", "Player 2, Correct answer");
                        }
                        currentPlayer = CurrPlayer.P1;
                    }
                    break;
                case MULTIPLICATION:
                    multiplication = new Multiplication();
                    Log.w("program1", "multiplication");
                    if(currentPlayer == CurrPlayer.P1) {
                        if(!player1.checkMultiplication(Integer.parseInt(userInput.getText().toString()), problemId, multiplication, currentJackpot)) {
                            currentJackpot += 3;
                            Log.w("program1", "Player 1, Wrong answer");
                        }
                        else {
                            p1_Total.setText(String.valueOf(player1.getTotal()));
                            Log.w("program1", "Player 1, Correct answer");
                        }
                        currentPlayer = CurrPlayer.P2;
                    }
                    else {
                        if(!player2.checkMultiplication(Integer.parseInt(userInput.getText().toString()), problemId, multiplication, currentJackpot)) {
                            currentJackpot += 3;
                            Log.w("program1", "Player 2, Wrong answer");
                        }
                        else {

                            p2_Total.setText(String.valueOf(player2.getTotal()));
                            Log.w("program1", "Player 2, Correct answer");
                        }
                        currentPlayer = CurrPlayer.P1;
                    }
                    break;

                default:
                    Log.w("program1", "Default branch!");
                    break;

            }

//            Check if any user has won yet
            if(player1.getTotal() >= 20) {
                Intent intent = new Intent(getApplicationContext(), WinActivity.class);
                intent.putExtra("message_key", "Player 1 wins!");
                startActivity(intent);
            }
            if(player2.getTotal() >= 20) {
                Intent intent = new Intent(getApplicationContext(), WinActivity.class);
                intent.putExtra("message_key", "Player 2 wins!");
                startActivity(intent);
            }

//            ******************************

            if(currentPlayer == CurrPlayer.P1) currentPlayerView.setText("P1");
            else currentPlayerView.setText("P2");
            jackpot.setText(String.valueOf(currentJackpot));
            problem.setText("");
            userInput.setText("");
            dieImage.setImageResource(R.drawable.die_random);
        }
    }
}