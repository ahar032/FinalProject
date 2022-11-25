package com.mahar.busxhacktiv.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mahar.busxhacktiv.R;

import java.util.ArrayList;
import java.util.List;

public class ChooseSeatActivity extends AppCompatActivity {
    Button a1,a2,a3,a4,b1,b2,b3,b4,c1,c2,c3,c4,d1,d2,d3,d4,e1,e2,e3,e4,f1,f2,f3,f4;
    ArrayList<String> tickets = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat);
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
        if(tickets.size()<=maxtickets-1){
            if(tickets.contains(s)){
                tickets.remove(s);
                b.setBackgroundResource(android.R.drawable.btn_default_small);
            }else if(!tickets.contains(s)){
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