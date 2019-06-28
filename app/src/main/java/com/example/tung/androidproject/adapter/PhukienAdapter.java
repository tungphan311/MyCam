package com.example.tung.androidproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tung.androidproject.R;
import com.example.tung.androidproject.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PhukienAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayListProduct;

    public PhukienAdapter(Context context, ArrayList<Sanpham> arrayListProduct) {
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
        public TextView textViewNamePhukien, textViewPricePhukien, textViewDescriptionPhukien;
        public ImageView imageViewPhukien;
        public LinearLayout linearLayoutPhukien;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        PhukienAdapter.ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new PhukienAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_phukien,null);
            viewHolder.textViewNamePhukien = (TextView) view.findViewById(R.id.textViewNamePhukien);
            viewHolder.textViewPricePhukien = (TextView) view.findViewById(R.id.textViewPricePhukien);
            viewHolder.textViewDescriptionPhukien = (TextView) view.findViewById(R.id.textViewDescriptionPhukien);
            viewHolder.imageViewPhukien = (ImageView) view.findViewById(R.id.imageViewPhukien);
            viewHolder.linearLayoutPhukien = (LinearLayout) view.findViewById(R.id.linearLayoutPhukien);
            view.setTag(viewHolder);
        }else {
            viewHolder = (PhukienAdapter.ViewHolder) view.getTag();
        }
        final Sanpham product = (Sanpham) getItem(position);
        viewHolder.textViewNamePhukien.setText(product.getTensp());
        viewHolder.textViewNamePhukien.setSelected(true);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textViewPricePhukien.setText("Giá : " + decimalFormat
                .format(product.getGiasp()) + "đ");
        viewHolder.textViewDescriptionPhukien.setMaxLines(2);
        viewHolder.textViewDescriptionPhukien.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.textViewDescriptionPhukien.setText(product.getMotasp());
        Picasso.with(context).load(product.getHinhanhsp()).placeholder(R.drawable.ic_not_available)
                .error(R.drawable.ic_error)
                .into(viewHolder.imageViewPhukien);
//        viewHolder.linearLayoutPhukien.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,PhukienActivity.class);
//                intent.putExtra("masanphham", product.masp);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
        return view;
    }
}
