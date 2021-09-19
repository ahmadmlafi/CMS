package com.Lafi.CMS.Models;

import com.Lafi.CMS.DataBaseAccess.AdminAccess;

public class Admin extends User {
    private static long IDPointer;
    private String name;
    private long ID;

    public Admin() {
    }

    public Admin(String email, String password, long ID) {
        super(email, password, "Admin", IDGenerator());
    }

    public Admin(Boolean isNull) {
        super("Null.com", "12345", "Admin", 0);
    }

    public static long IDGenerator() {
        AdminAccess DBAccess = AdminAccess.getInstance();
        if (IDPointer == 0)
            IDPointer = DBAccess.getLastID();
        return ++IDPointer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "name='" + name + '\'' +
                ", ID=" + ID +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
