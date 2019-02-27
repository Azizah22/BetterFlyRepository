package com.example.betterfly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class RequestToVounteer extends AppCompatActivity  {

    private TextView textViewEventName , textViewOrgName, textViewDiscreption , textViewLoc , textViewDate , textViewVolunteerNum;

    event event;
    public String orgName , location ,des;
    public String eventName;
    public String date;
    public int nov;


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


        event= (event) intent.getSerializableExtra("event");
        if(bundle!=null){
            eventName = (String) bundle.get("name");
            DateFormat format = new SimpleDateFormat("d/MM/yyyy", Locale.ENGLISH);
            date=format.format(bundle.get("date"));
            // DoE=format.parse(date);
            nov= (int) bundle.get("Number of Volunteers");
            location =(String) bundle.get("location");
            des= (String) bundle.get("description");


            textViewEventName.setText(eventName);
            textViewDiscreption.setText(des);
            textViewLoc.setText(location);
            textViewDate.setText(date);
            textViewVolunteerNum.setText(String.valueOf(nov));

        }
    }

}
