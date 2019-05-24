package com.lulu.androidpuzzle.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lulu.androidpuzzle.R;

public class MainActivity extends AppCompatActivity {

    private Button btnPciker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPciker = findViewById(R.id.btn_picker);
        btnPciker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PuzzlePickerActivity.class));
            }
        });
    }
}
