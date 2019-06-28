package com.example.tung.androidproject.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.tung.androidproject.R;
import com.example.tung.androidproject.model.Loaisanpham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaisanphamAdapter extends BaseAdapter {

    ArrayList<Loaisanpham> listLoaisp;
    Context context;

    public LoaisanphamAdapter(ArrayList<Loaisanpham> listLoaisp, Context context) {
        this.listLoaisp = listLoaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listLoaisp.size();
    }

    @Override
    public Object getItem(int position) {
        return listLoaisp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // dùng để lưu giá trị của view để tránh phải load lại view nhiều lần
    public class ViewHolder {
        TextView tvTenLoaiSP;
        ImageView imgLoaiSP;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_loaisanpham, null);
            viewHolder.tvTenLoaiSP = convertView.findViewById(R.id.tv_loaisp);
            viewHolder.imgLoaiSP = convertView.findViewById(R.id.iv_loaisp);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Loaisanpham loaisanpham =(Loaisanpham) getItem(position);
        viewHolder.tvTenLoaiSP.setText(loaisanpham.getTenloaisp());
        Picasso.with(context).load(loaisanpham.getHinhanhloaisp())
                .placeholder(R.drawable.ic_not_available)
                .error(R.drawable.ic_error)
                .into(viewHolder.imgLoaiSP);

        return convertView;
    }
}
