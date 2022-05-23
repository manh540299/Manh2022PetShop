package com.manh.petshopdemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.manh.petshopdemo1.adapter.OrderDetailAdapter;
import com.manh.petshopdemo1.db.DBHelper;
import com.manh.petshopdemo1.model.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class OrderDetails extends AppCompatActivity {
    private TextView tvname, tvphone, tvaddress, tvtotal;
    private ImageView imgback;
    private ListView lvorder;
    private DBHelper helper;
    private List<OrderDetail> orderDetails;
    private OrderDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        initUI();
        getData();
        setDetails();
    }

    private void initUI() {
        tvname = findViewById(R.id.tvname);
        tvphone = findViewById(R.id.tvphone);
        tvaddress = findViewById(R.id.tvaddress);
        tvtotal = findViewById(R.id.tvtotall);
        imgback = findViewById(R.id.imgback);
        lvorder = findViewById(R.id.list_order);
        helper = new DBHelper(this, "petshop.db", null, 1);
        orderDetails = new ArrayList<>();
        imgback.setOnClickListener(view -> finish());
    }

    private void getData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("idOrder", -1);
        Cursor purchaseHistory = helper.getData("SELECT * FROM orderr");
        purchaseHistory.moveToFirst();
        while (!purchaseHistory.isAfterLast()) {
            int idOrder = purchaseHistory.getInt(0);
            if (id == idOrder) {
                String name = purchaseHistory.getString(2);
                String phone = purchaseHistory.getString(3);
                String address = purchaseHistory.getString(5);
                long total = purchaseHistory.getLong(6);
                tvname.setText(name);
                tvphone.setText(phone);
                tvaddress.setText(address);
                tvtotal.setText(String.format("%,d", +total)+ "đ");
                Cursor purchasesDetails = helper.getData("SELECT * FROM orderdetail");
                purchasesDetails.moveToFirst();
                while (!purchasesDetails.isAfterLast()) {
                    String nameI = purchasesDetails.getString(1);
                    String imageI = purchasesDetails.getString(2);
                    int quantityI = purchasesDetails.getInt(3);
                    String sizeI = purchasesDetails.getString(4);
                    long priceI = purchasesDetails.getLong(5);
                    int idOrderI = purchasesDetails.getInt(6);
                    if (idOrderI == id) {
                        orderDetails.add(new OrderDetail(nameI, imageI, quantityI, sizeI, priceI));
                    }
                    purchasesDetails.moveToNext();
                }

            }
            purchaseHistory.moveToNext();
        }

    }

    private void setDetails() {
        adapter = new OrderDetailAdapter(orderDetails);
        lvorder.setAdapter(adapter);
    }
}