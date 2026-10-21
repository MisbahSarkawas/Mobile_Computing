package com.example.horizontalbar_progress;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button btnStart;
    TextView txtProgress;

    int progress = 0;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect Java with activity_main.xml
        setContentView(R.layout.activity_main);

        // Connect XML components with Java
        progressBar = findViewById(R.id.progressBar);
        btnStart = findViewById(R.id.btnStart);
        txtProgress = findViewById(R.id.txtProgress);

        // Start button click
        btnStart.setOnClickListener(v -> {

            // Start from 0%
            progress = 0;

            progressBar.setProgress(0);
            txtProgress.setText("0%");

            // Start progress
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {

                    if (progress <= 100) {

                        // Update ProgressBar
                        progressBar.setProgress(progress);

                        // Update percentage
                        txtProgress.setText(progress + "%");

                        // Check completion
                        if (progress == 100) {

                            txtProgress.setText("Process Completed");

                            return;
                        }

                        // Increase progress by 10
                        progress = progress + 10;

                        // Wait 500 milliseconds
                        handler.postDelayed(this, 500);
                    }
                }

            }, 500);
        });
    }
}