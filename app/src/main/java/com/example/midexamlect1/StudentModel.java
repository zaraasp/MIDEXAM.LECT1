package com.example.midexamlect1;

public class StudentModel {
    private int id;
    private String studentId;
    private String name;
    private String major;

    public StudentModel(String studentId, String name, String major) {
        this.studentId = studentId;
        this.name = name;
        this.major = major;
    }

    public StudentModel(int id, String studentId,
                        String name, String major) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.major = major;
    }

    public int getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}