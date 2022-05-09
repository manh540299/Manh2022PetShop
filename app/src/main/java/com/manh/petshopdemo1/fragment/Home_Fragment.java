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
import com.manh.petshopdemo1.api.ApiService;
import com.manh.petshopdemo1.databinding.FragmentHomeBinding;
import com.manh.petshopdemo1.model.Menu;
import com.manh.petshopdemo1.model.Product;

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
    ProductHomAdapter foodAdapter;
    ProductHomAdapter accessoriesAdapter;
    ProductHomAdapter healthAdapter;
    ProductHomAdapter showerGelAdapter;

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
                Intent intent=new Intent(getActivity(),SearchViewActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void runViewFliper() {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.banner));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        binding.vflipbanner.addView(imageView);
        ImageView imageView1 = new ImageView(getContext());
        imageView1.setImageDrawable(getResources().getDrawable(R.drawable.pet));
        imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
        binding.vflipbanner.addView(imageView1);
        ImageView imageView2 = new ImageView(getContext());
        imageView2.setImageDrawable(getResources().getDrawable(R.drawable.petshopp));
        imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
        binding.vflipbanner.addView(imageView2);
        ImageView imageView3 = new ImageView(getContext());
        imageView3.setImageDrawable(getResources().getDrawable(R.drawable.pshopbn1));
        imageView3.setScaleType(ImageView.ScaleType.FIT_XY);
        binding.vflipbanner.addView(imageView3);
        binding.vflipbanner.setFlipInterval(4000);
        binding.vflipbanner.setAutoStart(true);
    }

    private void setMenuListProduct() {
        menuList = new ArrayList<>();
        menuList.add(new Menu(1,R.drawable.dogbowl, "Food"));
        menuList.add(new Menu(2,R.drawable.pettoy, "Toys"));
        menuList.add(new Menu(3,R.drawable.health, "HeaLth"));
        menuList.add(new Menu(4,R.drawable.accessories, "Accessories"));
        menuList.add(new Menu(5,R.drawable.shower_gel, "Shower gel"));
        menuList.add(new Menu(6,R.drawable.house, "Cage"));

    }

    private void callApiProduct() {

        productList = new ArrayList<>();
        food_item=new ArrayList<>();
        toys_item=new ArrayList<>();
        accessories_item= new ArrayList<>();
        health_item=new ArrayList<>();
        shower_gel_item=new ArrayList<>();
        Call<List<Product>> call = ApiService.apiService.getProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList=response.body();
                for(Product product:productList){
                    if(product.getGroup()==1||product.getGroup()==2||product.getGroup()==3||product.getGroup()==4
                    ||product.getGroup()==11){
                        food_item.add(product);
                    }else if(product.getGroup()==5||product.getGroup()==6||product.getGroup()==7){
                        shower_gel_item.add(product);
                    }else if(product.getGroup()==8||product.getGroup()==10){
                        accessories_item.add(product);
                    }else if(product.getGroup()==9){
                        health_item.add(product);
                    }
                }
                setProductHomAdapter();
                adapter = new MenuAdapter(menuList, getActivity());
                binding.menu.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                binding.menu.setLayoutManager(layoutManager);
            }


            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getActivity(),"call api error",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void setProductHomAdapter(){
       foodAdapter=new ProductHomAdapter(food_item);
       accessoriesAdapter=new ProductHomAdapter(accessories_item);
       healthAdapter=new ProductHomAdapter(health_item);
       showerGelAdapter=new ProductHomAdapter(shower_gel_item);
       RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        RecyclerView.LayoutManager layoutManager01=new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        RecyclerView.LayoutManager layoutManager02=new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        RecyclerView.LayoutManager layoutManager03=new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
       binding.lvFoodItem.setAdapter(foodAdapter);
       foodAdapter.notifyDataSetChanged();
       binding.lvFoodItem.setLayoutManager(layoutManager);
       binding.lvHealthItem.setAdapter(healthAdapter);
       healthAdapter.notifyDataSetChanged();
       binding.lvHealthItem.setLayoutManager(layoutManager01);
       binding.lvShowerGelItem.setAdapter(showerGelAdapter);
       showerGelAdapter.notifyDataSetChanged();
       binding.lvShowerGelItem.setLayoutManager(layoutManager02);
       binding.lvAccessoriesItem.setAdapter(accessoriesAdapter);
       accessoriesAdapter.notifyDataSetChanged();
       binding.lvAccessoriesItem.setLayoutManager(layoutManager03);

    }


}