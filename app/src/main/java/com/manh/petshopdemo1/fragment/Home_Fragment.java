package com.manh.petshopdemo1.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.manh.petshopdemo1.R;
import com.manh.petshopdemo1.SearchViewActivity;
import com.manh.petshopdemo1.adapter.MenuAdapter;
import com.manh.petshopdemo1.adapter.ProductHomAdapter;
import com.manh.petshopdemo1.interf.ApiService;
import com.manh.petshopdemo1.databinding.FragmentHomeBinding;
import com.manh.petshopdemo1.model.ItemSale;
import com.manh.petshopdemo1.model.Menu;
import com.manh.petshopdemo1.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_Fragment extends Fragment {
    FragmentHomeBinding binding;
    private List<Product> productList;
    private List<Menu> menuList;
    MenuAdapter adapter;
    private List<Product> food_item;
    private List<Product> toys_item;
    private List<Product> accessories_item;
    private List<Product> health_item;
    private List<Product> shower_gel_item;
    private List<ItemSale> itemSales;
    ProductHomAdapter SaleAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        runViewFliper();
        setMenuListProduct();
        callApiProduct();
        binding.tvsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchViewActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void runViewFliper() {
        List<String> img = new ArrayList<>();
        img.add("https://bizweb.dktcdn.net/100/346/633/themes/710500/assets/slider_2.png?1639561389730");
        img.add("https://bizweb.dktcdn.net/100/346/633/themes/710500/assets/slider_3.png?1639561389730");
        img.add("https://bizweb.dktcdn.net/100/346/633/themes/710500/assets/slider_4.png?1639561389730");
        img.add("https://bizweb.dktcdn.net/100/346/633/themes/710500/assets/slider_1.png?1639561389730");
        for (int i = 0; i < img.size(); i++) {
            ImageView image = new ImageView(getActivity());
            Picasso.get().load(img.get(i)).into(image);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            binding.vflipbanner.addView(image);
        }
        binding.vflipbanner.setFlipInterval(4000);
        binding.vflipbanner.setAutoStart(true);
    }

    private void setMenuListProduct() {
        menuList = new ArrayList<>();
        menuList.add(new Menu(1, R.drawable.dogbowl, "Food"));
        menuList.add(new Menu(2, R.drawable.health, "HeaLth"));
        menuList.add(new Menu(3, R.drawable.accessories, "Accessories"));
        menuList.add(new Menu(4, R.drawable.shower_gel, "Shower gel"));
        menuList.add(new Menu(5, R.drawable.pettoy, "Toys"));
        menuList.add(new Menu(6, R.drawable.house, "Cage"));

    }

    private void callApiProduct() {
        itemSales = new ArrayList<>();
        productList = new ArrayList<>();
        food_item = new ArrayList<>();
        toys_item = new ArrayList<>();
        accessories_item = new ArrayList<>();
        health_item = new ArrayList<>();
        shower_gel_item = new ArrayList<>();
        Call<List<Product>> call = ApiService.apiService.getProduct();
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
                adapter = new MenuAdapter(menuList, getActivity());
                binding.menu.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                binding.menu.setLayoutManager(layoutManager);
            }


            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });
        Call<List<ItemSale>> call1 = ApiService.apiService.getItemSale();
        call1.enqueue(new Callback<List<ItemSale>>() {
            @Override
            public void onResponse(Call<List<ItemSale>> call, Response<List<ItemSale>> response) {
                itemSales = response.body();
                setProductHomAdapter();
            }

            @Override
            public void onFailure(Call<List<ItemSale>> call, Throwable t) {

            }
        });
    }

    private void setProductHomAdapter() {
        SaleAdapter = new ProductHomAdapter(itemSales, getActivity());
        binding.lvFoodItem.setAdapter(SaleAdapter);
        SaleAdapter.notifyDataSetChanged();
        binding.lvFoodItem.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.release();
        }
        if (SaleAdapter != null) {
            SaleAdapter.release();
        }

    }
}