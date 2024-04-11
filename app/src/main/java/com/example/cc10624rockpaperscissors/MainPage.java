package com.example.cc10624rockpaperscissors;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainPage extends AppCompatActivity {
    private ImageView imgStart;
    private TextView lblTap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imgStart = findViewById(R.id.imgStart);
        imgStart.setImageResource(R.drawable.start);

        lblTap = findViewById(R.id.lblTap);

        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(lblTap, "scaleX", 1.0f, 1.2f, 1.0f);
        scaleXAnimation.setDuration(1000); // duration in milliseconds
        scaleXAnimation.setRepeatCount(ObjectAnimator.INFINITE);

        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(lblTap, "scaleY", 1.0f, 1.2f, 1.0f);
        scaleYAnimation.setDuration(1000); // duration in milliseconds
        scaleYAnimation.setRepeatCount(ObjectAnimator.INFINITE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation);
        animatorSet.start();

        ConstraintLayout rootView = findViewById(R.id.rootView);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}