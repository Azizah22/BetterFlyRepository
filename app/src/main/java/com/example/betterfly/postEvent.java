package com.example.betterfly;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.Annotation;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.app.DatePickerDialog;

public class postEvent extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "postEvent";
    EditText editTextName, editTextLoc, editTextDisc, editTextnov, editTextDoB;
    DatePickerDialog.OnDateSetListener datePickerDoB;
    String date;
    Date DoE;

    FirebaseAuth mAuth;
    DatabaseReference databaseEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_event);

        editTextName = findViewById(R.id.name);
        editTextLoc = findViewById(R.id.loc);
        editTextDisc = findViewById(R.id.desc);
        editTextnov = findViewById(R.id.NoV);
        editTextDoB= findViewById(R.id.DoB);

        databaseEvents = FirebaseDatabase.getInstance().getReference("Events");

        findViewById(R.id.post).setOnClickListener(this);
        editTextDoB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        postEvent.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        datePickerDoB,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        datePickerDoB= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                Log.d(TAG,"onDateSet: dd/mm/yyyy:"+ dayOfMonth+"/"+month+"/"+year);

                date=dayOfMonth+"/"+month+"/"+year;
                editTextDoB.setText(date);

            }
        };

        findViewById(R.id.post).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.post:
                try {
                    newPost();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void newPost() throws ParseException {
        String name = editTextName.getText().toString().trim();
        String loc = editTextLoc.getText().toString().trim();
        String disc = editTextDisc.getText().toString().trim();

        int ch = 3;
        String snov =editTextnov.getText().toString().trim();
        int nov = Integer.parseInt(snov);

        if(date!=null) {
            DateFormat format = new SimpleDateFormat("d/MM/yyyy", Locale.ENGLISH);
            DoE = format.parse(date);
        }

        if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(loc)&&!TextUtils.isEmpty(disc)&&!TextUtils.isEmpty(snov)) {
           // FirebaseUser user = mAuth.getCurrentUser();
            String id =FirebaseAuth.getInstance().getCurrentUser().getUid();
            event e=new event (id,name,disc,DoE,ch,loc,nov);


            databaseEvents.push().setValue(e);

            Toast.makeText(this, "Event posted", Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(this, OrgProcessActivity.class));
        }
        else{
            Toast.makeText(this, "Fill all feild", Toast.LENGTH_LONG).show();
        }

    }
}
