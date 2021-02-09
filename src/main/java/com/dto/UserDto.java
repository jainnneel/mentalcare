package com.dto;

import java.sql.Date;

public class UserDto {

    private String email;
    
    private String pass;

    private Date date;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UserDto(String email, String pass, Date date) {
        super();
        this.email = email;
        this.pass = pass;
        this.date = date;
    }

    public UserDto() {
        // TODO Auto-generated constructor stub
    }

}
