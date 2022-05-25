package com.manh.petshopdemo1.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.manh.petshopdemo1.model.Messenge;
import com.manh.petshopdemo1.R;
import com.manh.petshopdemo1.adapter.MessengerAdapter;
import com.manh.petshopdemo1.databinding.FragmentMessengeBinding;

import java.util.ArrayList;
import java.util.List;

public class Messenge_Fagment extends Fragment {
   private MessengerAdapter adapter;
   private List<Messenge> messengeList;
   FragmentMessengeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       binding= DataBindingUtil.inflate(inflater, R.layout.fragment_messenge,container,false);
       View view=binding.getRoot();
       messengeList=new ArrayList<>();
        adapter=new MessengerAdapter(messengeList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        binding.lvMessenge.setAdapter(adapter);
        binding.lvMessenge.setLayoutManager(layoutManager);
       binding.btnsend.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String mess=binding.edtmessenge.getText().toString();
               if(TextUtils.isEmpty(mess))
                   return;
               messengeList.add(new Messenge(mess));
               binding.lvMessenge.scrollToPosition(messengeList.size()-1);
               binding.edtmessenge.setText("");
           }
       });

       return view;
    }
}