package com.manh.petshopdemo1.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.manh.petshopdemo1.ActivityInfomation;
import com.manh.petshopdemo1.R;
import com.manh.petshopdemo1.adapter.CartItemAdapter;
import com.manh.petshopdemo1.databinding.FragmentCartBinding;
import com.manh.petshopdemo1.db.DBHelper;
import com.manh.petshopdemo1.interf.IOnClickCartItemListener;
import com.manh.petshopdemo1.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class Cart_Fagment extends Fragment {
    private FragmentCartBinding binding;
    private DBHelper helper;
    private List<Cart> cartList;
    private CartItemAdapter adapter;
    private long totalz = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        View view = binding.getRoot();
        helper = new DBHelper(getActivity(), "petshop.db", null, 1);
        data();
        binding.tvpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.tvamount.getText() == null || binding.tvamount.getText().equals("0đ") || binding.tvamount.getText().equals("")) {
                    Toast.makeText(getActivity(), "choose item your need", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), ActivityInfomation.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    private void data() {
        cartList = new ArrayList<>();
        Cursor cursor = helper.getData("SELECT * FROM cart");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String image = cursor.getString(2);
            int quantity = cursor.getInt(3);
            String size = cursor.getString(4);
            long price = cursor.getLong(5);
            cartList.add(new Cart(id, name, image, quantity, size, price));
            cursor.moveToNext();
        }
        adapter = new CartItemAdapter(cartList, new IOnClickCartItemListener() {
            @Override
            public void onClickItem(Long total) {
                totalz = totalz + total;
                binding.tvamount.setText(String.format("%,d", totalz) + "đ");
            }
        }, getActivity());
        binding.lvproductCart.setAdapter(adapter);
       // adapter.notifyDataSetChanged();
        binding.lvproductCart.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        binding.lvproductCart.addItemDecoration(itemDecoration);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.release();
    }
}