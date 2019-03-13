package com.example.betterfly;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static java.net.Proxy.Type.HTTP;


public class ApproveOrg extends AppCompatActivity implements View.OnClickListener {

  //  private DatabaseReference orgRef;
    private TextView textViewName , textViewEmail, textViewApprovalID;

    Organization organization;
    public String orgName;
    public String orgID , password , orgEmail;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    Intent emailIntent;
    Context context = ApproveOrg.this;
    NotificationManager nmanager;//outside the method
    public Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_org);

        databaseReference= FirebaseDatabase.getInstance().getReference("Organization");

        findViewById(R.id.backbtn).setOnClickListener(this);
        findViewById(R.id.Request).setOnClickListener(this);
        findViewById(R.id.reject).setOnClickListener(this);
         textViewName = findViewById(R.id.EvName);
         textViewEmail = findViewById(R.id.OrgName);
         textViewApprovalID = findViewById(R.id.aprove);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        organization= (Organization) intent.getSerializableExtra("organization");
         if(bundle!=null){
             orgName = (String) bundle.get("name");
            // orgEmail =(String) bundle.get("email");
             orgID = (String) bundle.get("ApprovalId") ;

             textViewName.setText(orgName);
             textViewEmail.setText(organization.email);
             textViewApprovalID.setText(orgID);

         }




    }







        @Override
    public void onClick(View view) {


                switch (view.getId()) {
                    case R.id.Request:
                        organization.setStatus(Status.APPROVED);


                        FirebaseDatabase.getInstance().getReference("Organization").child(orgID).setValue(organization);
                     sendEmail("Congratulations your request have been accepted");

                       Toast.makeText(ApproveOrg.this, "The organization has been accepted successfully", Toast.LENGTH_SHORT).show();
                        //finish();
                        buclick();

                        break;

                    case R.id.reject:
                        DatabaseReference orgRef = FirebaseDatabase.getInstance().getReference("Organization").child(orgID);
                        orgRef.removeValue();

                        sendEmail("Sorry to inform you that your request has been rejected. If you have any complains please contact us with this email");

                        Toast.makeText(ApproveOrg.this, "The organization has been deleted successfully", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(this, dataRetrieved.class));
                        buclick();
                        break;
                    case R.id.backbtn:
                        //finish();
                        startActivity(new Intent(this, dataRetrieved.class));
                        break;
                }





        }


    protected void sendEmail(String msg) {

     Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        String[] recipients = new String[]{organization.email, "",};

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);

        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "About your registration request");

        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg);

        emailIntent.setType("text/plain");

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));



       Toast.makeText(ApproveOrg.this, "Waiting ....", Toast.LENGTH_SHORT).show();

    }




    public void buclick(){

        Intent intent = new Intent(this, dataRetrieved.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "121212")
                .setContentTitle("Nice")
                .setContentText("Your email has been sent")
                .setSmallIcon(R.drawable.logo)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(4000, builder.build());
    }




    protected void sendEmailaccept() {

        Toast.makeText(ApproveOrg.this, "Waiting ....", Toast.LENGTH_SHORT).show();

        try {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

            String[] recipients = new String[]{organization.email, "",};

            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);

            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "About your registration request");

            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Congratulations your request have been accepted ");

            emailIntent.setType("text/plain");

            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ApproveOrg.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name ="11111ID";
            String description = "noti";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("121212", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    }

