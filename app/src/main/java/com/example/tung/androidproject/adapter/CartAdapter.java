package com.example.tung.androidproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tung.androidproject.R;
import com.example.tung.androidproject.activity.CartActivity;
import com.example.tung.androidproject.fragment.ShoppingFragment;
import com.example.tung.androidproject.model.Cart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cart> carts;

    public CartAdapter(Context context, ArrayList<Cart> carts) {
        this.context = context;
        this.carts = carts;
    }

    @Override
    public int getCount() {
        return carts.size();
    }

    @Override
    public Object getItem(int position) {
        return carts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView tvTensp, tvGiasp;
        public ImageView imgSp;
        public Button btnThemsp, btnBotsp;
        public EditText etSoluongsp;

        public ImageView btnXoa;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        CartAdapter.ViewHolder viewHolder = null;

        if (view == null) {
            viewHolder = new CartAdapter.ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_cart, null);

            viewHolder.tvTensp = view.findViewById(R.id.tv_cart_tensp);
            viewHolder.tvGiasp = view.findViewById(R.id.tv_cart_giasp);
            viewHolder.imgSp = view.findViewById(R.id.im_cart);
            viewHolder.btnBotsp = view.findViewById(R.id.btn_botsp);
            viewHolder.btnThemsp = view.findViewById(R.id.btn_themsp);
            viewHolder.etSoluongsp = view.findViewById(R.id.et_soluongsp);
            viewHolder.btnXoa = view.findViewById(R.id.btn_xoa);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (CartAdapter.ViewHolder) view.getTag();
        }

        Cart cart = (Cart) getItem(position);

        viewHolder.tvTensp.setText(cart.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGiasp.setText(decimalFormat.format(cart.getGiasp()));
        Picasso.with(context).load(cart.getHinhsp())
                .placeholder(R.drawable.ic_not_available)
                .error(R.drawable.ic_error)
                .into(viewHolder.imgSp);

        viewHolder.etSoluongsp.setText(cart.getSoluong() + "");
        int sl = Integer.parseInt(viewHolder.etSoluongsp.getText().toString());
        if(sl>=10){
            viewHolder.btnThemsp.setVisibility(View.INVISIBLE);
            viewHolder.btnBotsp.setVisibility(View.VISIBLE);
        }else if(sl<=1){
            viewHolder.btnThemsp.setVisibility(View.VISIBLE);
            viewHolder.btnBotsp.setVisibility(View.INVISIBLE);

        }else if (sl>=1){
            viewHolder.btnThemsp.setVisibility(View.VISIBLE);
            viewHolder.btnBotsp.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnThemsp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.etSoluongsp.getText().toString())+1;
                int slht = ShoppingFragment.carts.get(position).getSoluong();
                long giaht = ShoppingFragment.carts.get(position).getGiasp();
                ShoppingFragment.carts.get(position).setSoluong(slmoinhat);
                long giamoinhat = (giaht*slmoinhat)/slht;
                ShoppingFragment.carts.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.tvGiasp.setText(decimalFormat.format(giamoinhat)+"Đ");
                CartActivity.EvenUltil();
                if(slmoinhat>9){
                    finalViewHolder.btnThemsp.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnBotsp.setVisibility(View.VISIBLE);
                    finalViewHolder.etSoluongsp.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btnThemsp.setVisibility(View.VISIBLE);
                    finalViewHolder.btnBotsp.setVisibility(View.VISIBLE);
                    finalViewHolder.etSoluongsp.setText(String.valueOf(slmoinhat));
                }
            }
        });
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.btnBotsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder1.etSoluongsp.getText().toString())-1;
                int slht = ShoppingFragment.carts.get(position).getSoluong();
                long giaht = ShoppingFragment.carts.get(position).getGiasp();
                ShoppingFragment.carts.get(position).setSoluong(slmoinhat);
                long giamoinhat = (giaht*slmoinhat)/slht;
                ShoppingFragment.carts.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder1.tvGiasp.setText(decimalFormat.format(giamoinhat)+"Đ");
                CartActivity.EvenUltil();
                if(slmoinhat<2){
                    finalViewHolder1.btnBotsp.setVisibility(View.INVISIBLE);
                    finalViewHolder1.btnThemsp.setVisibility(View.VISIBLE);
                    finalViewHolder1.etSoluongsp.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder1.btnBotsp.setVisibility(View.VISIBLE);
                    finalViewHolder1.btnThemsp.setVisibility(View.VISIBLE);
                    finalViewHolder1.etSoluongsp.setText(String.valueOf(slmoinhat));
                }

            }
        });

        return view ;
    }
}
