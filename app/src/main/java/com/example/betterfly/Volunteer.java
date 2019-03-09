package com.example.betterfly;

import java.util.Date;

public class Volunteer {
    public String getSkill1() {
        return skill1;
    }

    public String getSkill2() {
        return skill2;
    }

    public String getSkill3() {
        return skill3;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSkill1(String skill1) {
        this.skill1 = skill1;
    }

    public void setSkill2(String skill2) {
        this.skill2 = skill2;
    }

    public void setSkill3(String skill3) {
        this.skill3 = skill3;
    }


    private String skill1, skill2, skill3;
    public String name , email;
    public Date dob;

    public Volunteer(){

    }

    public Volunteer(String name, String email, Date dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    public Volunteer(String name, String skill1, String skill2, String skill3){
        this.name = name;
        this.skill1 = skill1;
        this.skill2 = skill2;
        this.skill3 = skill3;

    }
}
