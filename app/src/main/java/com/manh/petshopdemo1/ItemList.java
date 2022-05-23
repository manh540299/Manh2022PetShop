package com.manh.petshopdemo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.manh.petshopdemo1.adapter.ItemSearchAdapter;
import com.manh.petshopdemo1.interf.ApiService;
import com.manh.petshopdemo1.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemList extends AppCompatActivity {
    private List<Product> productList;
    private TextView tvtitle;
    private ImageView imgback;
    private RecyclerView rc_list_item;
    private List<Product> food_item;
    private List<Product> toys_item;
    private List<Product> accessories_item;
    private List<Product> health_item;
    private List<Product> shower_gel_item;
    ItemSearchAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        initUI();
        Intent intent=getIntent();
        String title=intent.getStringExtra("itemview")+" Item";
        tvtitle.setText(title);
        int id =intent.getIntExtra("key",0);
        Call<List<Product>> call= ApiService.apiService.getProduct();
        productList=new ArrayList<>();
        productList = new ArrayList<>();
        food_item=new ArrayList<>();
        toys_item=new ArrayList<>();
        accessories_item= new ArrayList<>();
        health_item=new ArrayList<>();
        shower_gel_item=new ArrayList<>();
        imgback.setOnClickListener(view -> {
            Intent intent1=new Intent(ItemList.this,MainActivity.class);
            startActivity(intent1);
        });

        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
                for (Product product : productList) {
                    if (product.getGroup() == 1 || product.getGroup() == 2 || product.getGroup() == 3 || product.getGroup() == 4
                            || product.getGroup() == 11) {
                        food_item.add(product);
                    } else if (product.getGroup() == 5 || product.getGroup() == 6 || product.getGroup() == 7) {
                        shower_gel_item.add(product);
                    } else if (product.getGroup() == 8 || product.getGroup() == 10) {
                        accessories_item.add(product);
                    } else if (product.getGroup() == 9) {
                        health_item.add(product);
                    }
                }
                switch (id) {
                    case 1:
                        adapter = new ItemSearchAdapter(food_item,ItemList.this);
                        rc_list_item.setAdapter(adapter);
                        rc_list_item.setLayoutManager(layoutManager);
                        break;
                    case 2:
                        adapter = new ItemSearchAdapter(health_item,ItemList.this);
                        rc_list_item.setAdapter(adapter);
                        rc_list_item.setLayoutManager(layoutManager);
                        break;
                    case 3:
                        adapter = new ItemSearchAdapter(accessories_item,ItemList.this);
                        rc_list_item.setAdapter(adapter);
                        rc_list_item.setLayoutManager(layoutManager);
                        break;
                    case 4:
                        adapter = new ItemSearchAdapter(shower_gel_item,ItemList.this);
                        rc_list_item.setAdapter(adapter);
                        rc_list_item.setLayoutManager(layoutManager);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void initUI() {
        tvtitle=findViewById(R.id.tv_name_view);
        imgback=findViewById(R.id.img_back);
        rc_list_item=findViewById(R.id.lv_item);
    }
}