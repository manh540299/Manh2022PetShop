package com.manh.petshopdemo1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.manh.petshopdemo1.R;
import com.manh.petshopdemo1.model.Product;

import java.util.List;

public class SearchViewAdapter extends BaseAdapter {
    List<Product> productList;

    public SearchViewAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1;
        if(view==null){
            view1=View.inflate(viewGroup.getContext(), R.layout.lv_search,null);
        }
        else view1=view;

        return view1;
    }
}
