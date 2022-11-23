package com.mahar.busxhacktiv.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahar.busxhacktiv.R;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class InfoBusActivity extends AppCompatActivity {

    TextView tDeparture,tArrival,departure,arrival,passager,date,dateCard,jPassagers,harga;
    ImageView goback;
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

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}