package com.Lafi.CMS.Models.Courses;

import com.Lafi.CMS.Models.Students.StudentBasic;

import java.util.ArrayList;

// TODO - add constructor and defaults values
public class CourseAdvanced extends CourseBasic{
    public double getStudent_grade() {
        return student_grade;
    }

    public void setStudent_grade(double student_grade) {
        this.student_grade = student_grade;
    }

    private double student_grade;
    private ArrayList<Double> grades = new ArrayList<>();
    private ArrayList<StudentBasic> students = new ArrayList<>();

    public CourseAdvanced(String name, int hours, long instructor_ID) {
        super(name, hours, instructor_ID);
    }

    public CourseAdvanced(CourseBasic course, double grade){
        super(course.getName(), course.getID(), course.getHours(), course.getInstructor_ID());
        student_grade = grade;
    }
    public double getStudentGrade(long student_id) {
        for (StudentBasic s : students) {
            if (s.getID() == student_id)
                return grades.get(students.indexOf(s));
        }
    return 0;
    }

    public void setGrade(long student_id, double grade) {
        for (StudentBasic s : students) {
            if (s.getID() == student_id)
                grades.set(students.indexOf(s), grade);
        }    }

    public ArrayList<StudentBasic> getStudents() {
        return students;
    }

    public void addStudent(StudentBasic student) {
        students.add(student);
    }
    public void addStudent(StudentBasic student, double grade) {
        students.add(student);
        grades.add(grade);
    }

    @Override
    public String toString() {
        return "CourseAdvanced{" +
                ", grade=" + student_grade +
                "} " + super.toString();
    }
}
