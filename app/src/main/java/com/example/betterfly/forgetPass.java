package com.example.betterfly;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPass extends AppCompatActivity {


    FirebaseAuth mAuth;
    EditText editTextEmail;
    Button set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        set = findViewById(R.id.buttonSet);


        set.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();

                if(email.equals("")){
                    Toast.makeText(forgetPass.this, "Pleas enter your registered Email address", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(forgetPass.this,"Password reset email sent!!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(forgetPass.this, MainActivity.class));
                            }else {
                                Toast.makeText(forgetPass.this,"Error in sending password reset email!!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
    }
}