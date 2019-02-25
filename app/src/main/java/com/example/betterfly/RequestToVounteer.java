package com.example.betterfly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;


public class RequestToVounteer extends AppCompatActivity  {

    private TextView textViewEventName , textViewOrgName, textViewDiscreption , textViewLoc , textViewDate , textViewVolunteerNum;

    event event;
    public String orgName , location ;
    public String eventName;
    public Date date;
    public int numberofVol;


    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteering_request);

        databaseReference = FirebaseDatabase.getInstance().getReference("Events");


       // findViewById(R.id.Request).setOnClickListener(this);

        textViewEventName = findViewById(R.id.EvName);
        textViewDiscreption = findViewById(R.id.description);
        textViewOrgName = findViewById(R.id.OrgName);
        textViewLoc = findViewById(R.id.loc);
        textViewDate = findViewById(R.id.date);
        textViewVolunteerNum = findViewById(R.id.VolNum);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        event = (event) intent.getSerializableExtra("Events");
        if (bundle != null) {
            eventName = (String) bundle.get("name");
            String evDes = (String) bundle.get("descreption");
            date = (Date) bundle.get("date");
            numberofVol = (int) bundle.get("num");
            location = (String) bundle.get("location");


            textViewEventName.setText(eventName);
            textViewDiscreption.setText(evDes);
            textViewLoc.setText(location);
            textViewDate.setText(String.valueOf(date));
            textViewVolunteerNum.setText(String.valueOf(numberofVol));

        }
    }

}
