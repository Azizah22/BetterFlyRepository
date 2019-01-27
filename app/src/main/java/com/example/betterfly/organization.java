package com.example.betterfly;

import android.location.Location;

import java.util.Date;

public class organization {

    protected String name,skils;
    protected char type;
    protected event[] posts;
    protected int count;

    public organization (String n, String sk ){
        name=n;
        type='o';
        skils=sk;
        posts= new event[500];
        count=0;
    }

    public String getName() {
        return name;
    }

    public boolean postEvent(String n, String des, Date d, double ch, Location l){
       if(count <= 500){
        posts[count++] =new event(n, des,  d,  ch,  l,  this);
        return true;}
        return false;
    }

    public String getSkils() {
        return skils;
    }

}
