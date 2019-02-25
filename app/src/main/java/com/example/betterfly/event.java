package com.example.betterfly;

import android.location.Location;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class event implements Serializable {
    public String ID, name, descreption,location;
    public Date date;
    public int cHours;
    public List<String> emails ;

    int nov;
    int counter=0;


    public event(){

    }

    public event(String id,String n, String des, Date d, int ch, String l, int num){
        ID=id;
        name=n;
        descreption=des;
        cHours=ch;
        date=d;
        location=l;
        nov=num;
        emails = new LinkedList<String>() ;

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

    public int addEmail(String email){
        if(counter<nov){
            emails.add(email);
            counter++;
            return 1;
        }
         return  -1;

    }


}
