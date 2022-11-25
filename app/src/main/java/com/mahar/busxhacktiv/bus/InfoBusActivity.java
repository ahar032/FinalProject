package com.mahar.busxhacktiv.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahar.busxhacktiv.R;
import com.mahar.busxhacktiv.model.Provinsi;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class InfoBusActivity extends AppCompatActivity {
    Button bookNow;
    TextView tDeparture,tArrival,departure,arrival,passager,date,dateCard,jPassagers,harga;
    ImageView goback;
    FirebaseDatabase database;
    DatabaseReference reference;

    String busInfo;
    @Override
    protected void onStart() {
        super.onStart();
        if(getIntent()!=null){
            tArrival.setText(getIntent().getStringExtra("tArrival"));
            tDeparture.setText(getIntent().getStringExtra("tDeparture"));
            departure.setText(getIntent().getStringExtra("departure"));
            arrival.setText(getIntent().getStringExtra("arrival"));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);

            DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("EEE, d MMM yyyy", Locale.ENGLISH);
            String tgl=getIntent().getStringExtra("date");
            //                Date tanggal=sdf.parse(tgl);
            LocalDate ld = LocalDate.parse(tgl, dtf);
            String fulldate=dtf3.format(ld);
            String tanggal=dtf2.format(ld);
            dateCard.setText(tanggal);
            date.setText(fulldate);

            passager.setText(getIntent().getStringExtra("passagers"));
            jPassagers.setText(getIntent().getStringExtra("passagers"));
            double pg=Double.parseDouble(passager.getText().toString());
            double tHarga= 166000 * pg;
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String finalAmount = formatter.format(tHarga);
            harga.setText(finalAmount);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_bus);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
        tDeparture=findViewById(R.id.time_departure);
        tArrival=findViewById(R.id.time_arrival);

        departure=findViewById(R.id.departure);
        arrival=findViewById(R.id.arrival);

        date=findViewById(R.id.date);
        dateCard=findViewById(R.id.date_card);

        harga=findViewById(R.id.harga);
        passager=findViewById(R.id.passangers);
        jPassagers=findViewById(R.id.jPassagers);
        goback=findViewById(R.id.goback);
        bookNow=findViewById(R.id.btnAdd);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tgl=getIntent().getStringExtra("date");
                String amount=harga.getText().toString();
                String jTicket=jPassagers.getText().toString();
                reference.child("Provinsi").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String dep = null,arri = null,iddep = null,idarri = null;
                            busInfo=getIntent().getStringExtra("date").trim().replaceAll("[^0-9]", "");
                            for (DataSnapshot sp:snapshot.getChildren()){
                                Provinsi provinsi=sp.getValue(Provinsi.class);
                                if(getIntent().getStringExtra("departure")!=null && provinsi.getName().contains(getIntent().getStringExtra("departure"))){
                                    dep= provinsi.getName();
                                    iddep=provinsi.getId();
                                    busInfo=busInfo+"/"+provinsi.getId();
                                }else if(getIntent().getStringExtra("arrival")!=null && provinsi.getName().contains(getIntent().getStringExtra("arrival"))){
                                    arri=provinsi.getName();
                                    idarri= provinsi.getId();
                                    busInfo=busInfo+"?"+provinsi.getId();
                                }
                            }
                            Log.i("provinsi",busInfo);
                            Intent i=new Intent(InfoBusActivity.this,ChooseSeatActivity.class);
                            i.putExtra("busId",busInfo);
                            i.putExtra("date",tgl);
                            i.putExtra("amount",amount);
                            i.putExtra("tickets",jTicket);
                            i.putExtra("departure",dep);
                            i.putExtra("iddeparture",iddep);
                            i.putExtra("idarrival",idarri);
                            i.putExtra("arrival",arri);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                BusID=tgl/kode-departure/kode-arrival/

//                String
            }
        });
    }


}