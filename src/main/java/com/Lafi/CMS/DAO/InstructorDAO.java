package com.Lafi.CMS.DAO;

import com.Lafi.CMS.CRUDOperations.CourseCRUD;
import com.Lafi.CMS.CRUDOperations.StudentCRUD;
import com.Lafi.CMS.CRUDOperations.TakeCRUD;
import com.Lafi.CMS.Models.Courses.CourseBasic;
import com.Lafi.CMS.Models.Students.StudentAdvanced;
import com.Lafi.CMS.Models.Take;

import java.util.ArrayList;

public class InstructorDAO {
    // get all courses

    public ArrayList<CourseBasic> getAllCourses(long instructor_id) {
        CourseCRUD courseCRUD = new CourseCRUD();
        ArrayList<CourseBasic> results = courseCRUD.getInstructorCourses(instructor_id);
        return results;
    }

    // get all student in course
    public ArrayList<StudentAdvanced> getAllStudentsInCourse(long course_id){
        TakeCRUD takeCRUD = new TakeCRUD();
        ArrayList<Take> takes = takeCRUD.readAllCourseRecords(course_id);
        StudentCRUD studentCRUD = new StudentCRUD();
        ArrayList<StudentAdvanced> students = new ArrayList<>();
        for (Take t : takes) {
            students.add(new StudentAdvanced(studentCRUD.readByID(t.getStudent_ID()), t.getGrade()));
        }
        return students;
    }

    // edit student mark
    public  void editStudentGrade(long student_id, long course_id, double grade){
        TakeCRUD takeCRUD = new TakeCRUD();
        takeCRUD.editGrade(student_id, course_id, grade);
    }
}
