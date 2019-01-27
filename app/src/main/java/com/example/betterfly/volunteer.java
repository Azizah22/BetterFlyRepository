package com.example.betterfly;

import java.util.Date;

public class volunteer {

    protected String name,skils,sex;
    protected char type;
    protected double hours;
    protected Date dob;

    public volunteer (String n, String sk, String g, Date d ){
       name=n;
       type='v';
       skils=sk;
       sex=g;
       hours=0;
      dob=d;
    }

    public Date getDob() {
        return dob;
    }

    public char getType() {
        return type;
    }

    public double getHours() {
        return hours;
    }

    public String vBatch(){
        if(hours == 0 || hours <= 15)
            return "";
        else if(hours>=16 || hours <= 30)
            return "";
        else
            return "";
    }

    public String getName() {
        return name;
    }

    public String getSkils() {
        return skils;
    }
}
