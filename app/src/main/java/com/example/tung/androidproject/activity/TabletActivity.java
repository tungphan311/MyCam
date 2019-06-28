package com.example.tung.androidproject.activity;

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
import com.example.tung.androidproject.adapter.PhoneAdapter;
import com.example.tung.androidproject.adapter.TabletAdapter;
import com.example.tung.androidproject.model.Sanpham;
import com.example.tung.androidproject.util.CheckConnection;
import com.example.tung.androidproject.util.Constran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TabletActivity extends AppCompatActivity {

    Toolbar toolbarTablet;
    ListView listViewTablet;
    TabletAdapter tabletAdapter;
    ArrayList<Sanpham> arrayListTablet;
    View footerView;
    boolean isLoading = false;
    TabletActivity.mHandler mHandler;
    boolean limitData = false; // check xem còn data không

    int idTablet =0;
    int page =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);

        Anhxa();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            GetMaLoaiSP();
            ActionToolbbar();
            GetDataTablet(page);
            LoadMoreData();
        }
        else {
            CheckConnection.ShowToast_Short(getApplicationContext(), Constran.connectionErrorMessage);
            finish();
        }

    }

    private void LoadMoreData() {
        listViewTablet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TabletActivity.this, ProducDetailActivity.class);
                intent.putExtra("thongtinsanpham", arrayListTablet.get(position));
                startActivity(intent);
            }
        });

        listViewTablet.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 &&
                        isLoading == false && limitData == false) {
                    isLoading = true;
                    TabletActivity.threadData threadData = new TabletActivity.threadData();
                    threadData.start();

                }
            }
        });
    }

    private void GetDataTablet(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String pathTablet = Constran.getTablet_URL + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(pathTablet, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int idTablet = 0;
                String nameTablet = "";
                int priceTablet = 0;
                String imageTablet = "";
                String descriptionTablet = "";
                int idloaisp =0;
                int mahangsanxuat = 0;

                if (response !=null && response.length() != 2){
                    listViewTablet.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            idTablet = jsonObject.getInt("masanpham");
                            nameTablet = jsonObject.getString("tensanpham");
                            priceTablet = jsonObject.getInt("giasanpham");
                            imageTablet=jsonObject.getString("hinhanhsanpham");
                            descriptionTablet = jsonObject.getString("motasanpham");
                            idloaisp = jsonObject.getInt("maloaisanpham");
                            mahangsanxuat = jsonObject.getInt("mahangsanxuat");

                            arrayListTablet.add(new Sanpham(idTablet,nameTablet,priceTablet,imageTablet,descriptionTablet,idloaisp, mahangsanxuat));
                            tabletAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    limitData = true;
                    listViewTablet.removeFooterView(footerView);
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
        setSupportActionBar(toolbarTablet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTablet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetMaLoaiSP() {
        idTablet = getIntent().getIntExtra("maloaisanpham", -1);
        Log.d("gia tri maloaisanpham: ",idTablet +"");
    }

    private void Anhxa() {
        toolbarTablet = findViewById(R.id.toolBarTablet);
        listViewTablet = findViewById(R.id.listViewTablet);
        arrayListTablet = new ArrayList<>();
        tabletAdapter = new TabletAdapter(getApplicationContext(), arrayListTablet);
        listViewTablet.setAdapter(tabletAdapter);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
        mHandler = new mHandler();
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    listViewTablet.addFooterView(footerView);
                    break;

                case 1:
                    GetDataTablet(++page);
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
