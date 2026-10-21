package com.example.circularhorizontal_bar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ProgressRingView progressBar;
    Button btnStart;
    TextView txtProgress;

    int progress = 0;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect Java with XML
        setContentView(R.layout.activity_main);

        // Connect XML components
        progressBar = findViewById(R.id.progressBar);
        btnStart = findViewById(R.id.btnStart);
        txtProgress = findViewById(R.id.txtProgress);

        // Initial message
        txtProgress.setText("Order Processing...");

        // Start button
        btnStart.setOnClickListener(v -> {

            // Start again from 0
            progress = 0;

            progressBar.setProgress(0);

            txtProgress.setText("Order Processing...");

            // Disable button while running
            btnStart.setEnabled(false);

            // Start progress
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {

                    // Set current progress
                    progressBar.setProgress(progress);

                    // Check if progress completed
                    if (progress >= 100) {

                        progressBar.setProgress(100);

                        txtProgress.setText("Process Completed");

                        // Enable button again
                        btnStart.setEnabled(true);

                        return;
                    }

                    // Increase progress by 5
                    progress = progress + 5;

                    // Run again after 100 milliseconds
                    handler.postDelayed(this, 100);
                }

            }, 100);
        });
    }


    // =====================================================
    // CUSTOM CIRCULAR PROGRESS RING
    // =====================================================

    public static class ProgressRingView extends View {

        // Paint for background circle
        Paint backgroundPaint;

        // Paint for progress circle
        Paint progressPaint;

        // Paint for percentage text
        Paint textPaint;

        // Rectangle used to draw the arc
        RectF rectF;

        // Current progress
        int progress = 0;


        // Constructor used by Android
        public ProgressRingView(Context context) {
            super(context);
            initialize();
        }


        // Constructor used when created from XML
        public ProgressRingView(Context context, AttributeSet attrs) {
            super(context, attrs);
            initialize();
        }


        // Constructor
        public ProgressRingView(
                Context context,
                AttributeSet attrs,
                int defStyleAttr) {

            super(context, attrs, defStyleAttr);
            initialize();
        }


        // Initialize Paint objects
        private void initialize() {

            // Background ring
            backgroundPaint = new Paint();
            backgroundPaint.setAntiAlias(true);
            backgroundPaint.setStyle(Paint.Style.STROKE);
            backgroundPaint.setStrokeWidth(22);
            backgroundPaint.setColor(0xFFE0D5E5);
            backgroundPaint.setStrokeCap(Paint.Cap.ROUND);


            // Progress ring
            progressPaint = new Paint();
            progressPaint.setAntiAlias(true);
            progressPaint.setStyle(Paint.Style.STROKE);
            progressPaint.setStrokeWidth(22);
            progressPaint.setColor(0xFF8E24AA);
            progressPaint.setStrokeCap(Paint.Cap.ROUND);


            // Percentage text
            textPaint = new Paint();
            textPaint.setAntiAlias(true);
            textPaint.setColor(0xFFD81B60);
            textPaint.setTextSize(45);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTypeface(
                    android.graphics.Typeface.DEFAULT_BOLD
            );


            rectF = new RectF();
        }


        @Override
        protected void onDraw(Canvas canvas) {

            super.onDraw(canvas);

            // Find center of the view
            float centerX = getWidth() / 2f;
            float centerY = getHeight() / 2f;

            // Calculate circle radius
            float radius =
                    Math.min(getWidth(), getHeight()) / 2f - 25;


            // Set rectangle for arc
            rectF.set(
                    centerX - radius,
                    centerY - radius,
                    centerX + radius,
                    centerY + radius
            );


            // ==========================================
            // Draw empty/background circle
            // ==========================================

            canvas.drawCircle(
                    centerX,
                    centerY,
                    radius,
                    backgroundPaint
            );


            // ==========================================
            // Calculate progress angle
            // ==========================================

            float sweepAngle =
                    (progress / 100f) * 360f;


            // ==========================================
            // Draw progress ring
            // ==========================================

            canvas.drawArc(
                    rectF,
                    -90,
                    sweepAngle,
                    false,
                    progressPaint
            );


            // ==========================================
            // Draw percentage in center
            // ==========================================

            Paint.FontMetrics fontMetrics =
                    textPaint.getFontMetrics();

            float textY =
                    centerY -
                            (fontMetrics.ascent + fontMetrics.descent) / 2;


            canvas.drawText(
                    progress + "%",
                    centerX,
                    textY,
                    textPaint
            );
        }


        // Set progress
        public void setProgress(int progress) {

            // Keep value between 0 and 100
            if (progress < 0) {
                progress = 0;
            }

            if (progress > 100) {
                progress = 100;
            }

            this.progress = progress;

            // Redraw the view
            invalidate();
        }
    }
}