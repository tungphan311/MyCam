package com.example.tung.androidproject.adapter;

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
import com.example.tung.androidproject.model.Cart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MycartAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cart> carts;

    public MycartAdapter(Context context, ArrayList<Cart> carts) {
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
        public TextView tvSoluongsp;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        MycartAdapter.ViewHolder viewHolder = null;
        if (view==null){
            viewHolder = new MycartAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_mycart, null);
            viewHolder.tvTensp = view.findViewById(R.id.tv_mycart_tensp);
            viewHolder.tvGiasp = view.findViewById(R.id.tv_mycart_giasp);
            viewHolder.tvSoluongsp = view.findViewById(R.id.tv_mycart_soluong);
            viewHolder.imgSp = view.findViewById(R.id.imageview_mycart);
            view.setTag(viewHolder);
        }else {
            viewHolder = (MycartAdapter.ViewHolder) view.getTag();
        }

        Cart cart = (Cart) getItem(position);

        viewHolder.tvTensp.setText(cart.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGiasp.setText("Giá: " + decimalFormat.format(cart.getGiasp()) + " đ");
        Picasso.with(context).load(cart.getHinhsp())
                .placeholder(R.drawable.ic_not_available)
                .error(R.drawable.ic_error)
                .into(viewHolder.imgSp);

        viewHolder.tvSoluongsp.setText("Số lượng: " +String.valueOf(cart.getSoluong()));

        return view;
    }
}
