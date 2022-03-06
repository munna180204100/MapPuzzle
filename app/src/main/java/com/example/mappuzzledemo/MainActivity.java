package com.example.mappuzzledemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static com.example.mappuzzledemo.R.id.howtoplay;

public class MainActivity extends AppCompatActivity {

    private Button playButton, HowToPlayButton, RecordButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = (Button) findViewById(R.id.play);
        HowToPlayButton = (Button) findViewById(howtoplay);
        RecordButton = (Button) findViewById(R.id.record);







        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PuzzlePage.class);
                startActivity(intent);
            }
        });

        HowToPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Instructions.class);
                startActivity(i);
            }
        });

        RecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RecordActivity3.class);
                startActivity(i);
            }
        });
    }

    public void onBackPressed(){
        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("You want to Exit??")
               .setMessage("Are You Sure??")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No",null)
                .setCancelable(false);
        AlertDialog alert=builder.create();
        alert.show();
    }


}
