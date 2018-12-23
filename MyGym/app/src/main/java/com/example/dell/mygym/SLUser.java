package com.example.dell.mygym;

public class SLUser {
    private int id;
    private String username;
    private String password;
    public SLUser() {
        super();
    }

    public SLUser(int id, String username, String password, String sex, int age) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}