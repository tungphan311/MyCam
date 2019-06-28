package com.example.tung.androidproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tung.androidproject.R;
import com.example.tung.androidproject.model.ItemTK;

import java.util.ArrayList;

public class MoreAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ItemTK> listItem;

    public MoreAdapter(Context context, ArrayList<ItemTK> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.listview_more, null);

        ImageView ivItem = view.findViewById(R.id.iv_chucnang);
        TextView tvChucnang = view.findViewById(R.id.tv_chucnang);

        ItemTK item = listItem.get(position);
        ivItem.setImageResource(item.getIcon());
        tvChucnang.setText(item.getTenChucNang());

        return view;
    }
}
