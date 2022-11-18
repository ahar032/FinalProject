package com.mahar.busxhacktiv.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    EditText inputProvinsi;
    ImageView goback,enter;
    RecyclerView rv;
    ProgressBar pb;
    List<Provinsi> list=new ArrayList<>();
    ProvinsiAdapter provinsiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_provinsi);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference();

        inputProvinsi=findViewById(R.id.input_provinsi);
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
        provinsiAdapter=new ProvinsiAdapter(list);
        rv.setAdapter(provinsiAdapter);
        provinsiAdapter.setOnClickListener(new ProvinsiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Provinsi provinsi) {
                if (getIntent().getStringExtra("where").equals("-")){
                    Intent i=new Intent(ListProvinsi.this,HomeFragment.class);
                    i.putExtra("where",provinsi.getName());
                    startActivity(i);
                } else if(getIntent().getStringExtra("from").equals("-")){
                    Intent i=new Intent(ListProvinsi.this,HomeFragment.class);
                    i.putExtra("from",provinsi.getName());
                    startActivity(i);
                }
            }
        });
        if(inputProvinsi.getText().toString().trim().isEmpty()){
            reference.child("Provinsi").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    loading(true);
                    for (DataSnapshot sp:snapshot.getChildren()){
                        Provinsi provinsi=sp.getValue(Provinsi.class);
                        list.add(provinsi);
                    }
                    provinsiAdapter.notifyDataSetChanged();
                    loading(false);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading(true);
                String input=inputProvinsi.getText().toString().trim().toUpperCase();

                if(input.isEmpty()){
                    loading(false);
                    Toast.makeText(ListProvinsi.this, "Mohon isi provinsi", Toast.LENGTH_SHORT).show();
                }
                else{
                    reference.child("Provinsi").orderByChild("name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list.clear();
                            for (DataSnapshot sp:snapshot.getChildren()){
                                Provinsi provinsi=sp.getValue(Provinsi.class);
                                if(provinsi.getName().contains(input)){
                                    list.add(provinsi);
                                }
                            }
                            provinsiAdapter.notifyDataSetChanged();
                            loading(false);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });


    }
    public void loading(boolean p){
        if(p){
//                true == visible
            pb.setVisibility(View.VISIBLE);
            inputProvinsi.setEnabled(false);
            enter.setEnabled(false);
        }else{
            pb.setVisibility(View.GONE);
            inputProvinsi.setEnabled(true);
            enter.setEnabled(true);
        }
    }
}