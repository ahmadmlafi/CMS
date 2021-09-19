package com.Lafi.CMS.Models.Instructors;

import com.Lafi.CMS.DataBaseAccess.InstructorAccess;
import com.Lafi.CMS.Models.User;

public class InstructorBasic extends User {
    private String name;
    private String phone;
    private String department;
    // TODO - read last ID from file
    private static long IDPointer;

    public InstructorBasic(){}
    public InstructorBasic (String email, String password) {
        super(email, password, "Instructor", IDGenerator());
    }
    public InstructorBasic(String name, long ID, String email, String password, String phone, String department) {
        super(email, password, "Instructor", ID);
        this.name = name;
        this.department = department;
        this.phone = phone;
    }
    public InstructorBasic(String name, String email, String password, String phone, String department) {
        super(email, password, "Instructor", IDGenerator());
        this.name = name;
        this.department = department;
        this.phone = phone;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static long IDGenerator(){
        InstructorAccess DBAccess = InstructorAccess.getInstance();
        if (IDPointer == 0)
            IDPointer = DBAccess.getLastID();
        return ++IDPointer;
    }

    @Override
    public String toString() {
        return "InstructorBasic{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", department='" + department + '\'' +
                "} " + super.toString();
    }
}
