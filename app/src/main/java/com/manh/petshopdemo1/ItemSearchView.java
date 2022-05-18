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

public class ItemSearchView extends AppCompatActivity {
    TextView tvsearch;
    TextView tvnoresult;
    ImageView imgback;
    RecyclerView rc_list_item;
    List<Product> productList;
    ItemSearchAdapter adapter;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_search_view);
        initUI();
        initListener();
        callAPIItem();

    }

    private void initUI() {
        tvsearch=findViewById(R.id.tvsearch);
        tvnoresult=findViewById(R.id.tv_no_result);
        imgback=findViewById(R.id.imgback);
        rc_list_item=findViewById(R.id.lvitem);
        Intent intent=this.getIntent();
        key=intent.getStringExtra("key");
        tvsearch.setText(key);
    }

    private void initListener() {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(ItemSearchView.this,MainActivity.class);
                startActivity(intent1);
            }
        });
        tvsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ItemSearchView.this,SearchViewActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void callAPIItem(){
        productList=new ArrayList<>();
        List<Product> productList1=new ArrayList<>();
        Call<List<Product>> call= ApiService.apiService.getProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList=response.body();
                for(Product product:productList){
                    if(product.getName().toLowerCase().contains(key.toLowerCase())){
                        productList1.add(product);
                    }
                }
                if(productList1.size()>0) {
                    adapter = new ItemSearchAdapter(productList1,ItemSearchView.this);
                    rc_list_item.setAdapter(adapter);
                    RecyclerView.LayoutManager layoutManager=new GridLayoutManager(ItemSearchView.this,2);
                    rc_list_item.setLayoutManager(layoutManager);
                }
                else{
                 tvnoresult.setText("không tìm thấy kết quả");
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
}