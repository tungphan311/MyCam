package com.example.tung.androidproject.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tung.androidproject.R;
import com.example.tung.androidproject.adapter.CartAdapter;
import com.example.tung.androidproject.fragment.ShoppingFragment;
import com.example.tung.androidproject.util.CheckConnection;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    ListView listViewCart;
    TextView tvThongbao;
    static TextView tvTongtien;
    Button btnThanhtoan, btnMuatiep;
    android.support.v7.widget.Toolbar toolbarCart;
    CartAdapter cartAdapter;
    Button botsp;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Anhxa();
        ActionToolbar();
        CheckData();
        EvenUltil();
        CatchOnItemListView();
        EvenButton();
    }


    private void EvenButton() {
        btnMuatiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),MainScreen.class);
            startActivity(intent);
            }
        });
        btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ShoppingFragment.carts.size()>0){
                    if (MainScreen.isDangNhap) {
                        Intent intent = new Intent(getApplicationContext(), MuahangActivity.class);
                        intent.putExtra("tongtien", tvTongtien.getText().toString());
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(CartActivity.this, "Vui lòng đăng nhập trước khi thanh toán!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Giỏ hàng của bạn chưa có sản phẩm đề thanh toán");
                }
            }
        });
    }

    private void CatchOnItemListView() {
        listViewCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Xacnhanxoa(position);
                return true;
            }
        });
    }

    public void myClickHandler (View view) {
        LinearLayout parent = (LinearLayout) view.getParent();
        TextView child = (TextView)parent.getChildAt(0);
        for (int i=0 ; i<ShoppingFragment.carts.size(); i++) {
            if (ShoppingFragment.carts.get(i).getTensp().equals(child.getText())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc xóa sản phẩm này");
                final int finalI = i;
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int k) {
                        if (ShoppingFragment.carts.size()<=0){
                            tvThongbao.setVisibility(View.VISIBLE);
                        }else {
                            ShoppingFragment.carts.remove(finalI);
                            cartAdapter.notifyDataSetChanged();
                            EvenUltil();
                            if (ShoppingFragment.carts.size()<=0){
                                tvThongbao.setVisibility(View.VISIBLE);
                            }else {
                                tvThongbao.setVisibility(View.INVISIBLE);
                                cartAdapter.notifyDataSetChanged();
                                EvenUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartAdapter.notifyDataSetChanged();
                        EvenUltil();
                    }
                });
                builder.show();
            }
        }
    }

    private void Xacnhanxoa(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setTitle("Xác nhận xóa sản phẩm");
        builder.setMessage("Bạn có chắc xóa sản phẩm này");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (ShoppingFragment.carts.size()<=0){
                    tvThongbao.setVisibility(View.VISIBLE);
                }else {
                    ShoppingFragment.carts.remove(position);
                    cartAdapter.notifyDataSetChanged();
                    EvenUltil();
                    if (ShoppingFragment.carts.size()<=0){
                        tvThongbao.setVisibility(View.VISIBLE);
                    }else {
                        tvThongbao.setVisibility(View.INVISIBLE);
                        cartAdapter.notifyDataSetChanged();
                        EvenUltil();
                    }
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cartAdapter.notifyDataSetChanged();
                EvenUltil();
            }
        });
        builder.show();
    }

    @SuppressLint("SetTextI18n")
    public static void EvenUltil() {
        long tongtien = 0;
        for (int i = 0; i<ShoppingFragment.carts.size();i++){
            tongtien += ShoppingFragment.carts.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongtien.setText(decimalFormat.format(tongtien)+ "đ");
    }

    private void CheckData() {
        if (ShoppingFragment.carts.size() <= 0) {
            cartAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.VISIBLE);
            listViewCart.setVisibility(View.INVISIBLE);
        }
        else {
            cartAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.INVISIBLE);
            listViewCart.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        listViewCart = findViewById(R.id.lv_cart);
        tvThongbao = findViewById(R.id.tv_cart_noitem);
        tvTongtien = findViewById(R.id.tv_totalcost);
        btnThanhtoan = findViewById(R.id.btn_thanhtoan);
        btnMuatiep = findViewById(R.id.btn_tieptucmuahang);
        toolbarCart = findViewById(R.id.toolbar_cart);
        cartAdapter = new CartAdapter(CartActivity.this, ShoppingFragment.carts);
        listViewCart.setAdapter(cartAdapter);
    }
}
