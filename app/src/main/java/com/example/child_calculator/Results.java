package com.example.child_calculator;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get data from Play activity
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);
        int total = intent.getIntExtra("TOTAL", 10);
        ArrayList<String> questions = intent.getStringArrayListExtra("QUESTIONS");
        ArrayList<String> correctAnswers = intent.getStringArrayListExtra("ANSWERS");
        ArrayList<String> userAnswers = intent.getStringArrayListExtra("USER_ANSWERS");

        // Grade TextView
        TextView passText = findViewById(R.id.pass);
        float percent = (score * 100f) / total;
        String gradeMessage;
        if (percent >= 90) gradeMessage = "Excellent!";
        else if (percent >= 70) gradeMessage = "Good Job!";
        else if (percent >= 50) gradeMessage = "Not Bad!";
        else gradeMessage = "Try Again!";
        passText.setText(gradeMessage + " You scored " + score + "/" + total);

        // ProgressBar animation
        ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setMax(total);
        progressBar.setProgress(0);
        progressBar.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 50));
        ((LinearLayout) findViewById(R.id.main)).addView(progressBar, 1);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, score);
        animation.setDuration(1500);
        animation.start();

        // ScrollView for questions
        ScrollView scrollView = new ScrollView(this);
        LinearLayout questionLayout = new LinearLayout(this);
        questionLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(questionLayout);

        // Display each question and correct answer, highlighting wrong answers
        for (int i = 0; i < questions.size(); i++) {
            TextView tv = new TextView(this);
            String displayText = (i + 1) + ". " + questions.get(i)
                    + " = " + correctAnswers.get(i)
                    + " | Your answer: " + userAnswers.get(i);
            tv.setText(displayText);
            tv.setTextSize(22f);
            tv.setPadding(10, 10, 10, 10);

            // Color code based on correctness
            if (correctAnswers.get(i).equals(userAnswers.get(i))) {
                tv.setTextColor(Color.parseColor("#388E3C")); // Green for correct
            } else {
                tv.setTextColor(Color.parseColor("#D32F2F")); // Red for wrong
            }

            questionLayout.addView(tv);
        }

        ((LinearLayout) findViewById(R.id.main)).addView(scrollView);

        // Back and Done buttons
        Button back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());

        Button done = findViewById(R.id.done);
        done.setOnClickListener(v -> finish());
    }
}
