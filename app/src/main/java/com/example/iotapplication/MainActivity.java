package com.example.iotapplication;

import androidx.annotation.NonNull;
import java.lang.Integer;
import java.util.Objects;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Dialog contact ;
    TextView t1,t2,h1,h2,a1,a2,msg;
    Button addContact;

    // connecting to firebase
    DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("data");
    EditText editTextPersonContact;
    String contactNumber="8309821338",message;
    int te1,te2,hu1,hu2,ai1,ai2;
    int tw= 45;
    int hw=40;
    int aw=1500;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
        setContentView(R.layout.activity_main);
        t1 =findViewById(R.id.t1);
        t2 =findViewById(R.id.t2);
        h1 =findViewById(R.id.h1);
        h2 =findViewById(R.id.h2);
        a1 =findViewById(R.id.a1);
        a2 =findViewById(R.id.a2);
        addContact= findViewById(R.id.addContact);
        editTextPersonContact = (EditText) findViewById(R.id.editTextPersonContact);
        final SmsManager smsManager = SmsManager.getDefault();

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactNumber = editTextPersonContact.getText().toString();
                Toast.makeText(MainActivity.this,"Conatct saved",Toast.LENGTH_SHORT).show();
                editTextPersonContact.setText("Change contact");
            }
        });

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String area = Objects.requireNonNull(dataSnapshot.child("area").getValue()).toString();
                String temp = Objects.requireNonNull(dataSnapshot.child("temp").getValue()).toString();
                String air = Objects.requireNonNull(dataSnapshot.child("air").getValue()).toString();
                String humid = Objects.requireNonNull(dataSnapshot.child("humid").getValue()).toString();
                //data for area 1
                if (area.equals("1")) {
                    te1=Integer.parseInt(temp);
                    if (te1>tw){
                        message = "Temperature in area 1 is high ";
                        smsManager.sendTextMessage(contactNumber,null,message,null,null);
                    }
                    String temperature = temp+"\u2103";
                    t1.setText(temperature);

                    hu1=Integer.parseInt(humid);
                    if (hu1>hw){
                        message = "Humidity in area 1 is high ";
                        smsManager.sendTextMessage(contactNumber,null,message,null,null);
                    }
                    String humidity=humid+"%";
                    h1.setText(humidity);


                    ai1=Integer.parseInt(air);
                    if (ai1>aw){
                        message = "Air quality in area 1 is not moderate ";
                        smsManager.sendTextMessage(contactNumber,null,message,null,null);
                    }
                    String airquality=air+" ppm";
                    a1.setText(airquality);

                }

                //data for area 2
                if (area.equals("2")) {
                    te2=Integer.parseInt(temp);
                    if (te2>tw){
                        message = "Temperature in area 2 is high";
                        smsManager.sendTextMessage(contactNumber,null,message,null,null);
                    }
                    String temperature = temp+"\u2103";
                    t2.setText(temperature);


                    hu2=Integer.parseInt(humid);
                    if (hu2>hw){
                        message = "Humidity in area 2 is high";
                        smsManager.sendTextMessage(contactNumber,null,message,null,null);
                    }
                    String humidity=humid+"%";
                    h2.setText(humidity);

                    ai2=Integer.parseInt(air);
                    if (ai2>aw){
                        message = "Air quality in area 2 is not moderate";
                        smsManager.sendTextMessage(contactNumber,null,message,null,null);
                    }
                    String airquality=air+" ppm";
                    a2.setText(airquality);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
