package com.example.containerprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtName;
    Button btnSave;

    // Content Provider URI
    Uri uri = StudentProvider.CONTENT_URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // First XML
        setContentView(R.layout.activity_main);

        // Connect XML
        edtName = findViewById(R.id.edtName);
        btnSave = findViewById(R.id.btnSave);

        // Save button
        btnSave.setOnClickListener(v -> {

            String name =
                    edtName
                            .getText()
                            .toString()
                            .trim();

            // Check empty
            if (name.isEmpty()) {

                edtName.setError(
                        "Please enter student name"
                );

                return;
            }

            // Create ContentValues
            ContentValues values =
                    new ContentValues();

            values.put(
                    "name",
                    name
            );

            // Insert using ContentResolver
            Uri result =
                    getContentResolver().insert(
                            uri,
                            values
                    );

            if (result != null) {

                Toast.makeText(
                        MainActivity.this,
                        "Student Saved Successfully",
                        Toast.LENGTH_SHORT
                ).show();

                // Intent to second Activity
                Intent intent =
                        new Intent(
                                MainActivity.this,
                                DisplayActivity.class
                        );

                startActivity(intent);

                // Clear EditText
                edtName.setText("");

            } else {

                Toast.makeText(
                        MainActivity.this,
                        "Error while saving student",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}