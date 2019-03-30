package com.example.betterfly;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrgProcessActivity extends AppCompatActivity {
    private static final String TAG = "ViewDatabase";
    private ListView listView;
    DatabaseReference databaseReference;
   private FirebaseAuth.AuthStateListener mAuthListener;
    List<event> eventsList;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private  String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_process);
        listView=(ListView) findViewById(R.id.list_view1);
        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Events");
        user=mAuth.getCurrentUser();
        userID = user.getUid();
        eventsList=new ArrayList<>();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        }

    @Override

    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot eventSnapshot:dataSnapshot.getChildren()){
                    String org=eventSnapshot.child("org").getValue().toString();
                    if(userID.equals(org)) {

                        event event1 = eventSnapshot.getValue(event.class);
                        eventsList.add(event1);
                    }
                }
                eventInfoAdaptor eventinfoAdaptor=new eventInfoAdaptor(OrgProcessActivity.this, eventsList);
                listView.setAdapter(eventinfoAdaptor);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    startActivity(new Intent(OrgProcessActivity.this,OrgProcessActivity.class));
                    finish();

                    return true;

                case R.id.post:
                    startActivity(new Intent(OrgProcessActivity.this, postEvent.class));
                    finish();

                    return true;

                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(OrgProcessActivity.this, MainActivity.class));
                    finish();
                    return true;
            }
            return false;
        }
    };
}

