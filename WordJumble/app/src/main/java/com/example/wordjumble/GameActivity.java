package com.example.wordjumble;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class GameActivity extends AppCompatActivity {
    int life = 3;
    public static final String filename = "sharedprefs";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Random random = new Random();


        //Getting the word and clue from the Home page
        Intent intent = getIntent();
        String word = intent.getStringExtra("word");
        word=word.toUpperCase();
        int lenWord = word.length();
        String gWord = "";
        for (int i =0; i< lenWord;i++){
            gWord = gWord.concat("_ ");
        }
        final String clue = intent.getStringExtra("clue").toUpperCase();



        //Generating extra letters for grid
        char[] wor = word.toCharArray();

        ArrayList<String> keys = new ArrayList<>();

        for (int i =0;i< wor.length;i++){
            keys.add(Character.toString(wor[i]));
        }

        while (keys.size()!=16){
            int numb=random.nextInt(26)+65;
            char c = (char)numb;
            keys.add(Character.toString(c));
        }

        Collections.shuffle(keys);

            ArrayList<ImageView> hearts = new ArrayList<>();
        ImageView imgHeart1 = findViewById(R.id.imgHeart1);
        hearts.add(imgHeart1);
        ImageView imgHeart2 = findViewById(R.id.imgHeart2);
        hearts.add(imgHeart2);
        ImageView imgHeart3 = findViewById(R.id.imgHeart3);
        hearts.add(imgHeart3);

        //imgHeart1.setColorFilter(Color.argb(128,128,128,128));



        TextView txtDisplay = findViewById(R.id.txtDisplay);

        txtDisplay.setText(gWord);

        ArrayList<Button> buttons = new ArrayList<>();
        ArrayList<Button> activeBtn = new ArrayList<>();

        ImageButton btnShowClue = findViewById(R.id.btnShowClue);

        btnShowClue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showClueDialog(clue);
            }
        });

        //Initiating the buttons
        Button btnReset = findViewById(R.id.btnReset);
        Button btnCheck = findViewById(R.id.btnCheck);
        Button btn1 = findViewById(R.id.btn1);
        buttons.add(btn1);
        Button btn2 = findViewById(R.id.btn2);
        buttons.add(btn2);
        Button btn3 = findViewById(R.id.btn3);
        buttons.add(btn3);
        Button btn4 = findViewById(R.id.btn4);
        buttons.add(btn4);
        Button btn5 = findViewById(R.id.btn5);
        buttons.add(btn5);
        Button btn6 = findViewById(R.id.btn6);
        buttons.add(btn6);
        Button btn7 = findViewById(R.id.btn7);
        buttons.add(btn7);
        Button btn8 = findViewById(R.id.btn8);
        buttons.add(btn8);
        Button btn9 = findViewById(R.id.btn9);
        buttons.add(btn9);
        Button btn10 = findViewById(R.id.btn10);
        buttons.add(btn10);
        Button btn11 = findViewById(R.id.btn11);
        buttons.add(btn11);
        Button btn12 = findViewById(R.id.btn12);
        buttons.add(btn12);
        Button btn13 = findViewById(R.id.btn13);
        buttons.add(btn13);
        Button btn14 = findViewById(R.id.btn14);
        buttons.add(btn14);
        Button btn15 = findViewById(R.id.btn15);
        buttons.add(btn15);
        Button btn16 = findViewById(R.id.btn16);
        buttons.add(btn16);

        //Setting methods for grid buttons and assigning them values
        setBtnletters(buttons,keys);
        assignbtns(buttons,activeBtn,txtDisplay);

        //Setting method for reset button
        setbtnReset(btnReset,activeBtn,gWord,txtDisplay,buttons,keys);

        //Setting method for Check button
        setCheckbtn(btnCheck,txtDisplay,word,gWord,buttons,activeBtn,keys,hearts);


    }

    public void assignbtns(ArrayList<Button> btn, ArrayList<Button> activebtn,TextView txtDisplay){
        for (int i=0;i< btn.size();i++){
            Button button = btn.get(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation anim = AnimationUtils.loadAnimation(GameActivity.this,R.anim.fadein);
                    final MediaPlayer mediaplayer = MediaPlayer.create(GameActivity.this,R.raw.flick);
                    mediaplayer.start();
                    activebtn.add(button);
                    String letter = button.getText().toString();
                    button.setEnabled(false);
                    String word =txtDisplay.getText().toString();
                    word = word.replaceFirst("_ ",letter);
                    txtDisplay.setText(word);
                    button.startAnimation(anim);
                }
            });
        }

    }

    public void setBtnletters(ArrayList<Button> btn ,ArrayList<String> keys){
        for(int i=0; i<btn.size();i++){
            Button button = btn.get(i);
            button.setText(keys.get(i));
        }
    }

    public void setbtnReset(Button reset,ArrayList<Button> activebuttons, String gWord,TextView txtDisplay,ArrayList<Button> buttons,ArrayList<String> keys){
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = AnimationUtils.loadAnimation(GameActivity.this,R.anim.bounce);
                reset.startAnimation(anim);
                MediaPlayer mediaPlayer = MediaPlayer.create(GameActivity.this,R.raw.respawn);
                mediaPlayer.start();
                for(int i=0;i<activebuttons.size();i++){
                    Button btn = activebuttons.get(i);
                    btn.setEnabled(true);
                }
                txtDisplay.setText(gWord);
                Toast.makeText(GameActivity.this, "The chance is reset", Toast.LENGTH_SHORT).show();
                Collections.shuffle(keys);
                setBtnletters(buttons,keys);
            }
        });

    }

    public void setCheckbtn(Button btnCheck,TextView txtDisplay,String word,String gWord,ArrayList<Button> buttons,ArrayList<Button> activebuttons,ArrayList<String> keys,ArrayList<ImageView> hearts){
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim =AnimationUtils.loadAnimation(GameActivity.this,R.anim.bounce);
                btnCheck.startAnimation(anim);
                String userword = txtDisplay.getText().toString();
                if (!userword.contains("_")){
                    if (userword.equals(word)){
                        int points = 300 - (3-life)*100;
                        final MediaPlayer media = MediaPlayer.create(GameActivity.this,R.raw.successfullguess);
                        media.setVolume(100,100);
                        media.start();
                        Toast.makeText(GameActivity.this, "Hurray! You guessed it right", Toast.LENGTH_SHORT).show();
                        showGameOverDialog(points);
                    }
                    else {
                        Toast.makeText(GameActivity.this, "Oops It is a wrong guess", Toast.LENGTH_SHORT).show();
                        MediaPlayer media = MediaPlayer.create(GameActivity.this,R.raw.wrongguess);
                        media.start();
                        ImageView heart = hearts.get(hearts.size()-1);
                        heart.setColorFilter(Color.argb(128,128,128,128));
                        hearts.remove(hearts.size()-1);
                        life -= 1;
                        if (life == 0){
                            int points = 0;
                            MediaPlayer mediaPlayer = MediaPlayer.create(GameActivity.this,R.raw.fail);
                            mediaPlayer.start();
                            Toast.makeText(GameActivity.this, "GameOver", Toast.LENGTH_SHORT).show();
                            showGameOverDialog(points);
                        }
                        else {
                            for(int i=0;i<activebuttons.size();i++){
                                Button btn = activebuttons.get(i);
                                btn.setEnabled(true);
                            }
                            txtDisplay.setText(gWord);
                            Toast.makeText(GameActivity.this, "You have "+life+" lives remaining", Toast.LENGTH_SHORT).show();
                            Collections.shuffle(keys);
                            setBtnletters(buttons,keys);


                        }
                    }
                }
                else {
                    Toast.makeText(GameActivity.this, "Enter all the elements to check", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showClueDialog(String clue){
        Dialog dialog = new Dialog(GameActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.clue_dialog);

        TextView clueHead = dialog.findViewById(R.id.txtCluehead);
        TextView clueLine = dialog.findViewById(R.id.txtClueline);
        Button okay = dialog.findViewById(R.id.btnOkay);

        clueLine.setText(clue);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showGameOverDialog(int points){
        Dialog dialog = new Dialog(GameActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.gameover_dialog);

        TextView txtpoints = dialog.findViewById(R.id.textView2);

        Button btnHome = dialog.findViewById(R.id.btnHome);

        txtpoints.setText("Your Score: "+points);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this,MainActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences(filename,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (points == 0){
                    editor.putInt("current score",points);
                    editor.apply();
                }

                else{
                    int cscore = sharedPreferences.getInt("current score",0);
                    editor.putInt("current score",cscore+points);
                    editor.apply();
                    int hscore = sharedPreferences.getInt("high score",0);
                    if((cscore+points) > hscore) {
                        editor.putInt("high score", hscore + points);
                        editor.apply();
                    }
                }

                startActivity(intent);
            }
        });

        dialog.show();
    }


}

