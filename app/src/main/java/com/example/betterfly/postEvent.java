package com.example.betterfly;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.util.Locale;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class postEvent extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "postEvent";
    private ProgressBar progressBar;
    EditText editTextloc, editTextName, editTextDoB,editTextch,editTextmv, editTextdes;
    DatePickerDialog.OnDateSetListener datePickerDoB;
    String date;
    Date DoB;

    private FirebaseAuth mAuth;
    @Override
    public void onClick(View view){

        }

    }
}
