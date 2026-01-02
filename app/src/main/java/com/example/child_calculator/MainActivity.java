package com.example.child_calculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.VideoView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button startQuiz;
    VideoView bgAnimation;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startQuiz = findViewById(R.id.startQuiz);
        bgAnimation = findViewById(R.id.backgroundanim);
        radioGroup = findViewById(R.id.rd_radio_group);

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.backgroundanim);
        bgAnimation.setVideoURI(videoUri);
        bgAnimation.start();
        bgAnimation.setOnPreparedListener(mp -> mp.setLooping(true));

        startQuiz.setOnClickListener(v -> {

            int selectedId = radioGroup.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(this, "Please select a mode", Toast.LENGTH_SHORT).show();
                return;
            }

            String mode = "";

            if (selectedId == R.id.easy) mode = "EASY";
            else if (selectedId == R.id.medium) mode = "MEDIUM";
            else if (selectedId == R.id.hard) mode = "HARD";

            Intent intent = new Intent(MainActivity.this, Play.class);
            intent.putExtra("MODE", mode);
            startActivity(intent);
        });
    }
}
