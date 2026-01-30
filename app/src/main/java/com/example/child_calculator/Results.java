package com.example.child_calculator;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Results extends AppCompatActivity {

    VideoView videoView;

    private void playSound(int resId) {
        MediaPlayer mp = MediaPlayer.create(this, resId);
        mp.setOnCompletionListener(MediaPlayer::release);
        mp.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // 🎥 LOOPING VIDEO
        videoView = findViewById(R.id.resultsvideo);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.result_anim);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.setVolume(0f,0f);
        });
        videoView.start();

        // DATA
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE",0);
        int total = intent.getIntExtra("TOTAL",10);

        ArrayList<String> questions = intent.getStringArrayListExtra("QUESTIONS");
        ArrayList<String> correctAnswers = intent.getStringArrayListExtra("ANSWERS");
        ArrayList<String> userAnswers = intent.getStringArrayListExtra("USER_ANSWERS");

        float percent = (score*100f)/total;

        // PASS TEXT
        TextView pass = findViewById(R.id.pass);
        pass.setText("Score: "+score+"/"+total);

        LinearLayout content = findViewById(R.id.contentLayout);

        if(percent>=50){
            content.setBackgroundColor(Color.parseColor("#E8F5E9"));
            playSound(R.raw.win);
        } else {
            content.setBackgroundColor(Color.parseColor("#FFEBEE"));
            playSound(R.raw.fail);
        }

        // STARS
        int stars = percent>=90?3:percent>=70?2:percent>=50?1:0;
        int[] starIds={R.id.star1,R.id.star2,R.id.star3};

        for(int i=0;i<3;i++){
            ImageView s=findViewById(starIds[i]);
            s.setImageResource(i<stars?R.drawable.star_full:R.drawable.star_empty);
        }

        // TROPHY
        if(percent>=80){
            ImageView trophy=findViewById(R.id.trophy);
            trophy.setVisibility(View.VISIBLE);
            Animation bounce= AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
            bounce.setRepeatCount(Animation.INFINITE);
            bounce.setRepeatMode(Animation.REVERSE);
            trophy.startAnimation(bounce);
        }

        // ANIMAL
        ImageView animal=findViewById(R.id.animal);
        animal.setImageResource(percent>=50?R.drawable.animal_happy:R.drawable.animal_sad);

        // PROGRESS BAR
        ProgressBar pb=findViewById(R.id.progressBar);
        pb.setMax(total);
        ObjectAnimator.ofInt(pb,"progress",0,score).setDuration(1200).start();

        // SCORE BAR
        View bar=findViewById(R.id.scoreBar);
        bar.post(()->{
            int w=((View)bar.getParent()).getWidth();
            int target=(int)(w*percent/100f);
            bar.getLayoutParams().width=target;
            bar.requestLayout();
        });

        // QUESTION LIST
        LinearLayout container=findViewById(R.id.questionContainer);
        if(questions!=null){
            for(int i=0;i<questions.size();i++){
                TextView tv=new TextView(this);
                tv.setTextSize(18);
                tv.setPadding(8,8,8,8);
                String txt=(i+1)+". "+questions.get(i)+" = "+correctAnswers.get(i)
                        +" | Your: "+userAnswers.get(i);
                tv.setText(txt);
                tv.setTextColor(
                        correctAnswers.get(i).equals(userAnswers.get(i))
                                ? Color.parseColor("#2E7D32")
                                : Color.parseColor("#C62828")
                );
                container.addView(tv);
            }
        }

        // BUTTONS
        findViewById(R.id.back).setOnClickListener(v->finish());
        findViewById(R.id.done).setOnClickListener(v->finish());
    }

    @Override protected void onPause(){
        super.onPause();
        if(videoView!=null) videoView.pause();
    }

    @Override protected void onResume(){
        super.onResume();
        if(videoView!=null) videoView.start();
    }
}
