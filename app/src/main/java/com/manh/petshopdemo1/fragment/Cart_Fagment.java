package com.manh.petshopdemo1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.manh.petshopdemo1.R;
import com.manh.petshopdemo1.databinding.FragmentCartBinding;

public class Cart_Fagment extends Fragment {
   FragmentCartBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_cart,container,false);
        View view=binding.getRoot();
        return view;
    }
}