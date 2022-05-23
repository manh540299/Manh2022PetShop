package com.manh.petshopdemo1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.manh.petshopdemo1.R;
import com.manh.petshopdemo1.model.OrderDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderDetailAdapter extends BaseAdapter {
    List<OrderDetail> orderDetails;

    public OrderDetailAdapter(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public int getCount() {
        return orderDetails.size();
    }

    @Override
    public Object getItem(int i) {
        return orderDetails.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
       private ImageView img;
       private TextView tvname, tvsize, tvquantity, tvprice;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.lvorderdetail, null);
           holder=new ViewHolder();
            holder.img = view.findViewById(R.id.img_od_product);
            holder.tvname = view.findViewById(R.id.tv_od_name);
            holder.tvsize = view.findViewById(R.id.tv_od_size);
            holder.tvquantity = view.findViewById(R.id.tv_od_quantity);
            holder.tvprice = view.findViewById(R.id.tv_od_price);
             view.setTag(holder);
        }else {
            holder=(ViewHolder) view.getTag();
        }
        Picasso.get().load(orderDetails.get(i).getImage()).into(holder.img);
        holder.tvname.setText(orderDetails.get(i).getItemName());
        holder.tvsize.setText(orderDetails.get(i).getSize());
        holder.tvquantity.setText("x"+orderDetails.get(i).getQuantity());
        holder.tvprice.setText(String.format("%,d",+orderDetails.get(i).getPrice())+"đ");
        return view;
    }
}
