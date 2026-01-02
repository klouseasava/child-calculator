package com.example.child_calculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class Play extends AppCompatActivity {

    VideoView bgAnimation;
    EditText input;

    int questionCount = 0;
    int totalQuestions = 10;
    int score = 0;

    String currentQuestion;
    double currentAnswer;

    ArrayList<String> questionsList = new ArrayList<>();
    ArrayList<String> answersList = new ArrayList<>();

    double firstValue = 0;
    String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Video background
        bgAnimation = findViewById(R.id.animplay);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.animplay);
        bgAnimation.setVideoURI(videoUri);
        bgAnimation.start();
        bgAnimation.setOnPreparedListener(mp -> mp.setLooping(true));

        // Input
        input = findViewById(R.id.input);

        // Number buttons
        setNumberClick(R.id.zero, "0");
        setNumberClick(R.id.one, "1");
        setNumberClick(R.id.two, "2");
        setNumberClick(R.id.three, "3");
        setNumberClick(R.id.four, "4");
        setNumberClick(R.id.five, "5");
        setNumberClick(R.id.six, "6");
        setNumberClick(R.id.seven, "7");
        setNumberClick(R.id.eight, "8");
        setNumberClick(R.id.nine, "9");

        // Operators
        findViewById(R.id.addition).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.subtraction).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.multiplication).setOnClickListener(v -> setOperator("*"));
        findViewById(R.id.division).setOnClickListener(v -> setOperator("/"));

        // Equals
        findViewById(R.id.equal).setOnClickListener(v -> checkAnswer());

        // Delete
        findViewById(R.id.delete).setOnClickListener(v -> {
            String text = input.getText().toString();
            if (!text.isEmpty()) input.setText(text.substring(0, text.length() - 1));
        });

        // Cancel
        findViewById(R.id.cancel).setOnClickListener(v -> {
            input.setText("");
            firstValue = 0;
            operator = "";
        });

        // Start first question
        nextQuestion();
    }

    private void setNumberClick(int id, String value) {
        findViewById(id).setOnClickListener(v -> input.append(value));
    }

    private void setOperator(String op) {
        if (!input.getText().toString().isEmpty()) {
            firstValue = Double.parseDouble(input.getText().toString());
            operator = op;
            input.setText("");
        }
    }

    private void checkAnswer() {
        if (input.getText().toString().isEmpty()) return;

        double answer = Double.parseDouble(input.getText().toString());
        if (Math.abs(answer - currentAnswer) < 0.001) { // correct
            score++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong! Answer: " + currentAnswer, Toast.LENGTH_SHORT).show();
        }

        // Store question and correct answer
        questionsList.add(currentQuestion);
        answersList.add(String.valueOf(currentAnswer));

        questionCount++;
        if (questionCount < totalQuestions) {
            nextQuestion();
        } else {
            showResults();
        }

        input.setText("");
        operator = "";
        firstValue = 0;
    }

    private void nextQuestion() {
        Random rand = new Random();
        int a = rand.nextInt(10) + 1;
        int b = rand.nextInt(10) + 1;
        int c = rand.nextInt(10) + 1;
        char[] ops = {'+', '-', '*', '/'};
        char op1 = ops[rand.nextInt(4)];
        char op2 = ops[rand.nextInt(4)];

        currentQuestion = a + " " + op1 + " " + b + " " + op2 + " " + c;
        currentAnswer = evaluateExpression(a, op1, b, op2, c);

        Toast.makeText(this, "Question " + (questionCount + 1) + ": " + currentQuestion, Toast.LENGTH_LONG).show();
    }

    private double evaluateExpression(int a, char op1, int b, char op2, int c) {
        double first;
        switch (op1) {
            case '+': first = a + b; break;
            case '-': first = a - b; break;
            case '*': first = a * b; break;
            case '/': first = b != 0 ? a / (double)b : 0; break;
            default: first = 0;
        }

        double result;
        switch (op2) {
            case '+': result = first + c; break;
            case '-': result = first - c; break;
            case '*': result = first * c; break;
            case '/': result = c != 0 ? first / (double)c : 0; break;
            default: result = first;
        }

        return result;
    }

    private void showResults() {
        Intent intent = new Intent(this, Results.class);
        intent.putExtra("SCORE", score);
        intent.putExtra("TOTAL", totalQuestions);
        intent.putStringArrayListExtra("QUESTIONS", questionsList);
        intent.putStringArrayListExtra("ANSWERS", answersList);
        startActivity(intent);
        finish();
    }
}
