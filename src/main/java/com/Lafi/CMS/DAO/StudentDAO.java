package com.Lafi.CMS.DAO;

import com.Lafi.CMS.CRUDOperations.CourseCRUD;
import com.Lafi.CMS.CRUDOperations.TakeCRUD;
import com.Lafi.CMS.Models.Courses.CourseAdvanced;
import com.Lafi.CMS.Models.Take;

import java.util.ArrayList;

public class StudentDAO {
    public ArrayList<CourseAdvanced> getAllCourses(long student_id) {
        ArrayList<CourseAdvanced> results = new ArrayList<>();
        TakeCRUD takeCRUD = new TakeCRUD();
        CourseCRUD courseCRUD = new CourseCRUD();
        ArrayList<Take> takes = takeCRUD.readAllStudentRecords(student_id);
        CourseAdvanced tempCourse;
        for (Take t : takes) {
            tempCourse = new CourseAdvanced(courseCRUD.readByID(t.getCourse_ID()), t.getGrade());
            results.add(tempCourse);
        }
        return results;
    }
}
