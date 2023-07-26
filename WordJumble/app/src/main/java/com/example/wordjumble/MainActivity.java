package com.example.wordjumble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int highScore;
    int currentScore=0;
    public static final String filename = "sharedprefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer media = MediaPlayer.create(MainActivity.this,R.raw.startgame);
        media.start();

        EditText edtWord = findViewById(R.id.edtWord);
        EditText edtClue = findViewById(R.id.edtClue);
        TextView  txtHighScore = findViewById(R.id.textView5);
        TextView txtCurScore = findViewById(R.id.txtCurScore);

        Button btnStart = findViewById(R.id.btnStart);

        SharedPreferences sharedPreferences = this.getSharedPreferences(filename,MODE_PRIVATE);
        final int highScore = sharedPreferences.getInt("high score",0);
        txtHighScore.setText("HIGH SCORE : "+highScore);
        final int currentScore = sharedPreferences.getInt("current score",0);
        txtCurScore.setText("CURRENT SCORE : "+currentScore);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = edtWord.getText().toString();
                String clue = edtClue.getText().toString();

                //TODO  format that dialog of gameover.


                if (word.length() == 0 || clue.length() == 0){
                    Toast.makeText(MainActivity.this, "Word or Clue is not filled", Toast.LENGTH_SHORT).show();
                }
                else if (word.length() > 16) {
                    Toast.makeText(MainActivity.this, "The word length should not exceed 16", Toast.LENGTH_SHORT).show();
                }
                else if (word.contains(" ")){
                    Toast.makeText(MainActivity.this, "The word should not contain space", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(MainActivity.this,GameActivity.class);
                    intent.putExtra("word",word);
                    intent.putExtra("clue",clue);
                    startActivity(intent);
                }
            }
        });


    }
}