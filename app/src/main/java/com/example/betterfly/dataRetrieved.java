package com.example.betterfly;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dataRetrieved extends AppCompatActivity {

    private ListView listView;
    DatabaseReference databaseReference;
    List<Organization>organizationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_retrieved);

        listView=findViewById(R.id.list_view);

        databaseReference= FirebaseDatabase.getInstance().getReference("Organization");

        organizationList=new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot organizationSnapshot:dataSnapshot.getChildren()){

                   Organization organization=organizationSnapshot.getValue(Organization.class);
                   organizationList.add(organization);

               }
                organizationInfoAdaptor organizationInfoAdaptor=new organizationInfoAdaptor(dataRetrieved.this,organizationList);
               listView.setAdapter(organizationInfoAdaptor);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
