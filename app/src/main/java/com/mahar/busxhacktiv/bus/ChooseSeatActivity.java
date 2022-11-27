package com.mahar.busxhacktiv.bus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.mahar.busxhacktiv.R;
import com.mahar.busxhacktiv.model.BusInfo;
import com.mahar.busxhacktiv.model.Order;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.stripe.android.paymentsheet.PaymentSheetResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChooseSeatActivity extends AppCompatActivity {
    Button a1,a2,a3,a4,b1,b2,b3,b4,c1,c2,c3,c4,d1,d2,d3,d4,e1,e2,e3,e4,f1,f2,f3,f4;
    ArrayList<String> OldTickets = new ArrayList<>();
    ArrayList<String> tickets = new ArrayList<>();
    Button payment;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;


    PaymentSheet paymentSheet;
    String customerId;
    String EphemeralKey;
    String clientSecret;

    @Override
    protected void onStart() {
        super.onStart();
        reference.child("Orders").orderByChild("busId").equalTo(getIntent().getStringExtra("busId")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Log.i("snapshot","exists");
                    for (DataSnapshot sp:snapshot.getChildren()){
//                        Map<String, Object> map = (Map<String, Object>) sp.getValue();
                        Order order=sp.getValue(Order.class);
                        OldTickets.addAll(order.getDescripsi());
                    }
                   setButton();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
        payment=findViewById(R.id.btnBooknow);

        PaymentConfiguration.init(this,getString(R.string.PUBLISH_KEY));
        paymentSheet=new PaymentSheet(this, new PaymentSheetResultCallback() {
            @Override
            public void onPaymentSheetResult(@NonNull PaymentSheetResult paymentSheetResult) {
                onPaymentResult(paymentSheetResult);
            }
        });
//        https://api.stripe.com/v1/customers
//        Create customer

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tickets.size()==Integer.parseInt(getIntent().getStringExtra("tickets"))){
                    payment.setEnabled(false);
                    getCustomerId();
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
            if(i.equals("a1")){
                a1.setEnabled(false);
                a1.setBackgroundColor(Color.parseColor("#F88C99F1"));
            } else if( i.equals("a2")){
                a2.setEnabled(false);
                a2.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if( i.equals("a3")){
                a3.setEnabled(false);
                a3.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(  i.equals("a4")){
                a4.setEnabled(false);
                a4.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if( i.equals("b1")){
                b1.setEnabled(false);
                b1.setBackgroundColor(Color.parseColor("#F88C99F1"));
            } else if(  i.equals("b2")){
                b2.setEnabled(false);
                b2.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if( i.equals("b3")){
                b3.setEnabled(false);
                b3.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if( i.equals("b4")){
                b4.setEnabled(false);
                b4.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if( i.equals("c1")){
                c1.setEnabled(false);
                c1.setBackgroundColor(Color.parseColor("#F88C99F1"));
            } else if(i.equals("c2")){
                c2.setEnabled(false);
                c2.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.equals("c3")){
                c3.setEnabled(false);
                c3.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.equals("c4")){
                c4.setEnabled(false);
                c4.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.equals("d1")){
                d1.setEnabled(false);
                d1.setBackgroundColor(Color.parseColor("#F88C99F1"));
            } else if(i.equals("d2")){
                d2.setEnabled(false);
                d2.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.equals("d3")){
                d3.setEnabled(false);
                d3.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.equals("d4")){
                d4.setEnabled(false);
                d4.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.equals("e1")){
                e1.setEnabled(false);
                e1.setBackgroundColor(Color.parseColor("#F88C99F1"));
            } else if(i.equals("e2")){
                e2.setEnabled(false);
                e2.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.equals("e3")){
                e3.setEnabled(false);
                e3.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.equals("e4")){
                e4.setEnabled(false);
                e4.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.equals("f1")){
                f1.setEnabled(false);
                f1.setBackgroundColor(Color.parseColor("#F88C99F1"));
            } else if(i.equals("f2")){
                f2.setEnabled(false);
                f2.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.equals("f3")){
                f3.setEnabled(false);
                f3.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }else if(i.equals("f4")){
                f4.setEnabled(false);
                f4.setBackgroundColor(Color.parseColor("#F88C99F1"));
            }
        }
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed){
//            JIKA PAYMENT SUCCESS
            String busInfo=getIntent().getStringExtra("busId");
            int harga=Integer.parseInt(getIntent().getStringExtra("amount"))/100;
            String ticket=getIntent().getStringExtra("tickets");
            String departure=getIntent().getStringExtra("departure");
            String arrival=getIntent().getStringExtra("arrival");
            String tglKeberangkatan= getIntent().getStringExtra("date");

            UUID id=UUID.randomUUID();

            Order order=new Order();
            order.setDescripsi(tickets);
            order.setOrderId(id.toString());
            order.setAmount(String.valueOf(harga));
            order.setTickets(ticket);
            order.setBusId(busInfo);
            order.setUserId(auth.getUid());

            reference.child("Orders").child(id.toString()).setValue(order);
            reference.child("Orders").child(id.toString()).child("date").setValue(ServerValue.TIMESTAMP);
            BusInfo bus=new BusInfo();
            bus.setBusId(busInfo);
            bus.setBusName(getString(R.string.sempati_star));
            bus.setPlateNo(getString(R.string.p_1963_nm));
            bus.setTimeArrival(getString(R.string._09_30));
            bus.setTimeDeparture(getString(R.string._17_30));
            bus.setArrival(arrival);
            bus.setDeparture(departure);
            bus.setDate(tglKeberangkatan);
            reference.child("Bus").child(busInfo).setValue(bus);


            Intent i=new Intent(ChooseSeatActivity.this,Bus.class);
            startActivity(i);

        }


    }
    private void getCustomerId(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    customerId=object.getString("id");
                    Log.i("volley",customerId);
                    getEpricalKey(customerId);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header=new HashMap<>();
                header.put("Authorization","Bearer "+getString(R.string.SECRET_KEY));
                header.put("Content-Type", "application/json; charset=utf-8");
                return header;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(ChooseSeatActivity.this);
        rq.add(stringRequest);
    }

    private void getEpricalKey(String customerId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST
                , "https://api.stripe.com/v1/ephemeral_keys"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    EphemeralKey=object.getString("id");
                    Log.i("volley",EphemeralKey);
                    getClientSecret(customerId,EphemeralKey);
//                    getEpricalKey(customerId);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header=new HashMap<>();
                header.put("Authorization","Bearer "+getString(R.string.SECRET_KEY));
                header.put("Stripe-Version","2020-08-27");
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> body=new HashMap<>();
                body.put("customer",customerId);
                return body;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(ChooseSeatActivity.this);
        rq.add(stringRequest);
    }

    private void getClientSecret(String customerId, String ephemeralKey) {
//        https://api.stripe.com/v1/payment_intents
        StringRequest stringRequest = new StringRequest(Request.Method.POST
                , "https://api.stripe.com/v1/payment_intents"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    clientSecret=object.getString("client_secret");
                    Log.i("volley",clientSecret);
                    paymentFlow(customerId,clientSecret);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error.getLocalizedMessage());

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header=new HashMap<>();
                header.put("Authorization","Bearer "+getString(R.string.SECRET_KEY));
//                header.put("Content-Type","application/x-www-form-urlencoded");
                return header;
            }



            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("customer",customerId);

                float harga=(Float.parseFloat(getIntent().getStringExtra("amount"))/16000);
                int amount =(int)harga;
                params.put("amount", String.valueOf(amount));
                params.put("currency","usd");
                params.put("automatic_payment_methods[enabled]","true");
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(ChooseSeatActivity.this);
        rq.add(stringRequest);
    }

    private void paymentFlow(String customerId,String clientSecret) {
        paymentSheet.presentWithPaymentIntent(
               clientSecret, new PaymentSheet.Configuration("BUSX COMPANY"
                ,new PaymentSheet.CustomerConfiguration(customerId,clientSecret))
        );
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