package com.example.child_calculator;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Play extends AppCompatActivity {
    VideoView bgAnimation;


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
        bgAnimation = findViewById(R.id.animplay);
        Uri videouri = Uri.parse("android resource //" + getPackageName() + R.raw.animplay);
        bgAnimation.setVideoURI(videouri);
        bgAnimation.start();

        bgAnimation.setOnPreparedListener(mp->{
            mp.setLooping(true);
        });
    }
}