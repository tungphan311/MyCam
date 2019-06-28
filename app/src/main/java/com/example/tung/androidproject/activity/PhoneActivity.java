package com.example.tung.androidproject.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tung.androidproject.R;
import com.example.tung.androidproject.adapter.PhoneAdapter;
import com.example.tung.androidproject.model.Sanpham;
import com.example.tung.androidproject.util.CheckConnection;
import com.example.tung.androidproject.util.Constran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhoneActivity extends AppCompatActivity {

    Toolbar toolbarPhone;
    ListView listViewPhone;
    PhoneAdapter phoneAdapter;
    ArrayList<Sanpham> arrayListPhone;
    View footerView;
    boolean isLoading = false;
    mHandler mHandler;
    boolean limitData = false; // check xem còn data không

    int idPhone =0;
    int page =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        Anhxa();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            GetMaLoaiSP();
            ActionToolbbar();
            GetDataPhone(page);
            LoadMoreData();
        }
        else {
            CheckConnection.ShowToast_Short(getApplicationContext(), Constran.connectionErrorMessage);
            finish();
        }

    }

    private void LoadMoreData() {
        listViewPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PhoneActivity.this, ProducDetailActivity.class);
                intent.putExtra("thongtinsanpham", arrayListPhone.get(position));
                startActivity(intent);
            }
        });

        listViewPhone.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 &&
                        isLoading == false && limitData == false) {
                    isLoading = true;
                    threadData threadData = new threadData();
                    threadData.start();

                }
            }
        });
    }

    private void GetDataPhone(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String pathPhone = Constran.getPhone_URL + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(pathPhone, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int idPhone = 0;
                String namePhone = "";
                int pricePhone = 0;
                String imagePhone = "";
                String descriptionPhone = "";
                int idloaisp =0;
                int mahangsanxuat = 0;

                if (response !=null && response.length() != 2){
                    listViewPhone.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            idPhone = jsonObject.getInt("masanpham");
                            namePhone = jsonObject.getString("tensanpham");
                            pricePhone = jsonObject.getInt("giasanpham");
                            imagePhone=jsonObject.getString("hinhanhsanpham");
                            descriptionPhone = jsonObject.getString("motasanpham");
                            idloaisp = jsonObject.getInt("maloaisanpham");
                            mahangsanxuat = jsonObject.getInt("mahangsanxuat");

                            arrayListPhone.add(new Sanpham(idPhone,namePhone,pricePhone,imagePhone,descriptionPhone,idloaisp, mahangsanxuat));
                            phoneAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    limitData = true;
                    listViewPhone.removeFooterView(footerView);
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Đã hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), Constran.connectionErrorMessage);
            }
        });
        requestQueue.add(stringRequest);
    }

    private void ActionToolbbar() {
        setSupportActionBar(toolbarPhone);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarPhone.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetMaLoaiSP() {
        idPhone = getIntent().getIntExtra("maloaisanpham", -1);
        Log.d("gia tri maloaisanpham: ",idPhone +"");
    }

    private void Anhxa() {
        toolbarPhone = findViewById(R.id.toolBarPhone);
        listViewPhone = findViewById(R.id.listViewPhone);
        arrayListPhone = new ArrayList<>();
        phoneAdapter = new PhoneAdapter(getApplicationContext(), arrayListPhone);
        listViewPhone.setAdapter(phoneAdapter);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
        mHandler = new mHandler();
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    listViewPhone.addFooterView(footerView);
                    break;

                case 1:
                    GetDataPhone(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class threadData extends Thread {

        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message msg = mHandler.obtainMessage(1);
            mHandler.sendMessage(msg);
            super.run();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cart:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
