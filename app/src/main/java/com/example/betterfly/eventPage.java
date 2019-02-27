package com.example.betterfly;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class eventPage extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewName , textViewDate, textViewNOV, textViewLocation,textViewDes;

    event event1;
    public String eventName;
    public String date ,loc,des;
    int nov;
    public Date DoE;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);

        databaseReference= FirebaseDatabase.getInstance().getReference("Events");

        findViewById(R.id.backbtn).setOnClickListener(this);

        textViewName = findViewById(R.id.name);
        textViewDate = findViewById(R.id.date);
        textViewNOV = findViewById(R.id.num);
        textViewLocation = findViewById(R.id.loc);
        textViewDes = findViewById(R.id.des);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        event1= (event) intent.getSerializableExtra("event");
        if(bundle!=null){
            eventName = (String) bundle.get("name");
            DateFormat format = new SimpleDateFormat("d/MM/yyyy", Locale.ENGLISH);
            date=format.format(bundle.get("date"));
            // DoE=format.parse(date);
            nov= (int) bundle.get("Number of Volunteers");
            loc =(String) bundle.get("location");
            des= (String) bundle.get("description");

            String numOfVol= String.valueOf(nov);
            textViewName.setText(eventName);
            textViewDate.setText(date);
            textViewNOV.setText(numOfVol);
            textViewLocation.setText(loc);
            textViewDes.setText(des);

        }


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backbtn:
                finish();
                startActivity(new Intent(this, OrgProcessActivity.class));
                break;

        }

    }
}
