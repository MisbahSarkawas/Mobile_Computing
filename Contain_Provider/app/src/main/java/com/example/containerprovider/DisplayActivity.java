package com.example.containerprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    TextView txtStudentData;

    // Same Content Provider URI
    Uri uri =
            StudentProvider.CONTENT_URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Second XML
        setContentView(
                R.layout.activity_display
        );

        txtStudentData =
                findViewById(
                        R.id.txtStudentData
                );

        // Access data
        displayStudentData();
    }

    private void displayStudentData() {

        // ContentResolver accesses ContentProvider
        Cursor cursor =
                getContentResolver().query(
                        uri,
                        null,
                        null,
                        null,
                        "_id ASC"
                );

        StringBuilder data =
                new StringBuilder();

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                do {

                    // Get ID
                    int id =
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow(
                                            "_id"
                                    )
                            );

                    // Get name
                    String name =
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow(
                                            "name"
                                    )
                            );

                    data.append("ID: ")
                            .append(id)
                            .append("\n");

                    data.append("Student Name: ")
                            .append(name)
                            .append("\n\n");

                } while (cursor.moveToNext());

            } else {

                data.append(
                        "No student data found"
                );
            }

            cursor.close();

        } else {

            data.append(
                    "Unable to access Content Provider"
            );
        }

        // Display data
        txtStudentData.setText(
                data.toString()
        );
    }
}