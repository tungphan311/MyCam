package com.example.tung.androidproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tung.androidproject.R;
import com.example.tung.androidproject.model.Cart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class XacnhanAdapter extends BaseAdapter {
    Context cont;
    ArrayList<Cart> carts;

    public XacnhanAdapter(Context cont, ArrayList<Cart> carts) {
        this.cont = cont;
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
        TextView tvTensp, tvGiasp, tvSoluong;
        ImageView imgHinhsp;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        XacnhanAdapter.ViewHolder viewHolder = null;

        if (view == null) {
            viewHolder = new XacnhanAdapter.ViewHolder();

            LayoutInflater inflater = (LayoutInflater) cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_xacnhan, null);

            viewHolder.tvTensp = view.findViewById(R.id.tv_confirm_tensp);
            viewHolder.tvGiasp = view.findViewById(R.id.tv_confirm_giasp);
            viewHolder.tvSoluong = view.findViewById(R.id.tv_confirm_soluong);
            viewHolder.imgHinhsp = view.findViewById(R.id.imageview_cart);

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (XacnhanAdapter.ViewHolder) view.getTag();
        }

        Cart cart = (Cart) getItem(position);

        viewHolder.tvTensp.setText(cart.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGiasp.setText("Giá: " + decimalFormat.format(cart.getGiasp()) + " đ");
        viewHolder.tvSoluong.setText("Số lượng: " + String.valueOf(cart.getSoluong()));
        Picasso.with(cont).load(cart.getHinhsp())
                .placeholder(R.drawable.ic_not_available)
                .error(R.drawable.ic_error)
                .into(viewHolder.imgHinhsp);

        return view;
    }
}
