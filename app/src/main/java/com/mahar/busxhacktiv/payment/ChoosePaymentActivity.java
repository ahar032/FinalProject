package com.mahar.busxhacktiv.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.mahar.busxhacktiv.R;
import com.mahar.busxhacktiv.bus.Bus;
import com.mahar.busxhacktiv.bus.ChooseSeatActivity;
import com.mahar.busxhacktiv.model.BusInfo;
import com.mahar.busxhacktiv.model.Order;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.stripe.android.paymentsheet.PaymentSheetResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChoosePaymentActivity extends AppCompatActivity {
    TextView amount,payment;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    PaymentSheet paymentSheet;
    String customerId;
    String EphemeralKey;
    String clientSecret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_payment);

        amount=findViewById(R.id.amount);
        payment=findViewById(R.id.stripe);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();


        PaymentConfiguration.init(this,getString(R.string.PUBLISH_KEY));
        paymentSheet=new PaymentSheet(this, new PaymentSheetResultCallback() {
            @Override
            public void onPaymentSheetResult(@NonNull PaymentSheetResult paymentSheetResult) {
                onPaymentResult(paymentSheetResult);
            }
        });

        BusInfo busInfo=(BusInfo) getIntent().getSerializableExtra("bus");
        Order order=(Order) getIntent().getSerializableExtra("order");
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String finalAmount = formatter.format(Double.parseDouble(order.getAmount()));
        amount.setText(finalAmount);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    getCustomerId();
            }
        });
    }

    private void getCustomerId() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    customerId=object.getString("id");
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
        RequestQueue rq= Volley.newRequestQueue(ChoosePaymentActivity.this);
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
                    getClientSecret(customerId);
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
            public Map<String, String> getHeaders() {
                Map<String, String> header=new HashMap<>();
                header.put("Authorization","Bearer "+getString(R.string.SECRET_KEY));
                header.put("Stripe-Version","2020-08-27");
                return header;
            }

            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> body=new HashMap<>();
                body.put("customer",customerId);
                return body;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(ChoosePaymentActivity.this);
        rq.add(stringRequest);
    }

    private void getClientSecret(String customerId) {
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
            public Map<String, String> getHeaders() {
                Map<String, String> header=new HashMap<>();
                header.put("Authorization","Bearer "+getString(R.string.SECRET_KEY));
//                header.put("Content-Type","application/x-www-form-urlencoded");
                return header;
            }



            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params=new HashMap<>();
                params.put("customer",customerId);
                Order order= (Order) getIntent().getSerializableExtra("order");

                float harga=(Float.parseFloat(order.getAmount())/16000*100);
                int amount =(int)harga;
                params.put("amount", String.valueOf(amount));
                params.put("currency","usd");
                params.put("automatic_payment_methods[enabled]","true");
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(ChoosePaymentActivity.this);
        rq.add(stringRequest);
    }

    private void paymentFlow(String customerId, String clientSecret) {
        paymentSheet.presentWithPaymentIntent(
                clientSecret, new PaymentSheet.Configuration("BUSX COMPANY"
                        ,new PaymentSheet.CustomerConfiguration(customerId,clientSecret))
        );
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed){
//            IF PAYMENT SUCCESS
            UUID id=UUID.randomUUID();
            BusInfo busInfo= (BusInfo) getIntent().getSerializableExtra("bus");
            Order order= (Order) getIntent().getSerializableExtra("order");

//            detail status 2==sudah bayar,3==selesai perjalanan,9=gagal
            order.setStatus("Paid");
            order.setDetailStatus("2");
            order.setOrderId(id.toString());

            reference.child("Orders").child(id.toString()).setValue(order);
            reference.child("Orders").child(id.toString()).child("date").setValue(ServerValue.TIMESTAMP);
            reference.child("Bus").child(busInfo.getBusId()).setValue(busInfo);
            Intent i=new Intent(ChoosePaymentActivity.this, Bus.class);
            startActivity(i);

        }
        else if(paymentSheetResult instanceof PaymentSheetResult.Failed){

            UUID id=UUID.randomUUID();
            BusInfo busInfo= (BusInfo) getIntent().getSerializableExtra("bus");
            Order order= (Order) getIntent().getSerializableExtra("order");

//            detail status 2==sudah bayar,3==selesai perjalanan,9=gagal
            order.setStatus("Failed");
            order.setDetailStatus("9");
            order.setOrderId(id.toString());

            reference.child("Orders").child(id.toString()).setValue(order);
            reference.child("Orders").child(id.toString()).child("date").setValue(ServerValue.TIMESTAMP);
            reference.child("Bus").child(busInfo.getBusId()).setValue(busInfo);
            Intent i=new Intent(ChoosePaymentActivity.this,Bus.class);
            startActivity(i);
        }
        else if(paymentSheetResult instanceof PaymentSheetResult.Canceled){
            Toast.makeText(ChoosePaymentActivity.this, "Pembayaran dibatalkan", Toast.LENGTH_SHORT).show();
        }
    }
}