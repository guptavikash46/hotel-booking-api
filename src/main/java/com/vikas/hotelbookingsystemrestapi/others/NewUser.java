package com.vikas.hotelbookingsystemrestapi.others;

public class NewUser {

    private String uname;
    private String pass;

    public String getUname() {
        return uname;
    }
    public NewUser() {}
    
    public NewUser(String uname, String pass) {
        this.uname = uname;
        this.pass = pass;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
