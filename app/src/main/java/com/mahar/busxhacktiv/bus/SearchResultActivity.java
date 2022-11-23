package com.mahar.busxhacktiv.bus;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahar.busxhacktiv.R;

public class SearchResultActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> activityResultLauncherForLocation;
    TextView departure,arrival,card_departure,card_arrival,date,seat,card_arrival_disable,card_departure_disable;
    TextView tDeparture,tArrival;
    ImageView where,from,goback;
    Button btnBookNow;
    @Override
    protected void onStart() {
        super.onStart();
        if(getIntent()!=null){
            departure.setText(getIntent().getStringExtra("departure"));
            arrival.setText(getIntent().getStringExtra("arrival"));

            date.setText(getIntent().getStringExtra("date"));


            String seats=getIntent().getStringExtra("passagers");
            seat.setText(seats +" seat");

            card_arrival.setText(arrival.getText().toString());
            card_departure.setText(departure.getText().toString());

            card_arrival_disable.setText(arrival.getText().toString());
            card_departure_disable.setText(departure.getText().toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        RegisterActivityForLocation();

        goback=findViewById(R.id.goback);

        departure=findViewById(R.id.departure);
        arrival=findViewById(R.id.arrival);
        card_arrival=findViewById(R.id.card_arrival);
        card_departure=findViewById(R.id.card_departure);
        card_arrival_disable=findViewById(R.id.card_arrival_disable);
        card_departure_disable=findViewById(R.id.card_departure_disable);

        date=findViewById(R.id.date);
        seat=findViewById(R.id.seat);
        where=findViewById(R.id.where);
        from=findViewById(R.id.from);

        tDeparture=findViewById(R.id.time_departure);
        tArrival=findViewById(R.id.time_arrival);

        btnBookNow=findViewById(R.id.bookNow);

        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tgl=date.getText().toString().trim();
                String passager=seat.getText().toString().trim().replaceAll("[^0-9]", "");
                String timeDeparture=tDeparture.getText().toString().trim();
                String timeArrival=tArrival.getText().toString().trim();
                String tujuan=arrival.getText().toString().trim();
                String lokasi=departure.getText().toString().trim();

                Intent i=new Intent(SearchResultActivity.this,InfoBusActivity.class);
                i.putExtra("date",tgl);
                i.putExtra("passagers",passager);
                i.putExtra("tDeparture",timeDeparture);
                i.putExtra("tArrival",timeArrival);
                i.putExtra("departure",lokasi);
                i.putExtra("arrival",tujuan);
                startActivity(i);
            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        where.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SearchResultActivity.this,ListProvinsi.class);
                activityResultLauncherForLocation.launch(i);
            }
        });
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SearchResultActivity.this,ListProvinsi.class);
                activityResultLauncherForLocation.launch(i);
            }
        });

    }
    public void RegisterActivityForLocation(){
        activityResultLauncherForLocation=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int ResultCode=result.getResultCode();
                        Intent data=result.getData();

                        if(ResultCode==RESULT_OK && data !=null){
                            arrival.setText(data.getStringExtra("arrival"));
                            departure.setText(data.getStringExtra("departure"));

                            card_arrival.setText(arrival.getText().toString());
                            card_departure.setText(departure.getText().toString());

                            card_departure_disable.setText(departure.getText().toString());
                            card_arrival_disable.setText(arrival.getText().toString());
                        }
                    }
                });
    }

}