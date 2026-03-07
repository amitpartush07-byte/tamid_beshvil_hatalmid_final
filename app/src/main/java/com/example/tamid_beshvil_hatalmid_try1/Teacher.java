package com.example.tamid_beshvil_hatalmid_try1;

public class Teacher
{
    private String Name;
    private String UserID;
    private String Email;
    private String Password;
    private String Subject;
    private String Phone;




    public Teacher(String name, String userID, String email, String password, String subject, String phone)
    {
        this.Name=name;
        this.UserID=userID;
        this.Email=email;
        this.Password=password;
        this.Subject=subject;
        this.Phone=phone;
    }

    public Teacher()
    {

    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }

    public String getUserID() {
        return UserID;
    }

    public String getPassword() {
        return Password;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getSubject() {
        return Subject;
    }

    public void setPassword(String password) {
        Password = password;
    }


}
