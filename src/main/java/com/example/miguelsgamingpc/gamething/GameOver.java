package com.example.miguelsgamingpc.gamething;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;

public class GameOver extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);




        Intent intent = getIntent();
        String text = intent.getStringExtra(GameView.EXTRA_SCORE);

        TextView scoreV =  findViewById(R.id.scoreS);
        TextView highScoreV = findViewById(R.id.highScoreS);
        scoreV.setText(text);

        Button back = findViewById(R.id.backButton);
        Button replay = findViewById(R.id.reverseButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
        replay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startGameOver();
            }
            });

        SharedPreferences prefs = this.getSharedPreferences("zapdGameKey", Context.MODE_PRIVATE);
        int highScore = prefs.getInt("HIGH_SCORE", 0);
        int score = Integer.parseInt(text);


        if(score > highScore) {
            highScoreV.setText(score + "");

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("HIGH_SCORE", score);
            editor.commit();
        }else{
            highScoreV.setText(highScore + "");
        }



    }

    public void openMainActivity(){
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
        finish();
    }

    public void startGameOver(){
        Intent replay = new Intent(this, StartGame.class);
        startActivity(replay);
        finish();
    }






}
