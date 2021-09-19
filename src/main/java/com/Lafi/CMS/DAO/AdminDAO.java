package com.Lafi.CMS.DAO;

import com.Lafi.CMS.CRUDOperations.CourseCRUD;
import com.Lafi.CMS.CRUDOperations.InstructorCRUD;
import com.Lafi.CMS.CRUDOperations.StudentCRUD;
import com.Lafi.CMS.CRUDOperations.TakeCRUD;
import com.Lafi.CMS.Models.Courses.CourseBasic;
import com.Lafi.CMS.Models.Instructors.InstructorBasic;
import com.Lafi.CMS.Models.Students.StudentBasic;
import com.Lafi.CMS.Models.Take;

import java.util.ArrayList;

public class AdminDAO {
    // add course
    public void addCourse(CourseBasic course) {
        CourseCRUD courseCRUD = new CourseCRUD();
        courseCRUD.add(course);
    }

    // list all courses
    public ArrayList<CourseBasic> getAllCourse() {
        CourseCRUD courseCRUD = new CourseCRUD();
        return courseCRUD.readAll();
    }


    // add student to course
    public void addStudentToCourse(long student_id, long course_id) {
        TakeCRUD takeCRUD = new TakeCRUD();
        takeCRUD.add(new Take(student_id, course_id, 0.0));
    }

    // delete course
    public void deleteCourse(long course_id) {
        CourseCRUD courseCRUD = new CourseCRUD();
        courseCRUD.delete(course_id);
        TakeCRUD takeCRUD = new TakeCRUD();
        takeCRUD.deleteAllCourseRecords(course_id);
    }

    // delete instructor
    public void deleteInstructor(long instructor_id) {
        InstructorCRUD instructorCRUD = new InstructorCRUD();
        instructorCRUD.delete(instructor_id);
        CourseCRUD courseCRUD = new CourseCRUD();
        courseCRUD.setCoursesInstructorToUndefined(instructor_id);
    }

    // edit instructor
    public void editInstructor(InstructorBasic instructor) {
        InstructorCRUD instructorCRUD = new InstructorCRUD();
        instructorCRUD.update(instructor);
    }

    // add instructor
    public void addInstructor(InstructorBasic instructor) {
        InstructorCRUD instructorCRUD = new InstructorCRUD();
        instructorCRUD.add(instructor);
    }

    // list all instructor
    public ArrayList<InstructorBasic> getAllInstructor() {
        InstructorCRUD instructorCRUD = new InstructorCRUD();
        return instructorCRUD.readAll();
    }

    // edit student
    public void editStudent(StudentBasic student) {
        StudentCRUD studentCRUD = new StudentCRUD();
        studentCRUD.update(student);
    }

    // add student
    public void addStudent(StudentBasic student) {
        StudentCRUD studentCRUD = new StudentCRUD();
        studentCRUD.add(student);
    }

    // list all students
    public ArrayList<StudentBasic> getAllStudents() {
        StudentCRUD studentCRUD = new StudentCRUD();
        return studentCRUD.readAll();
    }
    // add student
    public void deleteStudent(long ID){
        StudentCRUD studentCRUD = new StudentCRUD();
        studentCRUD.delete(ID);
    }
}
