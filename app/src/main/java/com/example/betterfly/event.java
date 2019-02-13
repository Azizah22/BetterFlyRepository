package com.example.betterfly;

import android.location.Location;

import java.util.Date;

public class event {
    protected String ID, name, descreption,location;
    protected Date date;
    protected int cHours;
    protected String [] emails ;



    public event(String id,String n, String des, Date d, int ch, String l, int num){
        ID=id;
        name=n;
        descreption=des;
        cHours=ch;
        date=d;
        location=l;
        emails=new String [num];
    }

    public String getName() {
        return name;
    }

    public String getDescreption() {
        return descreption;
    }

    public Date getDate() {
        return date;
    }

    public int getcHours() {
        return cHours;
    }

    public String getLocation() {
        return location;
    }

    public String[] getEmails() {
        return emails;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setcHours(int cHours) {
        this.cHours = cHours;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEmails(String[] emails) {
        this.emails = emails;
    }

}
