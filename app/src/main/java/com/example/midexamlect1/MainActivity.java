package com.example.midexamlect1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etStudentName, etStudentId;
    Spinner spMajor;
    Button btnAddStudent, btnViewStudents;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etStudentName = findViewById(R.id.etStudentName);
        etStudentId = findViewById(R.id.etStudentId);
        spMajor = findViewById(R.id.spMajor);
        btnAddStudent = findViewById(R.id.btnAddStudent);
        btnViewStudents = findViewById(R.id.btnViewStudents);

        dbHelper = new DBHelper(this);

        String[] majors = {
                "Informatics",
                "Information Systems"
        };

        ArrayAdapter<String> majorAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                majors
        );

        majorAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spMajor.setAdapter(majorAdapter);

        btnAddStudent.setOnClickListener(v -> {
            String name = etStudentName.getText().toString().trim();
            String studentId = etStudentId.getText().toString().trim();
            String major = spMajor.getSelectedItem().toString();

            if (name.isEmpty() || studentId.isEmpty()) {
                Toast.makeText(this,
                        "Please fill in all fields",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            StudentModel student =
                    new StudentModel(studentId, name, major);

            boolean success = dbHelper.addStudent(student);

            if (success) {
                Toast.makeText(this,
                        "Student added successfully",
                        Toast.LENGTH_SHORT).show();

                etStudentName.setText("");
                etStudentId.setText("");
                spMajor.setSelection(0);
            } else {
                Toast.makeText(this,
                        "Student ID already exists",
                        Toast.LENGTH_LONG).show();
            }
        });

        btnViewStudents.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    ViewStudent.class
            );
            startActivity(intent);
        });
    }
}