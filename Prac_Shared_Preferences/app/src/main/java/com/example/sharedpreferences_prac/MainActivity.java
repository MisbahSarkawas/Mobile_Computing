package com.example.sharedpreferences_prac;


// Import required Android classes
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

// Import AppCompatActivity
import androidx.appcompat.app.AppCompatActivity;

// MainActivity is the main screen of the application
public class MainActivity extends AppCompatActivity {

    // Declare UI components
    EditText edtName;
    Button btnSave, btnDisplay;
    TextView txtResult;

    // onCreate() is called when the Activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect the Java Activity with activity_main.xml
        setContentView(R.layout.activity_main);

        // Connect EditText from XML with Java variable
        edtName = findViewById(R.id.edtName);

        // Connect Save button from XML
        btnSave = findViewById(R.id.btnSave);

        // Connect Display button from XML
        btnDisplay = findViewById(R.id.btnDisplay);

        // Connect TextView from XML
        txtResult = findViewById(R.id.txtResult);


        // ------------------------------------------------
        // SAVE DATA
        // ------------------------------------------------

        // Execute this code when Save button is clicked
        btnSave.setOnClickListener(v -> {

            // Get text entered by the user
            String name = edtName.getText().toString();

            // Open/Create SharedPreferences named "StudentData"
            getSharedPreferences("StudentData", MODE_PRIVATE)

                    // Open editor to make changes
                    .edit()

                    // Store the name using key "student_name"
                    .putString("student_name", name)

                    // Save the changes
                    .apply();

            // Display confirmation message
            txtResult.setText("Data Saved");
        });


        // ------------------------------------------------
        // RETRIEVE DATA
        // ------------------------------------------------

        // Execute this code when Display button is clicked
        btnDisplay.setOnClickListener(v -> {

            // Open the same SharedPreferences
            // and retrieve the value stored with key "student_name"
            String name = getSharedPreferences(
                    "StudentData",       // SharedPreferences name
                    MODE_PRIVATE         // Keep data private to this app
            ).getString(
                    "student_name",      // Key used while saving
                    "No Data Found"      // Default value if key does not exist
            );

            // Display the retrieved name
            txtResult.setText("Student Name: " + name);
        });
    }
}
