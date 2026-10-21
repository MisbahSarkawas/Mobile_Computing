package com.example.firebasecurdop;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText edtName;
    EditText edtCourse;
    EditText edtRollNo;

    Button btnSave;
    Button btnDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.edtName);
        edtCourse = findViewById(R.id.edtCourse);
        edtRollNo = findViewById(R.id.edtRollNo);

        btnSave = findViewById(R.id.btnSave);
        btnDisplay = findViewById(R.id.btnDisplay);

        // SAVE STUDENT
        btnSave.setOnClickListener(v -> {

            String name =
                    edtName.getText().toString().trim();

            String course =
                    edtCourse.getText().toString().trim();

            String rollNo =
                    edtRollNo.getText().toString().trim();

            if (name.isEmpty() ||
                    course.isEmpty() ||
                    rollNo.isEmpty()) {

                Toast.makeText(
                        MainActivity.this,
                        "Please enter all details",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // ContentResolver
            ContentResolver resolver =
                    getContentResolver();

            ContentValues values =
                    new ContentValues();

            values.put("name", name);
            values.put("course", course);
            values.put("rollNo", rollNo);

            // ContentResolver → StudentProvider
            Uri result =
                    resolver.insert(
                            StudentProvider.CONTENT_URI,
                            values
                    );

            if (result != null) {

                Toast.makeText(
                        MainActivity.this,
                        "Student saved successfully!",
                        Toast.LENGTH_SHORT
                ).show();

                edtName.setText("");
                edtCourse.setText("");
                edtRollNo.setText("");

            } else {

                Toast.makeText(
                        MainActivity.this,
                        "Failed to save student",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // DISPLAY STUDENTS
        btnDisplay.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            DisplayActivity.class
                    );

            startActivity(intent);
        });
    }
}