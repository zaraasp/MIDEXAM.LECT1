package com.example.midexamlect1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "student_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE = "student";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE student (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "student_id TEXT NOT NULL UNIQUE, " +
                "name TEXT NOT NULL, " +
                "major TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
        // Tidak menghapus data yang sudah ada.
    }

    public boolean addStudent(StudentModel student) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("student_id", student.getStudentId());
        values.put("name", student.getName());
        values.put("major", student.getMajor());

        long result = db.insert(TABLE, null, values);
        db.close();

        return result != -1;
    }

    public boolean updateStudent(StudentModel student) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("student_id", student.getStudentId());
        values.put("name", student.getName());
        values.put("major", student.getMajor());

        int result = db.update(
                TABLE,
                values,
                "id=?",
                new String[]{String.valueOf(student.getId())}
        );

        db.close();
        return result > 0;
    }

    public boolean deleteStudent(int id) {
        SQLiteDatabase db = getWritableDatabase();

        int result = db.delete(
                TABLE,
                "id=?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        return result > 0;
    }

    public List<StudentModel> getStudentList() {
        List<StudentModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id, student_id, name, major " +
                        "FROM student ORDER BY id DESC",
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String studentId = cursor.getString(1);
            String name = cursor.getString(2);
            String major = cursor.getString(3);

            list.add(new StudentModel(
                    id, studentId, name, major
            ));
        }

        cursor.close();
        db.close();

        return list;
    }
}