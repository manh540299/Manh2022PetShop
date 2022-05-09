package com.manh.petshopdemo1.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.manh.petshopdemo1.R;
import com.manh.petshopdemo1.databinding.ActivityProfileFragmentBinding;

public class Profile_Fragment extends Fragment {
    ActivityProfileFragmentBinding binding;
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
          binding= DataBindingUtil.inflate(inflater, R.layout.activity_profile_fragment,container,false);
          View view=binding.getRoot();
          return view;
    }
}