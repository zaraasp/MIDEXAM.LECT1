package com.example.midexamlect1;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewStudent extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        dbHelper = new DBHelper(this);

        loadStudents();
    }

    private void loadStudents() {
        List<StudentModel> students = dbHelper.getStudentList();

        StudentAdapter adapter = new StudentAdapter(
                this,
                students,
                dbHelper,
                this::loadStudents
        );

        recyclerView.setAdapter(adapter);

        if (students.isEmpty()) {
            Toast.makeText(this,
                    "No Student Found",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (dbHelper != null && recyclerView != null) {
            loadStudents();
        }
    }
}