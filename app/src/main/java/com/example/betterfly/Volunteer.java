package com.example.betterfly;

import java.util.Date;

public class Volunteer {
    public String name , email;
    public Date dob;
    public int hours,preHours;

    public Volunteer(String name, String email, Date dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        hours=0;
        preHours=0;
    }

    public Volunteer(){

    }

    public void setHours(int hours) {
        preHours= this.hours;
        this.hours = hours;
    }

    public int getHours()
    {
        return hours;
    }

    public  void setName(String name) {

        this.name = name;
    }

    public Date getDob() {

        return dob;
    }

    public void setDob(Date dob) {

        this.dob = dob;
    }

    public  String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getName() {

        return name;
    }
}
