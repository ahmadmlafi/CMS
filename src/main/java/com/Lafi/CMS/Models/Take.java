package com.Lafi.CMS.Models;

public class Take {
    private long student_ID;
    private long course_ID;
    private double grade;

    public Take(){}
    public Take(long student_ID, long course_ID, double grade) {
        this.student_ID = student_ID;
        this.course_ID = course_ID;
        this.grade = grade;
    }
    public Take(boolean isNull) {
        this.student_ID = 0;
        this.course_ID = 0;
        this.grade = 0.0;
    }
    public long getStudent_ID() {
        return student_ID;
    }

    public void setStudent_ID(long student_ID) {
        this.student_ID = student_ID;
    }

    public long getCourse_ID() {
        return course_ID;
    }

    public void setCourse_ID(long course_ID) {
        this.course_ID = course_ID;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Take{" +
                "student_ID=" + student_ID +
                ", course_ID=" + course_ID +
                ", grade=" + grade +
                '}';
    }
}
