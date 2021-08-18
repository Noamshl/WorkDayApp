package com.example.myapplication.data;





public class User {
    private int ID;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String mail;

    public User(){
        this.username = "";
        this.password = "111";
        this.firstName = "a";
        this.lastName = "a";
        this.mail = "mail";
    }
    public User(String username, String password,String firstName,String lastName,String mail){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
