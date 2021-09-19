package com.Lafi.CMS.CRUDOperations;

import com.Lafi.CMS.CRUDOperations.Interfaces.*;
import com.Lafi.CMS.CacheAccess.CourseCache;
import com.Lafi.CMS.DataBaseAccess.CourseAccess;
import com.Lafi.CMS.Models.Courses.CourseBasic;

import java.util.ArrayList;


public class CourseCRUD implements IAdd<CourseBasic>, IDelete, IUpdate<CourseBasic>, IReadAll<CourseBasic>, IReadByID<CourseBasic> {
    @Override
    public void add(CourseBasic course) {
        CourseCache cacheAccess = CourseCache.getInstance();
        cacheAccess.put(course.getID(), course);
        CourseAccess DBAccess = CourseAccess.getInstance();
        DBAccess.add(course);
    }

    @Override
    public void delete(long ID) {
        CourseCache cacheAccess = CourseCache.getInstance();
        if (cacheAccess.isContains(ID))
            cacheAccess.delete(ID);
        CourseAccess DBAccess = CourseAccess.getInstance();
        DBAccess.delete(ID);
    }

    @Override
    public ArrayList<CourseBasic> readAll() {
        ArrayList<CourseBasic> results;
        CourseAccess DBAccess = CourseAccess.getInstance();
        results = DBAccess.readAll();
        CourseCache cacheAccess = CourseCache.getInstance();
        for (CourseBasic c : results){
            cacheAccess.put(c.getID(), c);
        }
        return results;
    }

    @Override
    public CourseBasic readByID(long ID) {
        CourseBasic course;
        CourseCache cacheAccess = CourseCache.getInstance();
        if (cacheAccess.isContains(ID))
            course = cacheAccess.get(ID);
        else {
            CourseAccess DBAccess = CourseAccess.getInstance();
            course = DBAccess.readByID(ID);
            cacheAccess.put(course.getID(), course);
        }
        return course;
    }

    @Override
    public void update(CourseBasic course) {
        CourseCache cacheAccess = CourseCache.getInstance();
        if (cacheAccess.isContains(course.getID()))
            cacheAccess.update(course.getID(), course);
        CourseAccess DBAccess = CourseAccess.getInstance();
        DBAccess.delete(course.getID());
    }

    public ArrayList<CourseBasic> getInstructorCourses (long instructor_id){
        CourseAccess DBAccess = CourseAccess.getInstance();
        ArrayList<CourseBasic> results = DBAccess.getCoursesForInstructor(instructor_id);
        return results;
    }

    public void setCoursesInstructorToUndefined (long instructor_id){
        CourseAccess DBAccess = CourseAccess.getInstance();
        DBAccess.setCoursesInstructorToUndefined(instructor_id);
        CourseCache cacheAccess = CourseCache.getInstance();
        CourseBasic temp;
        for(long key : cacheAccess.getKeys()){
            cacheAccess.delete(key);
            temp = readByID(key);
            cacheAccess.put(key, temp);
        }
    }

}
