package com.Lafi.CMS.Models.Courses;

import com.Lafi.CMS.DataBaseAccess.CourseAccess;

public class CourseBasic {
    private String name;
    private long ID;
    private int hours;
    private long instructor_ID;
    private static long IDPointer = 0;

    public CourseBasic(){}

    public CourseBasic(String name, long ID, int hours, long instructor_ID) {
        this.name = name;
        this.ID = ID;
        this.hours = hours;
        this.instructor_ID = instructor_ID;
    }


    public CourseBasic(String name, int hours, long instructor_ID) {
        this.name = name;
        this.ID = IDGenerator();
        this.hours = hours;
        this.instructor_ID = instructor_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public long getInstructor_ID() {
        return instructor_ID;
    }

    public void setInstructor_ID(long instructor_ID) {
        this.instructor_ID = instructor_ID;
    }

    public static long IDGenerator(){
        CourseAccess DBAccess = CourseAccess.getInstance();
        if (IDPointer == 0)
            IDPointer = DBAccess.getLastID();
        return ++IDPointer;
    }


    @Override
    public String toString() {
        return "CourseBasic{" +
                "name='" + name + '\'' +
                ", ID=" + ID +
                ", hours=" + hours +
                ", instructor_ID=" + instructor_ID +
                '}';
    }
}
