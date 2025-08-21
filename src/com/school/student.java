package com.school;

public class student {

    int studentId;
    String studentName;

    public void setDetails(int studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public void displayDetails() {
        System.out.println("Student ID: " + studentId);
        System.out.println("Student Name: " + studentName);
    }
}
