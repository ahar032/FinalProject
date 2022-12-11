package com.mahar.busxhacktiv.payment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahar.busxhacktiv.R;
import com.mahar.busxhacktiv.model.BusInfo;
import com.mahar.busxhacktiv.model.Order;
import com.mahar.busxhacktiv.model.User;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class PaymentDetailActivity extends AppCompatActivity {
    TextView userName,phone,tickets,dDeparture,dArrival,departure,arrival,totalTicket,listticket,amount;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button btn;
    int totalamount;
    ImageView goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
        auth=FirebaseAuth.getInstance();

        userName=findViewById(R.id.userName);
        phone=findViewById(R.id.phone);
        tickets=findViewById(R.id.tickets);
        departure=findViewById(R.id.departure);
        arrival=findViewById(R.id.arrival);
        dDeparture=findViewById(R.id.date_departure);
        dArrival=findViewById(R.id.date_arrival);
        totalTicket=findViewById(R.id.txt_ticket);
        listticket=findViewById(R.id.list_seat);
        amount=findViewById(R.id.amount);
        goback=findViewById(R.id.goback);
        btn=findViewById(R.id.btnpayment);
        listticket.setText(getIntent().getStringExtra("lTickets").replaceAll("[()\\[\\]]", ""));
//        int total amount
        totalamount=166000*Integer.parseInt(getIntent().getStringExtra("tickets"));
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String finalAmount = formatter.format(totalamount);
        amount.setText(finalAmount);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("EEE, d MMM yyyy", Locale.ENGLISH);
        String tgl=getIntent().getStringExtra("date");
        LocalDate ld = LocalDate.parse(tgl, dtf);
        dDeparture.setText(dtf3.format(ld));
        Date dt;
        try {
            dt = dateFormat.parse(tgl);
            assert dt != null;
            Date tomorrow = new Date(dt.getTime() + (1000 * 60 * 60 * 24));

            String dateA= dateFormat.format(tomorrow);
            LocalDate l = LocalDate.parse(dateA, dtf);

            dArrival.setText(dtf3.format(l));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tickets.setText(getIntent().getStringExtra("tickets"));
        totalTicket.setText(getIntent().getStringExtra("tickets")+" X 166.000,00");
        arrival.setText(getIntent().getStringExtra("arrival"));
        departure.setText(getIntent().getStringExtra("departure"));
        reference.child("User").child(Objects.requireNonNull(auth.getUid())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                userName.setText(user.getFullname());
                phone.setText(user.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("EEE, d MMM yyyy", Locale.ENGLISH);
                String tglArrival=dArrival.getText().toString();
                LocalDate ld = LocalDate.parse(tglArrival, dtf3);

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);

                String seat=listticket.getText().toString().trim();

                String tglTiba=dtf.format(ld);

                BusInfo busInfo=new BusInfo();
                busInfo.setDateArrival(tglTiba);
                busInfo.setDate(getIntent().getStringExtra("date"));
                busInfo.setArrival(getIntent().getStringExtra("arrival"));
                busInfo.setBusId(getIntent().getStringExtra("busId"));
                busInfo.setBusName(getString(R.string.sempati_star));
                busInfo.setPlateNo(getString(R.string.p_1963_nm));
                busInfo.setTimeArrival(getString(R.string._09_30));
                busInfo.setTimeDeparture(getString(R.string._17_30));
                busInfo.setDeparture(getIntent().getStringExtra("departure"));

                ArrayList<String> list=new ArrayList<>(Arrays.asList(seat.split(",")));
                ArrayList<String> ls=new ArrayList<>();

                for(String i:list){
                    ls.add(i.toString().trim());
                }
                Order order=new Order();
                order.setTickets(getIntent().getStringExtra("tickets"));
                order.setDescripsi(ls);
                order.setAmount(String.valueOf(totalamount));
                order.setBusId(getIntent().getStringExtra("busId"));
                order.setUserId(auth.getUid());
                Intent i=new Intent(PaymentDetailActivity.this,ChoosePaymentActivity.class);
                i.putExtra("order", order);
                i.putExtra("bus",busInfo);
                startActivity(i);
            }
        });
    }
}