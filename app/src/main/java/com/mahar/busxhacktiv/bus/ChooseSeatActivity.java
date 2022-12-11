package com.mahar.busxhacktiv.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.mahar.busxhacktiv.R;

import com.mahar.busxhacktiv.model.Order;
import com.mahar.busxhacktiv.payment.PaymentDetailActivity;
import java.util.ArrayList;
import java.util.Objects;


public class ChooseSeatActivity extends AppCompatActivity {
    Button a1,a2,a3,a4,b1,b2,b3,b4,c1,c2,c3,c4,d1,d2,d3,d4,e1,e2,e3,e4,f1,f2,f3,f4;
    ArrayList<String> OldTickets = new ArrayList<>();
    ArrayList<String> tickets = new ArrayList<>();
    Button payment;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
        payment=findViewById(R.id.btnBooknow);

        reference.child("Orders").orderByChild("busId").equalTo(getIntent().getStringExtra("busId")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Log.i("snapshot","exists");
                    for (DataSnapshot sp:snapshot.getChildren()){
                        Order order=sp.getValue(Order.class);
                        OldTickets.addAll(Objects.requireNonNull(order).getDescripsi());
                    }
                    setButton();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tickets.size()==Integer.parseInt(getIntent().getStringExtra("tickets"))){
                    payment.setEnabled(false);
                    Intent i=new Intent(ChooseSeatActivity.this, PaymentDetailActivity.class);

                    i.putExtra("lTickets",tickets.toString());
                    i.putExtra("busId",getIntent().getStringExtra("busId"));
                    i.putExtra("date",getIntent().getStringExtra("date"));
                    i.putExtra("amount",getIntent().getStringExtra("amount").trim().replaceAll("[^0-9]", ""));
                    i.putExtra("tickets",getIntent().getStringExtra("tickets"));
                    i.putExtra("departure",getIntent().getStringExtra("departure"));
                    i.putExtra("iddeparture",getIntent().getStringExtra("iddeparture"));
                    i.putExtra("idarrival",getIntent().getStringExtra("idarrival"));
                    i.putExtra("arrival",getIntent().getStringExtra("arrival"));

                    startActivity(i);
                }else{

                    Toast.makeText(ChooseSeatActivity.this, "input seat", Toast.LENGTH_SHORT).show();
                }
            }
        });
        a1=findViewById(R.id.a1);
        a2=findViewById(R.id.a2);
        a3=findViewById(R.id.a3);
        a4=findViewById(R.id.a4);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        b3=findViewById(R.id.b3);
        b4=findViewById(R.id.b4);
        c1=findViewById(R.id.c1);
        c2=findViewById(R.id.c2);
        c3=findViewById(R.id.c3);
        c4=findViewById(R.id.c4);
        d1=findViewById(R.id.d1);
        d2=findViewById(R.id.d2);
        d3=findViewById(R.id.d3);
        d4=findViewById(R.id.d4);
        e1=findViewById(R.id.e1);
        e2=findViewById(R.id.e2);
        e3=findViewById(R.id.e3);
        e4=findViewById(R.id.e4);
        f1=findViewById(R.id.f1);
        f2=findViewById(R.id.f2);
        f3=findViewById(R.id.f3);
        f4=findViewById(R.id.f4);

        a1.setOnClickListener(this::onClick);
        a2.setOnClickListener(this::onClick);
        a3.setOnClickListener(this::onClick);
        a4.setOnClickListener(this::onClick);

        b1.setOnClickListener(this::onClick);
        b2.setOnClickListener(this::onClick);
        b3.setOnClickListener(this::onClick);
        b4.setOnClickListener(this::onClick);

        c1.setOnClickListener(this::onClick);
        c2.setOnClickListener(this::onClick);
        c3.setOnClickListener(this::onClick);
        c4.setOnClickListener(this::onClick);

        d1.setOnClickListener(this::onClick);
        d2.setOnClickListener(this::onClick);
        d3.setOnClickListener(this::onClick);
        d4.setOnClickListener(this::onClick);

        e1.setOnClickListener(this::onClick);
        e2.setOnClickListener(this::onClick);
        e3.setOnClickListener(this::onClick);
        e4.setOnClickListener(this::onClick);

        f1.setOnClickListener(this::onClick);
        f2.setOnClickListener(this::onClick);
        f3.setOnClickListener(this::onClick);
        f4.setOnClickListener(this::onClick);

    }
    public void setButton(){
        Log.i("OldTickets", String.valueOf(OldTickets));
        for (String i:OldTickets){
            Log.i("button",i);
            if(i.contains("a1")){
                a1.setEnabled(false);
                a1.setBackgroundColor(Color.parseColor("#F88C99F1"));
            } else if( i.contains("a2")){
                a2.setEnabled(false);
                a2.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if( i.contains("a3")){
                a3.setEnabled(false);
                a3.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(  i.contains("a4")){
                a4.setEnabled(false);
                a4.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if( i.contains("b1")){
                b1.setEnabled(false);
                b1.setBackgroundColor(Color.parseColor("#F88C99F1"));
            } else if(  i.contains("b2")){
                b2.setEnabled(false);
                b2.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if( i.contains("b3")){
                b3.setEnabled(false);
                b3.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if( i.contains("b4")){
                b4.setEnabled(false);
                b4.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if( i.contains("c1")){
                c1.setEnabled(false);
                c1.setBackgroundColor(Color.parseColor("#F88C99F1"));
            } else if(i.contains("c2")){
                c2.setEnabled(false);
                c2.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.contains("c3")){
                c3.setEnabled(false);
                c3.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.contains("c4")){
                c4.setEnabled(false);
                c4.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.contains("d1")){
                d1.setEnabled(false);
                d1.setBackgroundColor(Color.parseColor("#F88C99F1"));
            } else if(i.contains("d2")){
                d2.setEnabled(false);
                d2.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.contains("d3")){
                d3.setEnabled(false);
                d3.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.contains("d4")){
                d4.setEnabled(false);
                d4.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.contains("e1")){
                e1.setEnabled(false);
                e1.setBackgroundColor(Color.parseColor("#F88C99F1"));
            } else if(i.contains("e2")){
                e2.setEnabled(false);
                e2.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.contains("e3")){
                e3.setEnabled(false);
                e3.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.contains("e4")){
                e4.setEnabled(false);
                e4.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.contains("f1")){
                f1.setEnabled(false);
                f1.setBackgroundColor(Color.parseColor("#F88C99F1"));
            } else if(i.contains("f2")){
                f2.setEnabled(false);
                f2.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.contains("f3")){
                f3.setEnabled(false);
                f3.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.contains("f4")){
                f4.setEnabled(false);
                f4.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }
        }
    }

    public void onClick(View view){
        int viewId=view.getId();
        switch (viewId){
            case (R.id.a1):

                    checkButton("a1",a1);
                break;
            case (R.id.a2):
                    checkButton("a2",a2);
                break;
            case (R.id.a3):
                    checkButton("a3",a3);
                break;
            case (R.id.a4):
                checkButton("a4",a4);
                break;
            case (R.id.b1):
                checkButton("b1",b1);
                break;
            case (R.id.b2):
                checkButton("b2",b2);
                break;
            case (R.id.b3):
                checkButton("b3",b3);
                break;
            case (R.id.b4):
                checkButton("b4",b4);
                break;
            case (R.id.c1):
                checkButton("c1",c1);
                break;
            case (R.id.c2):
                checkButton("c2",c2);
                break;
            case (R.id.c3):
                checkButton("c3",c3);
                break;
            case (R.id.c4):
                checkButton("c4",c4);
                break;
            case (R.id.d1):
                checkButton("d1",d1);
                break;
            case (R.id.d2):
                checkButton("d2",d2);
                break;
            case (R.id.d3):
                checkButton("d3",d3);
                break;
            case (R.id.d4):
                checkButton("d4",d4);
                break;
            case (R.id.e1):
                checkButton("e1",e1);
                break;
            case (R.id.e2):
                checkButton("e2",e2);
                break;
            case (R.id.e3):
                checkButton("e3",e3);
                break;
            case (R.id.e4):
                checkButton("e4",e4);
                break;
            case (R.id.f1):
                checkButton("f1",f1);
                break;
            case (R.id.f2):
                checkButton("f2",f2);
                break;
            case (R.id.f3):
                checkButton("f3",f3);
                break;
            case (R.id.f4):
                checkButton("f4",f4);
                break;

        }

    }

    public void checkButton(String s,Button b){
        int maxtickets=Integer.parseInt(getIntent().getStringExtra("tickets"));
        Log.i("oldticket", String.valueOf(OldTickets));
        if(tickets.size()<=maxtickets-1){
            if(tickets.contains(s)){
                tickets.remove(s);
                b.setBackgroundResource(android.R.drawable.btn_default_small);
            }else {
                b.setBackgroundColor(Color.parseColor("#8749F2"));
                tickets.add(s);
            }
        }else{
            if(tickets.contains(s)){
                tickets.remove(s);
                b.setBackgroundResource(android.R.drawable.btn_default_small);
            }
        }
    }
}