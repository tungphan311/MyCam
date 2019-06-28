package com.example.tung.androidproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tung.androidproject.R;
import com.example.tung.androidproject.activity.PhoneActivity;
import com.example.tung.androidproject.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PhoneAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayListProduct;

    public PhoneAdapter(Context context, ArrayList<Sanpham> arrayListProduct) {
        this.context = context;
        this.arrayListProduct = arrayListProduct;
    }

    @Override
    public int getCount() {
        return arrayListProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView textViewNamePhone, textViewPricePhone, textViewDescriptionPhone;
        public ImageView imageViewPhone;
        public LinearLayout linearLayoutPhone;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_phone,null);
            viewHolder.textViewNamePhone = (TextView) view.findViewById(R.id.textViewNamePhone);
            viewHolder.textViewPricePhone = (TextView) view.findViewById(R.id.textViewPricePhone);
            viewHolder.textViewDescriptionPhone = (TextView) view.findViewById(R.id.textViewDescriptionPhone);
            viewHolder.imageViewPhone = (ImageView) view.findViewById(R.id.imageViewPhone);
            viewHolder.linearLayoutPhone = (LinearLayout) view.findViewById(R.id.linearLayoutPhone);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final Sanpham product = (Sanpham) getItem(position);
        viewHolder.textViewNamePhone.setText(product.getTensp());
        viewHolder.textViewNamePhone.setSelected(true);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textViewPricePhone.setText("Giá : " + decimalFormat
                .format(product.getGiasp()) + "đ");
        viewHolder.textViewDescriptionPhone.setMaxLines(2);
        viewHolder.textViewDescriptionPhone.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.textViewDescriptionPhone.setText(product.getMotasp());
        Picasso.with(context).load(product.getHinhanhsp()).placeholder(R.drawable.ic_not_available)
                .error(R.drawable.ic_error)
                .into(viewHolder.imageViewPhone);
//        viewHolder.linearLayoutPhone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,PhoneActivity.class);
//                intent.putExtra("masanphham", product.masp);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
        return view;
    }
}
