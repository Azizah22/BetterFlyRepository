package com.example.betterfly;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class vHome extends AppCompatActivity implements View.OnClickListener {

    int h=9;
    TextView TextViewName,TextViewHours;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);

            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            String userid=user.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Volunteer");
            TextViewName = findViewById(R.id.textViewName);
            TextViewHours = findViewById(R.id.textViewHours);
            findViewById(R.id.coloring).setOnClickListener(this);
            reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name=dataSnapshot.child("name").getValue().toString();
                    String ch= dataSnapshot.child("hours").getValue().toString();
                    h=Integer.parseInt(ch);
                    TextViewName.setText(name);
                    TextViewHours.setText("Your Total Volunteering Hours is "+h);
                    changeLevel();}

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            });


            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void changeLevel() {
        ImageView imageView;
        int color = ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null);
            if (h < 3){
                imageView= findViewById(R.id.level1);
                imageView.setColorFilter(color);
            }
            if (h<7){
                imageView= findViewById(R.id.level2);
                imageView.setColorFilter(color);
            }
            if (h<10){
                imageView= findViewById(R.id.level3);
                imageView.setColorFilter(color);
            }
            if (h<15){
                imageView= findViewById(R.id.level4);
                imageView.setColorFilter(color);
            }
            if (h<18){
            imageView= findViewById(R.id.level5);
            imageView.setColorFilter(color);
            }
            if (h<21){
            imageView= findViewById(R.id.level6);
            imageView.setColorFilter(color);
            }
        return;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.coloring:
                ImageView imageview;
                int color = ResourcesCompat.getColor(getResources(), R.color.wall, null);
                imageview= findViewById(R.id.wall);
                imageview.setColorFilter(color);
                break;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
             = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    startActivity(new Intent(vHome.this,eventRetrievd.class));
                    finish();

                    return true;

                case R.id.profile:
                    startActivity(new Intent(vHome.this, vHome.class));
                    finish();

                    return true;

                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(vHome.this, MainActivity.class));
                    finish();
                    return true;
            }
            return false;
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
