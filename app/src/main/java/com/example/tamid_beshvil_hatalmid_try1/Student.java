package com.example.tamid_beshvil_hatalmid_try1;

public class Student
{
    private String Name;
    private String UserID;
    private String Email;
    private String Password;
    private String Grade;
    private String Phone;


    public Student(String name,String userID,String email,String password, String grade, String phone)
    {
        this.Name=name;
        this.UserID=userID;
        this.Email=email;
        this.Password=password;
        this.Grade=grade;
        this.Phone=phone;
    }

    public Student()
    {

    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
