package com.Lafi.CMS.Models.Instructors;

import com.Lafi.CMS.Models.Courses.CourseAdvanced;

import java.util.ArrayList;

public class InstructorAdvanced extends InstructorBasic{

    private ArrayList<CourseAdvanced> courses;

    public InstructorAdvanced(String email, String password) {
        super(email, password);
    }

    public ArrayList<CourseAdvanced> getCourses() {
        return courses;
    }

    public void addCourses(CourseAdvanced course) {
        this.courses.add(course);
    }

    @Override
    public String toString() {
        return "InstructorAdvanced{" +
                "courses=" + courses +
                "} " + super.toString();
    }
}
