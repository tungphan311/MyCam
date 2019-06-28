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

public class TabletAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayListProduct;

    public TabletAdapter(Context context, ArrayList<Sanpham> arrayListProduct) {
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
        public TextView textViewNameTablet, textViewPriceTablet, textViewDescriptionTablet;
        public ImageView imageViewTablet;
        public LinearLayout linearLayoutTablet;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TabletAdapter.ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new TabletAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_tablet,null);
            viewHolder.textViewNameTablet = (TextView) view.findViewById(R.id.textViewNameTablet);
            viewHolder.textViewPriceTablet = (TextView) view.findViewById(R.id.textViewPriceTablet);
            viewHolder.textViewDescriptionTablet = (TextView) view.findViewById(R.id.textViewDescriptionTablet);
            viewHolder.imageViewTablet = (ImageView) view.findViewById(R.id.imageViewTablet);
            viewHolder.linearLayoutTablet = (LinearLayout) view.findViewById(R.id.linearLayoutTablet);
            view.setTag(viewHolder);
        }else {
            viewHolder = (TabletAdapter.ViewHolder) view.getTag();
        }
        final Sanpham product = (Sanpham) getItem(position);
        viewHolder.textViewNameTablet.setText(product.getTensp());
        viewHolder.textViewNameTablet.setSelected(true);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textViewPriceTablet.setText("Giá : " + decimalFormat
                .format(product.getGiasp()) + "đ");
        viewHolder.textViewDescriptionTablet.setMaxLines(2);
        viewHolder.textViewDescriptionTablet.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.textViewDescriptionTablet.setText(product.getMotasp());
        Picasso.with(context).load(product.getHinhanhsp()).placeholder(R.drawable.ic_not_available)
                .error(R.drawable.ic_error)
                .into(viewHolder.imageViewTablet);
//        viewHolder.linearLayoutTablet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,TabletActivity.class);
//                intent.putExtra("masanphham", product.masp);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
        return view;
    }
}
