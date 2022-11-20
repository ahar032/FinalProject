package com.mahar.busxhacktiv.bus;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahar.busxhacktiv.R;

public class SearchResultActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> activityResultLauncherForLocation;
    TextView departure,arrival,card_departure,card_arrival,date,seat;
    ImageView where,from,goback;

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

        date=findViewById(R.id.date);
        seat=findViewById(R.id.seat);
        where=findViewById(R.id.where);
        from=findViewById(R.id.from);

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
                        }
                    }
                });
    }

}