package com.example.firebasecurdop;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayActivity extends AppCompatActivity {

    TextView txtStudentData;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display);

        txtStudentData = findViewById(R.id.txtStudentData);

        databaseReference =
                FirebaseDatabase.getInstance()
                        .getReference("students");

        displayStudents();
    }

    private void displayStudents() {

        databaseReference.addValueEventListener(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(
                            DataSnapshot snapshot) {

                        StringBuilder data =
                                new StringBuilder();

                        if (!snapshot.exists()) {

                            data.append("No students found.");

                        } else {

                            for (DataSnapshot child :
                                    snapshot.getChildren()) {

                                Student student =
                                        child.getValue(Student.class);

                                if (student != null) {

                                    data.append("Student Name : ")
                                            .append(student.getName())
                                            .append("\n");

                                    data.append("Course : ")
                                            .append(student.getCourse())
                                            .append("\n");

                                    data.append("Roll No : ")
                                            .append(student.getRollNo())
                                            .append("\n");

                                    data.append("------------------------\n\n");
                                }
                            }
                        }

                        txtStudentData.setText(data.toString());
                    }

                    @Override
                    public void onCancelled(
                            DatabaseError error) {

                        txtStudentData.setText(
                                "Error: " + error.getMessage()
                        );
                    }
                });
    }
}