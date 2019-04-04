package com.example.betterfly;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;


public class eventRetrievd extends AppCompatActivity  implements View.OnClickListener{

    ListView listView;
    DatabaseReference databaseReference;
    public  List<event>eventList;
    public List<String>eventsName;
    SearchView searchView;
    int h,pre;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    startActivity(new Intent(eventRetrievd.this,eventRetrievd.class));
                    finish();

                    return true;

                case R.id.profile:

                    startActivity(new Intent(eventRetrievd.this, vHome.class));
                    finish();

                    return true;

                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(eventRetrievd.this, MainActivity.class));
                    finish();
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Search");
        toolbar.setTitleTextColor(Color.parseColor("#708090"));

        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.list_view);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Events");
        searchView.setQueryHint("Search..");
        eventList = new ArrayList<event>();
        eventsName = new ArrayList<String>();

        // toolbar.setOnClickListener(this);
        searchView.onActionViewCollapsed();

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String userEmail=user.getEmail().substring(0,user.getEmail().indexOf('@'));
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Volunteer");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {

                    event eventObj = eventSnapshot.getValue(event.class);
                    if (eventList.contains(eventObj)||eventObj.date.getTime()<System.currentTimeMillis())
                        continue;
                    else {
                        eventList.add(eventObj);
                        eventsName.add(eventObj.name);
                    }

                }
                eventinfoAdaptorv eventinfoAdaptor = new eventinfoAdaptorv(eventRetrievd.this, eventList);
                listView.setAdapter(eventinfoAdaptor);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        reference.child(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Volunteer v=  dataSnapshot.getValue(Volunteer.class);
                h=v.hours;
                pre=v.preHours;

                if(pre < h){
                    buclick();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<event> list;
                if (query != null && !query.isEmpty()) {
                    list = new ArrayList<event>();
                    for (int i = 0; i < eventList.size(); i++) {

                        if (eventList.get(i).name.contains(query))
                            list.add(eventList.get(i));
                    }

                    eventinfoAdaptorv eventinfoAdaptor = new eventinfoAdaptorv(eventRetrievd.this, list);
                    listView.setAdapter(eventinfoAdaptor);
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //  getSupportActionBar().setTitle("");
                List<event> list;
                if (newText != null && !newText.isEmpty()) {
                    list  = new ArrayList<event>();
                    for (int i =0 ; i <eventList.size() ; i++) {

                        if (eventList.get(i).name.contains(newText))
                            list.add(eventList.get(i));
                    }

                    eventinfoAdaptorv eventinfoAdaptor = new eventinfoAdaptorv(eventRetrievd.this, list);
                    listView.setAdapter(eventinfoAdaptor);


                }
                else{
                    eventinfoAdaptorv adapter = new eventinfoAdaptorv(eventRetrievd.this, eventList);
                    listView.setAdapter(adapter);
                }

                return true;
            }

        });


    }

    public void buclick(){

        Intent intent = new Intent(this, eventRetrievd.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "121212")
                .setContentTitle("Great News")
                .setContentText("You gain a new badge!!!")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(4000, builder.build());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


}