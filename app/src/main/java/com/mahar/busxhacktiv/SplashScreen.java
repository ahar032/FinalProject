package com.mahar.busxhacktiv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahar.busxhacktiv.auth.SignUpActivity;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user=auth.getCurrentUser();
                if(user!=null){
                    reference.child("User").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
//                                TODO: pindahkan halaman ke dashboard
                            }else if(!snapshot.exists()){
//                                TODO: pindahkan halaman ke halaman phone
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
//                    TODO: PINDAHKAN halaman ke halaman SignUp
                    Intent signUp=new Intent(SplashScreen.this,SignUpActivity.class);
                    startActivity(signUp);
                }

            }
        },4*1000);

//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent i = new Intent(SplashScreen.this, WelcomeActivity.class);
//                startActivity(i);
//                finish();
//
//            }
//
//        }, 5*1000);
    }
}