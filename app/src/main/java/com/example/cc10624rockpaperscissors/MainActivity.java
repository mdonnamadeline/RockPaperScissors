package com.example.cc10624rockpaperscissors;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView lblround, lblScoreYou, lblScoreCom;
    private ImageView imgYou, imgCom;
    private RadioButton radioRock, radioPaper, radioScissors;
    private Button btnReset, btnBet, btnClose;
    private RadioGroup radioGroup;

    private int[] scores = {0, 0};
    int you = 0;
    int round = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblScoreYou = findViewById(R.id.lblScoreYou);
        lblScoreCom = findViewById(R.id.lblScoreBot);
        imgYou = findViewById(R.id.imgYou);
        imgCom = findViewById(R.id.imgBot);
        radioRock = findViewById(R.id.radioRock);
        radioPaper = findViewById(R.id.radioPaper);
        radioScissors = findViewById(R.id.radioScissors);
        btnReset = findViewById(R.id.btnReset);
        btnBet = findViewById(R.id.btnBet);
        btnClose = findViewById(R.id.btnClose);
        lblround = findViewById(R.id.lblRound);
        radioGroup = findViewById(R.id.radioGroup);

        imgYou.setImageResource(R.drawable.rock_left);
        imgCom.setImageResource(R.drawable.rock_right);

        lblScoreYou.setText("Score: " + scores[0]);
        lblScoreCom.setText("Score: " + scores[1]);

        lblround.setText("Round: " + round);

        btnBet.setOnClickListener(v -> {
            // Set the images to rock
            imgYou.setImageResource(R.drawable.rock_left);
            imgCom.setImageResource(R.drawable.rock_right);

            // Create an animator for the 'imgYou' ImageView
            ObjectAnimator imgYouAnimator = ObjectAnimator.ofPropertyValuesHolder(
                    imgYou,
                    PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                    PropertyValuesHolder.ofFloat("scaleY", 1.2f));
            imgYouAnimator.setDuration(500);
            imgYouAnimator.setRepeatCount(3);
            imgYouAnimator.setRepeatMode(ValueAnimator.REVERSE);

            // Create an animator for the 'imgCom' ImageView
            ObjectAnimator imgComAnimator = ObjectAnimator.ofPropertyValuesHolder(
                    imgCom,
                    PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                    PropertyValuesHolder.ofFloat("scaleY", 1.2f));
            imgComAnimator.setDuration(500);
            imgComAnimator.setRepeatCount(3);
            imgComAnimator.setRepeatMode(ValueAnimator.REVERSE);

            // Start the animations
            imgYouAnimator.start();
            imgComAnimator.start();

            imgYouAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    // Reveal the bet of the player and the bot after the animation has ended
                    if (radioRock.isChecked()) {
                        you = 0;
                        imgYou.setImageResource(R.drawable.rock_left);
                    } else if (radioPaper.isChecked()) {
                        you = 1;
                        imgYou.setImageResource(R.drawable.paper_left);
                    } else if (radioScissors.isChecked()) {
                        you = 2;
                        imgYou.setImageResource(R.drawable.scissor_left);
                    }

                    int com = new Random().nextInt(3);
                    if (com == 0) {
                        imgCom.setImageResource(R.drawable.rock_right);
                    } else if (com == 1) {
                        imgCom.setImageResource(R.drawable.paper_right);
                    } else if (com == 2) {
                        imgCom.setImageResource(R.drawable.scissor_right);
                    }

                    if (you == com) {
                        showToast("Draw");
                    } else if (you == 0 && com == 1 || you == 1 && com == 2 || you == 2 && com == 0) {
                        scores[1]++;
                        showToast("You lose");
                    } else {
                        scores[0]++;
                        showToast("You win");
                    }

                    lblScoreYou.setText("Score: " + scores[0]);
                    lblScoreCom.setText("Score: " + scores[1]);

                    if (scores[0] == 10) {
                        showMessage("Congratulations!", "You won the game!");
                        btnBet.setEnabled(false);
                    } else if (scores[1] == 10) {
                        showMessage("Game Over", "You lost the game.");
                        btnBet.setEnabled(false);
                    } else {
                        round++;
                        lblround.setText("Round: " + round);
                        btnBet.setEnabled(true);
                    }

                    // Clear the radio button selection for the next round
                    radioGroup.clearCheck();
                }
            });
        });

        btnReset.setOnClickListener(v -> {
            scores[0] = 0;
            scores[1] = 0;
            round = 1;
            lblScoreYou.setText("You: " + scores[0]);
            lblScoreCom.setText("Com: " + scores[1]);
            lblround.setText("Round: " + round);

            if (scores[0] == 10) {
                showMessage("Congratulations!", "You won the game!");
                btnBet.setEnabled(false);
            } else if (scores[1] == 10) {
                showMessage("Game Over", "You lost the game.");
                btnBet.setEnabled(false);
            } else {
                btnBet.setEnabled(true);
            }

            // Clear the radio button selection for the next round
            radioGroup.clearCheck();
        });
        btnClose.setOnClickListener(v -> {
            finish();
        });
    }


    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void showMessage(String title, String message) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.btn_star)
                .show();
    }
}