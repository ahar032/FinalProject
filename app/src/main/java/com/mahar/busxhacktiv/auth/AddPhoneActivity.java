package com.mahar.busxhacktiv.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mahar.busxhacktiv.R;

public class AddPhoneActivity extends AppCompatActivity {
    Button btn;
    EditText inputNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);
        inputNum=findViewById(R.id.input_phone);
        btn=findViewById(R.id.btnAdd);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=inputNum.getText().toString().trim();
                if(phone.isEmpty()){
                    Toast.makeText(AddPhoneActivity.this, "mohon isi nomor telepon", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddPhoneActivity.this, "pindah halaman ke finishregister", Toast.LENGTH_SHORT).show();

                    Intent a=new Intent(AddPhoneActivity.this,FinishRegister.class);
                    a.putExtra("phone",phone);
                    startActivity(a);
                }
            }
        });
    }
}