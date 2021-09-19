package com.Lafi.CMS.CRUDOperations;

import com.Lafi.CMS.CRUDOperations.Interfaces.*;
import com.Lafi.CMS.CacheAccess.StudentCache;
import com.Lafi.CMS.DataBaseAccess.StudentAccess;
import com.Lafi.CMS.Models.Students.StudentBasic;

import java.util.ArrayList;

public class StudentCRUD implements IAdd<StudentBasic>, IDelete, IUpdate<StudentBasic>, IReadAll<StudentBasic>, IReadByID<StudentBasic> {
    @Override
    public void add(StudentBasic student) {
        StudentCache cacheAccess = StudentCache.getInstance();
        cacheAccess.put(student.getID(), student);
        StudentAccess DBAccess = StudentAccess.getInstance();
        DBAccess.add(student);
    }

    @Override
    public void delete(long ID) {
        StudentCache cacheAccess = StudentCache.getInstance();
        if (cacheAccess.isContains(ID))
            cacheAccess.delete(ID);
        StudentAccess DBAccess = StudentAccess.getInstance();
        DBAccess.delete(ID);
    }

    @Override
    public ArrayList<StudentBasic> readAll() {
        ArrayList<StudentBasic> results;
        StudentAccess DBAccess = StudentAccess.getInstance();
        results = DBAccess.readAll();
        StudentCache cacheAccess = StudentCache.getInstance();
        for (StudentBasic s : results) {
            cacheAccess.put(s.getID(), s);
        }
        return results;
    }

    @Override
    public StudentBasic readByID(long ID) {
        StudentBasic student;
        StudentCache cacheAccess = StudentCache.getInstance();
        if (cacheAccess.isContains(ID))
            student = cacheAccess.get(ID);
        else {
            StudentAccess DBAccess = StudentAccess.getInstance();
            student = DBAccess.readByID(ID);
            cacheAccess.put(student.getID(), student);
        }
        return student;
    }

    @Override
    public void update(StudentBasic student) {
        StudentCache cacheAccess = StudentCache.getInstance();
        if (cacheAccess.isContains(student.getID()))
            cacheAccess.update(student.getID(), student);
        StudentAccess DBAccess = StudentAccess.getInstance();
        DBAccess.delete(student.getID());
        DBAccess.add(student);
    }
}
