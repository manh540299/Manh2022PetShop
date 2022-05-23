package com.manh.petshopdemo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manh.petshopdemo1.adapter.SizeItemAdapter;

import com.manh.petshopdemo1.db.DBHelper;
import com.manh.petshopdemo1.interf.ApiService;
import com.manh.petshopdemo1.databinding.ActivityDetailProductBinding;
import com.manh.petshopdemo1.interf.OnClickItemListener;
import com.manh.petshopdemo1.model.Cart;
import com.manh.petshopdemo1.model.SizeItem;
import com.manh.petshopdemo1.model.TypeItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProduct extends AppCompatActivity {
    private ActivityDetailProductBinding binding;
    private SizeItemAdapter adapter;
    private List<TypeItem> itemTypeList;
    private List<SizeItem> list;
    private Call<List<SizeItem>> call;
    private int quantity;
    private DBHelper dbHelper;
    private String image;
    private int id;
    private Intent intent01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_product);
        initUI();

        callAPI();
        addToCart();
    }

    private void initUI() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        image = intent.getStringExtra("image");
        String price = intent.getStringExtra("price");
        String sale = intent.getStringExtra("sale");

        intent01 = new Intent(DetailProduct.this, MainActivity.class);
        id = intent.getIntExtra("id", -1);
        binding.tvNameItem.setText(name);
        binding.tvPrice.setText(String.valueOf(price));
        if(sale!=null) {
            binding.tvSale.setText(sale);
            binding.tvSale.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            binding.tvSale.setVisibility(View.GONE);
        }
        binding.imgback.setOnClickListener(view -> finish());
        Picasso.get().load(image).resize(350, 350).into(binding.imageItem);
        setQuantity();
    }

    public void callAPI() {
        call = ApiService.apiService.getSizeItem();
        call.enqueue(new Callback<List<SizeItem>>() {
            @Override
            public void onResponse(Call<List<SizeItem>> call, Response<List<SizeItem>> response) {
                list = response.body();
                if (id != -1) {
                    for (SizeItem itemSize : list) {
                        if (itemSize.getId() == id) {
                            binding.tvTypeTitle.setText(itemSize.getName()+" : ");
                            itemTypeList = itemSize.getType();
                            binding.tvTypeTitle01.setText(itemTypeList.get(0).getName());
                            adapter = new SizeItemAdapter(itemTypeList, DetailProduct.this, itemType -> {
                                binding.tvTypeTitle01.setText(itemType.getName());
                                binding.tvPrice.setText(String.format("%,d", itemType.getPrice()) + "đ");
                            });
                            binding.lvtypeitem.setAdapter(adapter);
                            binding.lvtypeitem.setLayoutManager(new LinearLayoutManager(DetailProduct.this, RecyclerView.HORIZONTAL, false));

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SizeItem>> call, Throwable t) {

            }
        });
    }

    public void setQuantity() {
         quantity = Integer.parseInt(binding.tvquantity.getText().toString());
        binding.tvsummation.setOnClickListener(view -> {
            quantity += 1;
            binding.tvquantity.setText(String.valueOf(quantity));
        });

        binding.tvsubtraction.setOnClickListener(view -> {
            if (quantity > 1) {
                quantity -= 1;
                binding.tvquantity.setText(String.valueOf(quantity));
            }
        });
    }

    public void addToCart() {

            dbHelper = new DBHelper(DetailProduct.this, "petshop.db", null, 1);
            dbHelper.queryData("CREATE TABLE IF NOT EXISTS cart(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name NTEXT," +
                    "gmail TEXT," +
                    "image TEXT," +
                    "quantity INT," +
                    "size NTEXT," +
                    "price INT" +
                    ")");

            binding.tvAddToCart.setOnClickListener(view -> {
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                if(user==null){
                    Intent intent=new Intent(this,Login.class);
                    intent.putExtra("detailk",true);
                    startActivity(intent);
                }else {

                    String price = binding.tvPrice.getText().toString();
                    String[] priceOld = price.split("đ");
                    String priceOld01 = priceOld[0];
                    String[] pcold = priceOld01.split(",");
                    String pcold01 = "";
                    for (int i = 0; i < pcold.length; i++) {
                        pcold01 += pcold[i];
                    }
                    int priceI = Integer.parseInt(pcold01);
                    String name = binding.tvNameItem.getText().toString();
                    int quantityy = Integer.parseInt(binding.tvquantity.getText().toString());
                    String sizez = binding.tvTypeTitle01.getText().toString();
                    Cursor cursor = dbHelper.getData("SELECT * FROM cart");
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        while (!cursor.isAfterLast()) {
                            String nameC = cursor.getString(1);
                            String gmailC = cursor.getString(2);
                            int quantityC = cursor.getInt(4);
                            String sizeC = cursor.getString(5);
                            long pricess = cursor.getLong(6);
                            if (nameC.equals(name)&&gmailC.equalsIgnoreCase(user.getEmail()) && sizeC.equals(sizez) && pricess == priceI || (nameC.equals(name) && sizeC == "" && pricess == priceI)&&gmailC.equalsIgnoreCase(user.getEmail())) {
                                quantityy += quantityC;
                                dbHelper.queryData("DELETE FROM cart WHERE id=" + cursor.getInt(0));
                            }
                            cursor.moveToNext();
                        }
                    }
                    dbHelper.queryData("INSERT INTO cart(name,gmail,image,quantity,size,price) VALUES('" + name + "','" +user.getEmail()+"','"+ image + "'," + quantityy + ",'" + sizez + "'," + priceI + ")");
                    intent01.putExtra("key", true);
                    startActivity(intent01);
                }
            });

    }
}