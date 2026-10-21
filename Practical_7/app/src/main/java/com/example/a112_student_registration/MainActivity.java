package com.example.a112_student_registration;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtEmail, edtMobile;
    RadioGroup radioGender;
    CheckBox checkAndroid, checkJava;
    Spinner spinnerCourse;
    Button btnRegister;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect XML components with Java
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtMobile = findViewById(R.id.edtMobile);

        radioGender = findViewById(R.id.radioGender);

        checkAndroid = findViewById(R.id.checkAndroid);
        checkJava = findViewById(R.id.checkJava);

        spinnerCourse = findViewById(R.id.spinnerCourse);

        btnRegister = findViewById(R.id.btnRegister);
        txtResult = findViewById(R.id.txtResult);

        // Course list
        String[] courses = {
                "Select Course",
                "MCA",
                "MBA",
                "BCA",
                "BBA"
        };

        // Create Spinner Adapter
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        courses
                );

        // Set adapter to Spinner
        spinnerCourse.setAdapter(adapter);

        // Register button
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Get student details
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String mobile = edtMobile.getText().toString();

                // Get gender
                int selectedId =
                        radioGender.getCheckedRadioButtonId();

                String gender = "Not Selected";

                if (selectedId != -1) {

                    RadioButton selectedRadio =
                            findViewById(selectedId);

                    gender = selectedRadio.getText().toString();
                }

                // Get skills
                String skills = "";

                if (checkAndroid.isChecked()) {
                    skills += "Android ";
                }

                if (checkJava.isChecked()) {
                    skills += "Java ";
                }

                if (skills.isEmpty()) {
                    skills = "No Skills Selected";
                }

                // Get course
                String course =
                        spinnerCourse.getSelectedItem().toString();

                // Create registration result
                String result =
                        "REGISTRATION SUCCESSFUL\n\n" +
                                "Name: " + name + "\n" +
                                "Email: " + email + "\n" +
                                "Mobile: " + mobile + "\n" +
                                "Gender: " + gender + "\n" +
                                "Skills: " + skills + "\n" +
                                "Course: " + course;

                // Display result
                txtResult.setText(result);
            }
        });
    }
}