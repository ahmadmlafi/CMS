package com.Lafi.CMS.Models.Students;

import com.Lafi.CMS.Models.Courses.CourseAdvanced;

import java.util.ArrayList;

public class StudentAdvanced extends StudentBasic {

    private double temp_grade;
    private ArrayList<CourseAdvanced> courses;

    public StudentAdvanced(String email, String password, String DOB) {
        super(email, password, DOB);
    }

    public StudentAdvanced(StudentBasic basic, double temp_grade) {
        super(basic.getName(), basic.getID(), basic.getEmail(), basic.getMajor());
        this.temp_grade = temp_grade;
    }

    public StudentAdvanced() {
        super("Null", 0, "Null.com", "0000", "01-01-1900", "Null");

    }

    public double getTemp_grade() {
        return temp_grade;
    }

    public void setTemp_grade(double temp_grade) {
        this.temp_grade = temp_grade;
    }

    public ArrayList<CourseAdvanced> getCourses() {
        return courses;
    }

    public void addCourses(CourseAdvanced course) {
        this.courses.add(course);
    }

    @Override
    public String toString() {
        return "StudentAdvanced{" +
                super.toString() +
                "temp_grade=" + temp_grade +
                ", courses=" + courses +
                '}';
    }
}
