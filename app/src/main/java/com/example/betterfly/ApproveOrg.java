package com.example.betterfly;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ApproveOrg extends AppCompatActivity implements View.OnClickListener {

  //  private DatabaseReference orgRef;
    private TextView textViewName , textViewEmail, textViewApprovalID;

    Organization organization;
    public String orgName;
    public String orgID;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_org);

        databaseReference= FirebaseDatabase.getInstance().getReference("Organization");


        findViewById(R.id.accept).setOnClickListener(this);
        findViewById(R.id.reject).setOnClickListener(this);
         textViewName = findViewById(R.id.nametext);
         textViewEmail = findViewById(R.id.emailText);
         textViewApprovalID = findViewById(R.id.approvalIDtext3);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        organization= (Organization) intent.getSerializableExtra("organization");
         if(bundle!=null){
             orgName = (String) bundle.get("name");
             String orgEmail =(String) bundle.get("email");
             orgID = (String) bundle.get("ApprovalId") ;

             textViewName.setText(orgName);
             textViewEmail.setText(orgEmail);
             textViewApprovalID.setText(orgID);

         }


    }







        @Override
    public void onClick(View view){


        switch (view.getId()){
            case R.id.accept:
                organization.setStatus(Status.APPROVED);


                FirebaseDatabase.getInstance().getReference("Organization").child(orgID).setValue(organization);
                finish();
                startActivity(new Intent(this, dataRetrieved.class));


                break;

            case R.id.reject:
                DatabaseReference orgRef = FirebaseDatabase.getInstance().getReference("Organization").child(orgID);
                orgRef.removeValue();
                finish();
                startActivity(new Intent(this, dataRetrieved.class));
        }

        }

    }

