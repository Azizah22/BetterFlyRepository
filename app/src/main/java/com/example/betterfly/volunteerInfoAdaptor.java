package com.example.betterfly;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class volunteerInfoAdaptor extends ArrayAdapter<Volunteer> implements View.OnClickListener {
    Activity context;
    private List<Volunteer> volunteersList;
    private event event1;

    public volunteerInfoAdaptor(Activity context, List<Volunteer> volunteersList, event event1) {
        super(context, R.layout.volunteerslist, volunteersList);
        this.context = context;
        this.volunteersList = volunteersList;
        this.event1 = event1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View listView = inflater.inflate(R.layout.volunteerslist, null, true);


        Date edate = event1.getDate();
        final int ehours = event1.getcHours();

        final TextView attandance = listView.findViewById(R.id.att);
        final Button yes = listView.findViewById(R.id.Yes);
        final Button no = listView.findViewById(R.id.no);

        final TextView vName = listView.findViewById(R.id.RName);
        final TextView vdate = listView.findViewById(R.id.RDate);
        final TextView vemail = listView.findViewById(R.id.remail);

        final Volunteer volunteer1 = volunteersList.get(position);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = volunteer1.getDob();
        String DoB = format.format(date);
        final int hours = volunteer1.getHours();
        final String vEmail=volunteer1.getEmail();
        vName.setText(volunteer1.getName());
        vdate.setText(DoB);
        vemail.setText(volunteer1.getEmail());
        Date c = Calendar.getInstance().getTime();


        if (edate.before(c)) {

            attandance.setVisibility(View.VISIBLE);
            yes.setVisibility(View.VISIBLE);
            no.setVisibility(View.VISIBLE);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int totalhours = hours + ehours;
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Volunteer");
                    databaseReference.child(vEmail.substring(0,vEmail.indexOf('@'))).child("hours").setValue(totalhours);
                    attandance.setVisibility(View.GONE);
                    yes.setVisibility(View.GONE);
                    no.setVisibility(View.GONE);
                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    attandance.setVisibility(View.GONE);
                    yes.setVisibility(View.GONE);
                    no.setVisibility(View.GONE);

                }
            });


        }
            return listView;
}



    @Override
    public void onClick(View v) {

    }
}