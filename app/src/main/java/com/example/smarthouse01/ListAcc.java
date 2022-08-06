package com.example.smarthouse01;

import androidx.appcompat.app.AppCompatActivity;

public class ListAcc {
    private String acc, pwd;

    public ListAcc(String acc, String pwd) {
        this.acc = acc;
        this.pwd = pwd;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
