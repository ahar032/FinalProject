package com.mahar.busxhacktiv.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahar.busxhacktiv.R;
import com.mahar.busxhacktiv.adapter.ProvinsiAdapter;
import com.mahar.busxhacktiv.model.Provinsi;

import java.util.ArrayList;
import java.util.List;

public class ListProvinsi extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    EditText departure,arrival;
    ImageView goback,enter;
    RecyclerView rv;
    ProgressBar pb;
    List<Provinsi> list=new ArrayList<>();
    List<String> listAll=new ArrayList<>();

    ProvinsiAdapter provinsiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_provinsi);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
        departure=findViewById(R.id.input_from);
        arrival=findViewById(R.id.input_where);
        rv=findViewById(R.id.rv);
        pb=findViewById(R.id.pb);
        enter=findViewById(R.id.enter);
        goback=findViewById(R.id.goback);


        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(ListProvinsi.this));
        list=new ArrayList<>();
        listAll=new ArrayList<>();

        provinsiAdapter=new ProvinsiAdapter(list);
        rv.setAdapter(provinsiAdapter);
        departure.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("char",charSequence.toString());
                reference.child("Provinsi").orderByChild("name").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot sp:snapshot.getChildren()){
                            Provinsi provinsi=sp.getValue(Provinsi.class);
                            if(provinsi.getName().contains(charSequence.toString().trim().toUpperCase())){
                                list.add(provinsi);
                            }
                        }
                        provinsiAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {
                provinsiAdapter.setOnClickListener(new ProvinsiAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Provinsi provinsi) {
                        departure.setText(provinsi.getName());
                        list.clear();

                    }
                });
            }
        });

        arrival.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("char",charSequence.toString());
                reference.child("Provinsi").orderByChild("name").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot sp:snapshot.getChildren()){
                            Provinsi provinsi=sp.getValue(Provinsi.class);
                            if(provinsi.getName().contains(charSequence.toString().trim().toUpperCase())){
                                list.add(provinsi);
                            }
                        }
                        provinsiAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {
                provinsiAdapter.setOnClickListener(new ProvinsiAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Provinsi provinsi) {
                        arrival.setText(provinsi.getName());
                        list.clear();

                    }
                });
            }
        });

        reference.child("Provinsi").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    listAll.clear();
                    loading(true);
                    for (DataSnapshot sp:snapshot.getChildren()){
                        Provinsi provinsi=sp.getValue(Provinsi.class);
                        list.add(provinsi);
                        listAll.add(provinsi.getName());
                    }
                    provinsiAdapter.notifyDataSetChanged();
                    loading(false);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dari=departure.getText().toString().trim();
                String tujuan=arrival.getText().toString().trim();
                if(dari.isEmpty() || tujuan.isEmpty()){
                    return;
                }else if(listAll.contains(dari) && listAll.contains(tujuan)){
                    Intent i=new Intent();
                    i.putExtra("departure",dari);
                    i.putExtra("arrival",tujuan);
                    setResult(RESULT_OK,i);
                    finish();
                }
            }
        });


    }
    public void loading(boolean p){
        if(p){
//                true == visible
            pb.setVisibility(View.VISIBLE);
            arrival.setEnabled(false);
            enter.setEnabled(false);
        }else{
            pb.setVisibility(View.GONE);
            arrival.setEnabled(true);
            enter.setEnabled(true);
        }
    }
}