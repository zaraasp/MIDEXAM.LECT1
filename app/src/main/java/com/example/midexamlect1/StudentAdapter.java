package com.example.midexamlect1;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter
        extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private final Context context;
    private final List<StudentModel> studentList;
    private final DBHelper dbHelper;
    private final Runnable refreshList;

    private final String[] majors = {
            "Informatics",
            "Information Systems"
    };

    public StudentAdapter(Context context,
                          List<StudentModel> studentList,
                          DBHelper dbHelper,
                          Runnable refreshList) {
        this.context = context;
        this.studentList = studentList;
        this.dbHelper = dbHelper;
        this.refreshList = refreshList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_item_list, parent, false);

        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull StudentViewHolder holder, int position) {

        StudentModel student = studentList.get(position);

        holder.tvDatabaseId.setText(
                "Student: " + student.getStudentId());

        holder.etStudentId.setText(student.getStudentId());
        holder.etName.setText(student.getName());

        int majorPosition = student.getMajor().equals("Information Systems")
                ? 1 : 0;

        holder.spMajor.setSelection(majorPosition);

        holder.btnEdit.setOnClickListener(v -> {
            String studentId =
                    holder.etStudentId.getText().toString().trim();

            String name =
                    holder.etName.getText().toString().trim();

            String major =
                    holder.spMajor.getSelectedItem().toString();

            if (studentId.isEmpty() || name.isEmpty()) {
                Toast.makeText(context,
                        "Please fill in all fields",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(context)
                    .setTitle("Edit Student")
                    .setMessage("Save changes?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        StudentModel updatedStudent = new StudentModel(
                                student.getId(),
                                studentId,
                                name,
                                major
                        );

                        boolean success =
                                dbHelper.updateStudent(updatedStudent);

                        if (success) {
                            Toast.makeText(context,
                                    "Student updated",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context,
                                    "Update failed. Student ID may already exist.",
                                    Toast.LENGTH_LONG).show();
                        }

                        refreshList.run();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Student")
                    .setMessage("Delete this student?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        boolean success =
                                dbHelper.deleteStudent(student.getId());

                        if (success) {
                            Toast.makeText(context,
                                    "Student deleted",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context,
                                    "Delete failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                        refreshList.run();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class StudentViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvDatabaseId;
        EditText etStudentId, etName;
        Spinner spMajor;
        Button btnEdit, btnDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDatabaseId =
                    itemView.findViewById(R.id.tvDatabaseId);

            etStudentId =
                    itemView.findViewById(R.id.etItemStudentId);

            etName =
                    itemView.findViewById(R.id.etItemName);

            spMajor =
                    itemView.findViewById(R.id.spItemMajor);

            btnEdit =
                    itemView.findViewById(R.id.btnEdit);

            btnDelete =
                    itemView.findViewById(R.id.btnDelete);

            String[] majors = {
                    "Informatics",
                    "Information Systems"
            };

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    itemView.getContext(),
                    android.R.layout.simple_spinner_item,
                    majors
            );

            adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);

            spMajor.setAdapter(adapter);
        }
    }
}