package com.manh.petshopdemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manh.petshopdemo1.db.DBHelper;
import com.manh.petshopdemo1.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class ActivityInfomation extends AppCompatActivity {
    private EditText edtname, edtphone, edtaddress;
    private Button btnconfirm;
    private ImageView imgback;
    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        initUI();
        initListener();
    }


    private void initUI() {
        helper = new DBHelper(this, "petshop.db", null, 1);
        edtname = findViewById(R.id.edtname);
        edtphone = findViewById(R.id.edtphone);
        edtaddress = findViewById(R.id.edtaddress);
        btnconfirm = findViewById(R.id.btnconfirm);
        imgback = findViewById(R.id.imgbacki);
        String createTableOrder="CREATE TABLE IF NOT EXISTS orderr(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "gmail NTEXT," +
                "name NTEXT," +
                "phone TEXT,"+
                "image TEXT,"+
                "address NTEXT," +
                "total INT" +
                ")";
        String createTableOrderDetail="CREATE TABLE IF NOT EXISTS orderdetail(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name NTEXT," +
                "image TEXT,"+
                "quantity INT," +
                "size NTEXT," +
                "price INT," +
                "idOrder INT"+
                ")";
        helper.queryData(createTableOrderDetail);

        helper.queryData(createTableOrder);
    }

    private void initListener() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String gmail = user.getEmail();
        Bundle extras = getIntent().getExtras();
        Intent intent=getIntent();
        long total=intent.getLongExtra("total",-1);
        List<Cart> cartList = extras.getParcelableArrayList("cartList");
        btnconfirm.setOnClickListener(view -> {
            String name01 = edtname.getText().toString();
            String phone01 = edtphone.getText().toString();
            String address01 = edtaddress.getText().toString();
            if (!name01.isEmpty() && !phone01.isEmpty() && !address01.isEmpty() && cartList.size() > 0) {
                helper.queryData("INSERT INTO orderr(gmail,name,phone,address,total) VALUES('" + gmail + "', '" + name01 + "', '" + phone01 + "', '" + address01 + "', " + total + ")");
                Cursor cursor= helper.getData("SELECT * FROM orderr");
                cursor.moveToLast();
                int id=cursor.getInt(0);
                for (Cart cart : cartList) {
                    String name = cart.getName();
                    String image = cart.getImage();
                    int quantity = cart.getQuantity();
                    String size = cart.getSize();
                    long price = cart.getPrice();
                    helper.queryData("INSERT INTO orderdetail(name,image,quantity,size,price,idOrder) VALUES('" + name + "','" + image + "'," + quantity + ",'" + size + "'," + price +","+id+")");

                }
                Handler handler=new Handler();
                handler.postDelayed(() -> Toast.makeText(ActivityInfomation.this,"Order Success",Toast.LENGTH_SHORT).show(),3000);
                Intent intent1=new Intent(ActivityInfomation.this,MainActivity.class);
                startActivity(intent1);
            } else {
                Toast.makeText(ActivityInfomation.this, "Please fill in the missing information", Toast.LENGTH_SHORT).show();
            }
        });
        imgback.setOnClickListener(view -> finish());
    }
}