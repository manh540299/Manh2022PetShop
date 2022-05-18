package com.manh.petshopdemo1.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.manh.petshopdemo1.R;
import com.manh.petshopdemo1.db.DBHelper;
import com.manh.petshopdemo1.interf.IOnClickCartItemListener;
import com.manh.petshopdemo1.model.Cart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private List<Cart> cartList;
    private IOnClickCartItemListener iOnClickCartItemListener;
    private Context context;
    private ViewBinderHelper helper=new ViewBinderHelper();
    private DBHelper dbhelper;

    public CartItemAdapter(List<Cart> cartList, IOnClickCartItemListener iOnClickCartItemListener, Context context) {
        this.cartList = cartList;
        this.iOnClickCartItemListener = iOnClickCartItemListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.lv_cart_product, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(cartList.get(position).getImage()).resize(300, 300).into(holder.image);
        holder.tvname.setText(cartList.get(position).getName());
        holder.tvquantity.setText(String.valueOf(cartList.get(position).getQuantity()));
        holder.tvsize.setText(cartList.get(position).getSize());
        holder.tvprice.setText(String.format("%,d", cartList.get(position).getPrice()) + "đ");
        long total = cartList.get(position).getPrice() * cartList.get(position).getQuantity();
        holder.tvtotal.setText(String.format("%,d", total));
        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int quantity = Integer.parseInt(holder.tvquantity.getText().toString());
                if (b) {
                    iOnClickCartItemListener.onClickItem(cartList.get(position).getPrice() * quantity);
                } else {
                    iOnClickCartItemListener.onClickItem(cartList.get(position).getPrice() * quantity * -1);
                }
            }
        });
        holder.tvsummation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(holder.tvquantity.getText().toString());
                quantity = quantity + 1;
                DBHelper helper=new DBHelper(context,"petshop.db",null,1);
                helper.queryData("UPDATE cart SET quantity="+quantity+" WHERE id="+cartList.get(position).getId());
                holder.tvquantity.setText(String.valueOf(quantity));
                holder.tvtotal.setText(String.format("%,d",cartList.get(position).getPrice()*quantity)+"đ");
                if(holder.cbSelect.isChecked()){
                     iOnClickCartItemListener.onClickItem(cartList.get(position).getPrice());
                }
            }
        });
        holder.tvsubtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(holder.tvquantity.getText().toString());
                if(quantity>1) {
                    quantity = quantity - 1;
                    DBHelper helper=new DBHelper(context,"petshop.db",null,1);
                    helper.queryData("UPDATE cart SET quantity="+quantity+" WHERE id="+cartList.get(position).getId());
                    if(holder.cbSelect.isChecked()) {
                         iOnClickCartItemListener.onClickItem(cartList.get(position).getPrice()*-1);
                    }
                    holder.tvquantity.setText(String.valueOf(quantity));
                    holder.tvtotal.setText(String.format("%,d",cartList.get(position).getPrice()*quantity)+"đ");
                }
            }
        });
        helper.bind(holder.layout,String.valueOf(cartList.get(position).getId()));
        helper.setOpenOnlyOne(true);
        holder.layoutdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbhelper=new DBHelper(context,"petshop.db",null,1);
                dbhelper.queryData("DELETE FROM cart WHERE id="+cartList.get(position).getId());
                cartList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }
   public void release(){
        context=null;
   }
    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView tvname;
        private TextView tvsize;
        private TextView tvquantity;
        private TextView tvprice;
        private TextView tvtotal;
        private CheckBox cbSelect;
        private TextView tvsummation;
        private TextView tvsubtraction;
        private RelativeLayout rlItemCart;
        private SwipeRevealLayout layout;
        private LinearLayout layoutdelete;


        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgproduct);
            tvname = itemView.findViewById(R.id.tv_cart_item_name);
            tvsize = itemView.findViewById(R.id.tv_cart_item_size);
            tvquantity = itemView.findViewById(R.id.tv_cart_item_quantity);
            tvprice = itemView.findViewById(R.id.tvprice);
            tvtotal = itemView.findViewById(R.id.tvtotal);
            cbSelect = itemView.findViewById(R.id.cbselcet);
            tvsubtraction = itemView.findViewById(R.id.tv_cart_item_subtraction);
            tvsummation = itemView.findViewById(R.id.tv_cart_item_summation);
            rlItemCart=itemView.findViewById(R.id.rlitem_cart);
            layout=itemView.findViewById(R.id.swipraveallayout);
            layoutdelete=itemView.findViewById(R.id.lldelete);
        }
    }
}
