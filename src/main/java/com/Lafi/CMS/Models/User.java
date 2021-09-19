package com.Lafi.CMS.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)

// TODO - add new constructor for advance purpose without generating new id
public class User implements Serializable {
    private String email;
    private String password;
    private String role;
    private long ID;

    public User() {}

    public User(String email, String password, String role, long ID) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.ID = ID;
    }

    public User(Boolean isNull) {
        this.email = "Null.com";
        this.password = "12345";
        this.role = "User";
        this.ID = 0;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", ID=" + ID +
                ", PassWord= " + password+
                '}';
    }

}
