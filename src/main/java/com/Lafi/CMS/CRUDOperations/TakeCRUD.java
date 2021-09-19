package com.Lafi.CMS.CRUDOperations;

import com.Lafi.CMS.CRUDOperations.Interfaces.IAdd;
import com.Lafi.CMS.DataBaseAccess.TakeAccess;
import com.Lafi.CMS.Models.Take;

import java.util.ArrayList;

public class TakeCRUD implements IAdd<Take> {
    @Override
    public void add(Take take) {
        TakeAccess DBAccess = TakeAccess.getInstance();
        DBAccess.add(take);
    }

    public void deleteAllCourseRecords(long ID) {
        TakeAccess DBAccess = TakeAccess.getInstance();
        DBAccess.delete(ID);
    }

    public void deleteAllStudentRecords(long ID) {
        TakeAccess DBAccess = TakeAccess.getInstance();
        DBAccess.deleteAllWithStudentID(ID);
    }

    public void deleteStudentRecordFromCourse(long student_id, long course_id) {
        TakeAccess DBAccess = TakeAccess.getInstance();
        DBAccess.deleteWithStudentAndCourse(student_id, course_id);
    }

    public void editGrade(long student_id, long course_id, double grade) {
        TakeAccess DBAccess = TakeAccess.getInstance();
        Take temp = DBAccess.readGrade(student_id, course_id);
        DBAccess.deleteWithStudentAndCourse(student_id, course_id);
        temp.setGrade(grade);
        DBAccess.add(temp);
    }

    public ArrayList<Take> readAllStudentRecords(long student_id){
        TakeAccess DBAccess = TakeAccess.getInstance();
        return DBAccess.readByStudentID(student_id);
    }

    public ArrayList<Take> readAllCourseRecords(long course_id){
        TakeAccess DBAccess = TakeAccess.getInstance();
        return DBAccess.readByCourseID(course_id);
    }

    public double getGrade (long student_id, long course_id){
        TakeAccess DBAccess = TakeAccess.getInstance();
        return DBAccess.readGrade(student_id, course_id).getGrade();

    }
}
