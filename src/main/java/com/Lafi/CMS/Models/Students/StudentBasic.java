package com.Lafi.CMS.Models.Students;

import com.Lafi.CMS.DataBaseAccess.StudentAccess;
import com.Lafi.CMS.Models.User;

public class StudentBasic extends User {
    // TODO - read last ID from file
    private static long IDPointer = 1000;
    private String name;
    private String DOB;
    private String major;

    public StudentBasic(){}
    public StudentBasic(String name, String major, String email, String password, String DOB) {
        super(email, password, "Student", IDGenerator());
        this.DOB = DOB;
        this.name = name;
        this.major = major;
    }

    public StudentBasic(String email, String password, String DOB) {
        super(email, password, "Student", IDGenerator());
        this.DOB = DOB;
    }

    public StudentBasic(String name, long ID, String email, String password, String DOB, String major) {
        super(email, password, "Student", ID);
        this.DOB = DOB;
        this.name = name;
        this.major = major;
    }

    public StudentBasic(String name, long ID, String email, String major) {
        super(email, "12345", "Student", ID);
        this.name = name;
        this.major = major;
    }

    public static synchronized long IDGenerator() {
        StudentAccess DBAccess = StudentAccess.getInstance();
        IDPointer = DBAccess.getLastID();
        return ++IDPointer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return "StudentBasic{" +
                "name='" + name + '\'' +
                ", DOB=" + DOB +
                ", Major='" + major + '\'' +
                super.toString() + '\'' +
                '}';
    }
}
