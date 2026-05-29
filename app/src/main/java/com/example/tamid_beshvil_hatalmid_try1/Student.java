package com.example.tamid_beshvil_hatalmid_try1;

public class Student
{
    private String name;
    private String userID;
    private String email;
    private String password;
    private String grade;
    private String phone;


    public Student(String name,String userID,String email,String password, String grade, String phone)
    {
        this.name=name;
        this.userID=userID;
        this.email=email;
        this.password=password;
        this.grade=grade;
        this.phone=phone;
    }

    public Student()
    {

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        grade = grade;
    }

    public void setEmail(String email) {
        email = email;
    }
}
