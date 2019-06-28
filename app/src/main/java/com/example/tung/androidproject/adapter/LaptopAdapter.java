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

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> listLaptop;

    public LaptopAdapter(Context context, ArrayList<Sanpham> arrayListProduct) {
        this.context = context;
        this.listLaptop = arrayListProduct;
    }

    @Override
    public int getCount() {
        return listLaptop.size();
    }

    @Override
    public Object getItem(int position) {
        return listLaptop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView tvLaptopName, tvLaptopPrice, tvLaptopDescription;
        public ImageView imgLaptop;
        public LinearLayout linearLayoutLaptop;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LaptopAdapter.ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new LaptopAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_laptop, null);
            viewHolder.tvLaptopName = (TextView) view.findViewById(R.id.textViewNameLaptop);
            viewHolder.tvLaptopPrice = (TextView) view.findViewById(R.id.textViewPriceLaptop);
            viewHolder.tvLaptopDescription = (TextView) view.findViewById(R.id.textViewPriceLaptop);
            viewHolder.imgLaptop = (ImageView) view.findViewById(R.id.imageViewLaptop);
            viewHolder.linearLayoutLaptop = (LinearLayout) view.findViewById(R.id.linearLayoutLaptop);
            view.setTag(viewHolder);
        } else {
            viewHolder = (LaptopAdapter.ViewHolder) view.getTag();
        }

        final Sanpham product = (Sanpham) getItem(position);
        viewHolder.tvLaptopName.setText(product.getTensp());
        viewHolder.tvLaptopName.setSelected(true);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvLaptopPrice.setText("Giá : " + decimalFormat
                .format(product.getGiasp()) + "đ");
        viewHolder.tvLaptopDescription.setMaxLines(2);
        viewHolder.tvLaptopDescription.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvLaptopDescription.setText(product.getMotasp());
        Picasso.with(context).load(product.getHinhanhsp()).placeholder(R.drawable.ic_not_available)
                .error(R.drawable.ic_error)
                .into(viewHolder.imgLaptop);

        return view;
    }
}
