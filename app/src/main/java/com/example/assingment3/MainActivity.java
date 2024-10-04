package com.example.assingment3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, phoneEditText;
    private Spinner feedbackSpinner;
    private Button submitButton;
    private LinearLayout inputLayout, outputLayout;
    private TextView outputText;

    private String name, email, phone, feedback;

    private Pattern namePattern = Pattern.compile("^[a-zA-Z\\s._]+$");
    private Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    private Pattern phonePattern = Pattern.compile("^[0-9]{10,15}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.num);
        feedbackSpinner = findViewById(R.id.spinner);
        submitButton = findViewById(R.id.submit);
        inputLayout = findViewById(R.id.inputLayout);
        outputLayout = findViewById(R.id.outputLayout);
        outputText = findViewById(R.id.outputText);

        // Change "Department" to feedback options
        String[] feedbackOptions = new String[]{"Select Feedback", "Poor", "Good", "Very Good", "Excellent"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, feedbackOptions);
        feedbackSpinner.setAdapter(spinnerAdapter);

        feedbackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                feedback = feedbackSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                feedback = "Select Feedback";
            }
        });

        submitButton.setOnClickListener(v -> {
            name = nameEditText.getText().toString();
            email = emailEditText.getText().toString();
            phone = phoneEditText.getText().toString();

            if (validateInputs()) {
                inputLayout.setVisibility(View.GONE);
                outputLayout.setVisibility(View.VISIBLE);

                String output = "Name: " + name + "\nEmail: " + email + "\nMobile Number: " + phone + "\nFeedback: " + feedback;
                outputText.setText(output);
            }
        });
    }

    private boolean validateInputs() {
        if (name.isEmpty()) {
            nameEditText.setError("Name cannot be empty");
            nameEditText.requestFocus();
            return false;
        } else if (!namePattern.matcher(name).matches()) {
            nameEditText.setError("Name can only contain alphabets, spaces, periods, and underscores");
            nameEditText.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            emailEditText.setError("Email cannot be empty");
            emailEditText.requestFocus();
            return false;
        } else if (!emailPattern.matcher(email).matches()) {
            emailEditText.setError("Invalid email format");
            emailEditText.requestFocus();
            return false;
        }

        if (phone.isEmpty()) {
            phoneEditText.setError("Phone number cannot be empty");
            phoneEditText.requestFocus();
            return false;
        } else if (!phonePattern.matcher(phone).matches()) {
            phoneEditText.setError("Phone number must be 10 to 15 digits long");
            phoneEditText.requestFocus();
            return false;
        }

        if (Objects.equals(feedback, "Select Feedback")) {
            Toast.makeText(this, "Please select feedback", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
