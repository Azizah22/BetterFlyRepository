package com.example.betterfly;


import com.google.firebase.FirebaseApp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        findViewById(R.id.buttonaorg).setOnClickListener(this);
        findViewById(R.id.buttonv).setOnClickListener(this);
        findViewById(R.id.buttonadmin).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewSignup:
                finish();
                startActivity(new Intent(this, SignUpActivity.class));
                break;

            case R.id.buttonv:
                finish();
                startActivity(new Intent(this, vLogin.class));
                break;
            case R.id.buttonaorg:
                finish();
                startActivity(new Intent(this, orgLogin.class));
                break;

            case R.id.buttonadmin:
                finish();
                startActivity(new Intent(this, adminLogin.class));
                break;
        }
    }
}
