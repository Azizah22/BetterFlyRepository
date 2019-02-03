package com.example.betterfly;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ApproveOrg extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference orgRef;
    private TextView textViewName , textViewEmail, textViewApprovalID;
    private String orgKey;
    private Organization organization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_org);

        // Get org Key from intent
      //  orgKey = getIntent().getStringExtra()

        //Initialize Database


         textViewName = findViewById(R.id.name);
         textViewEmail = findViewById(R.id.email);
         textViewApprovalID = findViewById(R.id.Aproval_ID);
    }

    public void orgOproval(Organization org){
        organization = org;
        DataSnapshot dataSnapshot = null;
                // Get Post object and use the values to update the UI
                 org = dataSnapshot.getValue(Organization.class);
                // [START_EXCLUDE]
                textViewName.setText(org.getName());
                textViewEmail.setText(org.getEmail());
                textViewApprovalID.setText(org.getApprovalId());
                // [END_EXCLUDE]


        }



        @Override
    public void onClick(View view){

        switch (view.getId()){
            case R.id.accept:
                organization.setStatus(Status.APPROVED);
                break;

            case R.id.reject:
                DatabaseReference orgRef = FirebaseDatabase.getInstance().getReference("Organization").child(organization.email);
                orgRef.removeValue();



















                

        }

        }

    }

