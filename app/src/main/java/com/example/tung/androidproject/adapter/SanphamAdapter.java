package com.example.tung.androidproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tung.androidproject.R;
import com.example.tung.androidproject.activity.ProducDetailActivity;
import com.example.tung.androidproject.model.Sanpham;
import com.example.tung.androidproject.util.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> {
    Context context;  // truyền vào màn hình muốn vẽ
    ArrayList<Sanpham> listSanpham;

    public SanphamAdapter(Context context, ArrayList<Sanpham> listSanpham) {
        this.context = context;
        this.listSanpham = listSanpham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rc_sanphammoinhat, null);

        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        // truyền giá trị vào
        Sanpham sanpham = listSanpham.get(i);
        itemHolder.txtTenSP.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        itemHolder.texGiaSP.setText("Giá: " + decimalFormat.format(sanpham.getGiasp()) + " đ");
        Picasso.with(context).load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.ic_not_available)
                .error(R.drawable.ic_error)
                .into(itemHolder.imgHinhSP);
    }

    @Override
    public int getItemCount() {
        return listSanpham.size();
    }

    // dùng để lưu giá trị cho 1 sản phẩm
    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imgHinhSP;
        public TextView txtTenSP, texGiaSP;

        public ItemHolder(@NonNull View itemView) {

            super(itemView);

            imgHinhSP = itemView.findViewById(R.id.iv_sanpham);
            txtTenSP = itemView.findViewById(R.id.tv_tensp);
            texGiaSP = itemView.findViewById(R.id.tv_giasp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProducDetailActivity.class);
                    intent.putExtra("thongtinsanpham", listSanpham.get(getLayoutPosition()));
                    CheckConnection.ShowToast_Short(context, listSanpham.get(getLayoutPosition()).getTensp());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
