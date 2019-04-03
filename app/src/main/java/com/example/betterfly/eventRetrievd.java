package com.example.betterfly;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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


public class eventRetrievd extends AppCompatActivity {

    private ListView listView;
    DatabaseReference databaseReference;
    public  List<event>eventList;
    public List<String>eventsName;
    MaterialSearchView searchView ;
    int h,pre;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    startActivity(new Intent(eventRetrievd.this,eventRetrievd.class));

                    return true;

                case R.id.profile:

                    startActivity(new Intent(eventRetrievd.this, vHome.class));

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

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search for events");
        toolbar.setTitleTextColor(Color.parseColor("#708090"));

        searchView = (MaterialSearchView)findViewById(R.id.search_view);
        listView=findViewById(R.id.list_view);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Events");

        eventList=new ArrayList<event>();
        eventsName= new ArrayList<String>();


        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String userid=user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Volunteer");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {

                    event eventObj = eventSnapshot.getValue(event.class);
                    if(eventList.contains(eventObj) || eventObj.date.getTime()<System.currentTimeMillis() )
                        continue;
                    else{
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
        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue().toString();
                String ch= dataSnapshot.child("hours").getValue().toString();
                String pch= dataSnapshot.child("preHours").getValue().toString();
                h=Integer.parseInt(ch);
                pre=Integer.parseInt(pch);

                if(pre < h){
                    buclick();
                }

               }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

                eventinfoAdaptorv eventinfoAdaptor = new eventinfoAdaptorv(eventRetrievd.this, eventList);
                listView.setAdapter(eventinfoAdaptor);
            }

            @Override
            public void onSearchViewClosed() {

                eventinfoAdaptorv eventinfoAdaptor = new eventinfoAdaptorv(eventRetrievd.this, eventList);
                listView.setAdapter(eventinfoAdaptor);

            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty()) {
                    List<event> list = new ArrayList<event>();
                    for (int i =0 ; i <eventList.size() ; i++) {

                        if (eventList.get(i).name.contains(newText))
                            list.add(eventList.get(i));
                    }

                    eventinfoAdaptorv eventinfoAdaptor = new eventinfoAdaptorv(eventRetrievd.this, list);
                    listView.setAdapter(eventinfoAdaptor);



                }
                else{
                    ArrayAdapter adapter = new ArrayAdapter(eventRetrievd.this, android.R.layout.simple_list_item_1,eventsName );
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }


}