package com.manh.petshopdemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivityInfomation extends AppCompatActivity {
    private EditText edtname,edtphone,edtaddress;
    private Button btnconfirm;
    ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        initUI();
        initListener();
    }



    private void initUI() {
        edtname=findViewById(R.id.edtname);
        edtphone=findViewById(R.id.edtphone);
        edtaddress=findViewById(R.id.edtaddress);
        btnconfirm=findViewById(R.id.btnconfirm);
        imgback=findViewById(R.id.imgback);
    }
    private void initListener() {
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityInfomation.this,"Order success",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}