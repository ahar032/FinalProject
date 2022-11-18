package com.mahar.busxhacktiv.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mahar.busxhacktiv.R;
import com.mahar.busxhacktiv.bus.Bus;
import com.mahar.busxhacktiv.model.User;

public class FinishRegister extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    Button btn;
    ImageView goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_register);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();

        btn=findViewById(R.id.btn);
        goback=findViewById(R.id.goback);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn.setEnabled(false);
                Intent i=getIntent();
                String phone=i.getStringExtra("phone");
                addUser(phone);
            }
        });
    }
    public void addUser(String phone){
        FirebaseUser currentUser=auth.getCurrentUser();
        if(currentUser!=null){

            User user= new User();
            user.setEmail(currentUser.getEmail());
            user.setUid(currentUser.getUid());
            user.setPicture(currentUser.getPhotoUrl().toString());
            user.setFullname(currentUser.getDisplayName());
            user.setPhone(phone);

            reference.child("User").child(currentUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Intent i= new Intent(FinishRegister.this, Bus.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(FinishRegister.this, "gagal register", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(FinishRegister.this,AddPhoneActivity.class);
                        startActivity(i);
                    }
                }
            });
        }
    }
}