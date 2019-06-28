package com.example.tung.androidproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tung.androidproject.R;
import com.example.tung.androidproject.adapter.PhukienAdapter;
import com.example.tung.androidproject.model.Sanpham;
import com.example.tung.androidproject.util.CheckConnection;
import com.example.tung.androidproject.util.Constran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhukienActivity extends AppCompatActivity {

    Toolbar toolbarphukien;
    ListView listViewphukien;
    PhukienAdapter phukienAdapter;
    ArrayList<Sanpham> arrayListPhukien;
    View footerView;
    boolean isLoading = false;
    PhukienActivity.mHandler mHandler;
    boolean limitData = false; // check xem còn data không

    int idPhukien =0;
    int page =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phukien);

        Anhxa();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            GetMaLoaiSP();
            ActionToolbbar();
            GetDataPhukien(page);
            LoadMoreData();
        }
        else {
            CheckConnection.ShowToast_Short(getApplicationContext(), Constran.connectionErrorMessage);
            finish();
        }

    }

    private void LoadMoreData() {
        listViewphukien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PhukienActivity.this, ProducDetailActivity.class);
                intent.putExtra("thongtinsanpham", arrayListPhukien.get(position));
                startActivity(intent);
            }
        });

        listViewphukien.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    private void GetDataPhukien(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String pathPhukien = Constran.getPhukien_URL + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(pathPhukien, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int idPhukien = 0;
                String namePhukien = "";
                int pricePhukien = 0;
                String imagePhukien = "";
                String descriptionPhukien = "";
                int idloaisp =0;
                int mahangsanxuat = 0;

                if (response !=null && response.length() != 2){
                    listViewphukien.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            idPhukien = jsonObject.getInt("masanpham");
                            namePhukien = jsonObject.getString("tensanpham");
                            pricePhukien = jsonObject.getInt("giasanpham");
                            imagePhukien=jsonObject.getString("hinhanhsanpham");
                            descriptionPhukien = jsonObject.getString("motasanpham");
                            idloaisp = jsonObject.getInt("maloaisanpham");
                            mahangsanxuat = jsonObject.getInt("mahangsanxuat");

                            arrayListPhukien.add(new Sanpham(idPhukien,namePhukien,pricePhukien,imagePhukien,descriptionPhukien,idloaisp, mahangsanxuat));
                            phukienAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    limitData = true;
                    listViewphukien.removeFooterView(footerView);
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
        setSupportActionBar(toolbarphukien);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarphukien.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetMaLoaiSP() {
        idPhukien = getIntent().getIntExtra("maloaisanpham", -1);
        Log.d("gia tri maloaisanpham: ",idPhukien +"");
    }

    private void Anhxa() {
        toolbarphukien = findViewById(R.id.toolBarphukien);
        listViewphukien = findViewById(R.id.listViewphukien);
        arrayListPhukien = new ArrayList<>();
        phukienAdapter = new PhukienAdapter(getApplicationContext(), arrayListPhukien);
        listViewphukien.setAdapter(phukienAdapter);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
        mHandler = new mHandler();
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    listViewphukien.addFooterView(footerView);
                    break;

                case 1:
                    GetDataPhukien(++page);
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
