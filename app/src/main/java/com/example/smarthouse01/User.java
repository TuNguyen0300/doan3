package com.example.smarthouse01;

public class User {
    private String fullname, email, passwd, pnumber;

    public User(){

    }

    public User(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }

    public User(String fullname, String email, String pnumber) {
        this.fullname = fullname;
        this.email = email;
        this.pnumber = pnumber;
    }

    public User(String fullname, String email, String passwd, String pnumber) {
        this.fullname = fullname;
        this.email = email;
        this.passwd = passwd;
        this.pnumber = pnumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPnumber() {
        return pnumber;
    }

    public void setPnumber(String pnumber) {
        this.pnumber = pnumber;
    }
}
