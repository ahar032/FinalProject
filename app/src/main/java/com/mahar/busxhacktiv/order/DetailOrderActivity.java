package com.mahar.busxhacktiv.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahar.busxhacktiv.R;
import com.mahar.busxhacktiv.bus.Bus;
import com.mahar.busxhacktiv.model.BusInfo;
import com.mahar.busxhacktiv.model.Order;
import com.mahar.busxhacktiv.model.User;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class DetailOrderActivity extends AppCompatActivity {
    TextView username,dateorder,orderid,phone,rateCard,tickets,dateCard,ratebtn,status,detailstatus,harga,selesai,amount,lseat,departure,arrival,ddeparture,darrival;
    FirebaseDatabase database;
    DatabaseReference reference;
    RatingBar rb,rb2;
    LinearLayout ly5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();

        ratebtn=findViewById(R.id.ratebtn);
        selesai=findViewById(R.id.selesai);
        username=findViewById(R.id.userName);
        dateorder=findViewById(R.id.dateOrder);
        orderid=findViewById(R.id.orderId);
        phone=findViewById(R.id.phone);
        tickets=findViewById(R.id.tickets);
        status=findViewById(R.id.status);
        detailstatus=findViewById(R.id.detailstatus);
        harga=findViewById(R.id.harga);
        amount=findViewById(R.id.amount);
        departure=findViewById(R.id.departure);
        ddeparture=findViewById(R.id.date_departure);
        arrival=findViewById(R.id.arrival);
        darrival=findViewById(R.id.date_arrival);
        lseat=findViewById(R.id.list_seat);
        rb2=findViewById(R.id.rb2);
        ly5=findViewById(R.id.ly5);

        BusInfo busInfo=(BusInfo) getIntent().getSerializableExtra("bus");
        Order order=(Order) getIntent().getSerializableExtra("order");

        reference.child("User").child(order.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User user=snapshot.getValue(User.class);
                    username.setText(user.getFullname());
                    phone.setText(user.getPhone());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        orderid.setText(order.getOrderId());
        tickets.setText(order.getTickets());

        status.setText(order.getStatus());
        if(order.getDetailStatus().equals("2")){
            detailstatus.setText("On going");
        }
        else if(order.getDetailStatus().equals("3")){
            selesai.setVisibility(View.INVISIBLE);
            detailstatus.setText("Done");
            if(order.getRate()==null){
                ratebtn.setVisibility(View.VISIBLE);
            }else{
                rb2.setRating(order.getRate());
                rb2.setVisibility(View.VISIBLE);
                ly5.setVisibility(View.VISIBLE);
            }
        }
        ratebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingpopup(view);
            }
        });

        departure.setText(busInfo.getDeparture());
        arrival.setText(busInfo.getArrival());

        String dateDeparture=busInfo.getDate();
        String dateArrival=busInfo.getDateArrival();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("EEE, d MMM yyyy", Locale.ENGLISH);

        LocalDate ld = LocalDate.parse(dateDeparture, dtf);
        LocalDate ld2 = LocalDate.parse(dateArrival, dtf);

        ddeparture.setText(dtf3.format(ld));
        darrival.setText(dtf3.format(ld2));

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order newOrder=(Order) getIntent().getSerializableExtra("order");
                newOrder.setDetailStatus("3");
                reference.child("Orders").child(newOrder.getOrderId()).setValue(newOrder);
                Intent i=new Intent(DetailOrderActivity.this, Bus.class);
                startActivity(i);
            }
        });

        String seat=order.getDescripsi().toString().trim().replaceAll("[()\\[\\]]", "");
        lseat.setText(seat);

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String finalAmount = formatter.format(Integer.parseInt(order.getAmount()));
        amount.setText(finalAmount);
        harga.setText(finalAmount);

        Date tgl = new Date(order.getDate());
        SimpleDateFormat sdf=new SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH);
        String tanggal=sdf.format(tgl);
        dateorder.setText(tanggal);
    }
    public void ratingpopup(View view){
        AlertDialog.Builder dialog = new AlertDialog.Builder(DetailOrderActivity.this);
        LayoutInflater inflater =(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View ratingpopup=inflater.inflate(R.layout.popup_rating,null);
        BusInfo busInfo=(BusInfo) getIntent().getSerializableExtra("bus");
        dateCard=ratingpopup.findViewById(R.id.date_departure);
        rb=ratingpopup.findViewById(R.id.rb);
        rateCard=ratingpopup.findViewById(R.id.rate);
        dialog.setView(ratingpopup);
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();

        String dateDeparture=busInfo.getDate();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("EEE, d MMM yyyy", Locale.ENGLISH);
        LocalDate ld = LocalDate.parse(dateDeparture, dtf);
        dateCard.setText(dtf3.format(ld));
        rateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rb.getRating()!=0.0f){
                    Order newOrder=(Order) getIntent().getSerializableExtra("order");
                    newOrder.setRate(rb.getRating());
                    reference.child("Orders").child(newOrder.getOrderId()).setValue(newOrder);
                    Intent i=new Intent(DetailOrderActivity.this, Bus.class);
                    startActivity(i);
                }else{
                    Toast.makeText(DetailOrderActivity.this, "please give your rating", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}