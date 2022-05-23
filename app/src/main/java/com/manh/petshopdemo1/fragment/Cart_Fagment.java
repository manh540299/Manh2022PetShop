package com.manh.petshopdemo1.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manh.petshopdemo1.ActivityInfomation;
import com.manh.petshopdemo1.Login;
import com.manh.petshopdemo1.R;
import com.manh.petshopdemo1.adapter.CartItemAdapter;
import com.manh.petshopdemo1.databinding.FragmentCartBinding;
import com.manh.petshopdemo1.db.DBHelper;
import com.manh.petshopdemo1.interf.IOnClickCartItemListener;
import com.manh.petshopdemo1.interf.IOnClickCartItemListener2;
import com.manh.petshopdemo1.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class Cart_Fagment extends Fragment {
    private FragmentCartBinding binding;
    private DBHelper helper;
    private List<Cart> cartList;
    private CartItemAdapter adapter;
    private long totalz = 0;
    private List<Cart> cartList01;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        View view = binding.getRoot();
        data();
        initListener();
        return view;
    }

    private void initListener() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        binding.tvpayment.setOnClickListener(view -> {
            if (binding.tvamount.getText() == null || binding.tvamount.getText().equals("0đ") || binding.tvamount.getText().equals("")) {
                Toast.makeText(getActivity(), "choose item your need", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent;
                if (user == null) {
                    intent = new Intent(getActivity(), Login.class);
                } else {
                    intent = new Intent(getActivity(), ActivityInfomation.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("cartList", (ArrayList<? extends Parcelable>) cartList01);
                    intent.putExtras(bundle);
                    intent.putExtra("total", totalz);
                }
                startActivity(intent);
            }
        });
    }

    private void data() {
        helper = new DBHelper(getActivity(), "petshop.db", null, 1);
        String createTableCart = "CREATE TABLE IF NOT EXISTS cart(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name NTEXT," +
                "gmail TEXT," +
                "image TEXT," +
                "quantity INT," +
                "size NTEXT," +
                "price INT" +
                ")";
        helper.queryData(createTableCart);
        cartList = new ArrayList<>();
        cartList01 = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String gmail = user.getEmail();
            Cursor cursor = helper.getData("SELECT * FROM cart");
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    if (cursor.getString(2).equalsIgnoreCase(gmail)) {
                        binding.lvproductCart.setVisibility(View.VISIBLE);
                        binding.tvcartisempty.setVisibility(View.INVISIBLE);
                        int id = cursor.getInt(0);
                        String name = cursor.getString(1);
                        String image = cursor.getString(3);
                        int quantity = cursor.getInt(4);
                        String size = cursor.getString(5);
                        long price = cursor.getLong(6);
                        cartList.add(new Cart(id, name, image, quantity, size, price));
                    }
                    else {
                        binding.lvproductCart.setVisibility(View.INVISIBLE);
                        binding.tvcartisempty.setVisibility(View.VISIBLE);
                    }
                    cursor.moveToNext();
                }
                List<Cart> cartList02 = new ArrayList<>();
                for (int i = cartList.size() - 1; i >= 0; i--) {
                    cartList02.add(cartList.get(i));
                }
                adapter = new CartItemAdapter(cartList02, cart -> {
                    totalz = totalz + cart.getPrice() * cart.getQuantity();
                    binding.tvamount.setText(String.format("%,d", totalz) + "đ");
                    if (cartList01.size() == 0) {
                        cartList01.add(cart);
                    } else {
                        boolean check = true;

                        for (int i = 0; i < cartList01.size(); i++) {
                            if (cartList01.get(i).getName().equalsIgnoreCase(cart.getName()) && cartList01.get(i).getPrice() == cart.getPrice() && cartList01.get(i).getSize().equalsIgnoreCase(cart.getSize())) {
                                cartList01.get(i).setQuantity(cartList01.get(i).getQuantity() + cart.getQuantity());
                                check = false;
                            }
                        }
                        if (check) {
                            cartList01.add(cart);
                        }
                    }
                }, getActivity());

                binding.lvproductCart.setAdapter(adapter);
                binding.lvproductCart.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
                binding.lvproductCart.addItemDecoration(itemDecoration);
            }
        }
}