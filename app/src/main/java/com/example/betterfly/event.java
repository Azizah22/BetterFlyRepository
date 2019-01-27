package com.example.betterfly;

import android.location.Location;

import java.util.Date;

public class event {
    protected String name, descreption;
    protected Date date;
    protected double cHours;
    protected Location location;
    protected organization org;

    public event(String n, String des, Date d, double ch, Location l, organization o){
        name=n;
        descreption=des;
        cHours=ch;
        date=d;
        location=l;
        org=o;
    }
}
