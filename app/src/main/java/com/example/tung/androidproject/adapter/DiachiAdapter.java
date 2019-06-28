package com.example.tung.androidproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.tung.androidproject.R;
import com.example.tung.androidproject.model.Diachi;

import java.util.ArrayList;

public class DiachiAdapter extends BaseAdapter {
    Context context;
    ArrayList<Diachi> listDiachi;

    RadioButton selected = null;
    String ten;
    String sodt;
    String diachi;

    public DiachiAdapter(Context context, ArrayList<Diachi> listDiachi) {
        this.context = context;
        this.listDiachi = listDiachi;
    }

    public RadioButton getSelected() {
        return selected;
    }

    public String getTen() {
        return ten;
    }

    public String getSodt() {
        return sodt;
    }

    public String getdiachi() {
        return diachi;
    }

    @Override
    public int getCount() {
        return listDiachi.size();
    }

    @Override
    public Object getItem(int position) {
        return listDiachi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        LinearLayout llDiachi;
        RadioButton rbChondchi;
        TextView tvTen, tvSodt, tvDiachi;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final DiachiAdapter.ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new DiachiAdapter.ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_diachi, null);

            viewHolder.llDiachi = view.findViewById(R.id.ll_diachicuthe);
            viewHolder.rbChondchi = view.findViewById(R.id.rd_chondchi);
            viewHolder.tvTen = view.findViewById(R.id.tv_tennguoinhan);
            viewHolder.tvSodt = view.findViewById(R.id.tv_sodtnguoinhan);
            viewHolder.tvDiachi = view.findViewById(R.id.tv_dchingnhan);

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (DiachiAdapter.ViewHolder) view.getTag();
        }

        final Diachi dchi = (Diachi) getItem(position);

        viewHolder.tvTen.setText(dchi.getHoten());
        viewHolder.tvSodt.setText(dchi.getSodt());
        viewHolder.tvDiachi.setText(dchi.getDiachicuthe());

        viewHolder.rbChondchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected == null) {
                    selected = viewHolder.rbChondchi;
                    selected.setChecked(true);

                    ten = viewHolder.tvTen.getText().toString();
                    sodt = viewHolder.tvSodt.getText().toString();
                    diachi = viewHolder.tvDiachi.getText().toString();
                }
                if (selected == viewHolder.rbChondchi)
                    return;

                selected.setChecked(false);
                viewHolder.rbChondchi.setChecked(true);
                selected = viewHolder.rbChondchi;
                ten = viewHolder.tvTen.getText().toString();
                sodt = viewHolder.tvSodt.getText().toString();
                diachi = viewHolder.tvDiachi.getText().toString();
            }
        });

        return view;
    }
}
