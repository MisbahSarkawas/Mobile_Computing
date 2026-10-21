package com.example.firebasecurdop;

public class Student {

    private String name;
    private String course;
    private String rollNo;

    // Required empty constructor for Firebase
    public Student() {
    }

    public Student(String name, String course, String rollNo) {
        this.name = name;
        this.course = course;
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }
}