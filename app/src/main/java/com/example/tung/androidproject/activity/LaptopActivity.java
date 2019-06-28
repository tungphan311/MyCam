package com.example.tung.androidproject.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tung.androidproject.R;
import com.example.tung.androidproject.adapter.LaptopAdapter;
import com.example.tung.androidproject.adapter.PhoneAdapter;
import com.example.tung.androidproject.model.Sanpham;
import com.example.tung.androidproject.util.CheckConnection;
import com.example.tung.androidproject.util.Constran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LaptopActivity extends AppCompatActivity {

    Toolbar toolbarLaptop;
    ListView listViewLaptop;
    LaptopAdapter laptopAdapter;
    ArrayList<Sanpham> listLaptop;
    View footerView;
    boolean isLoading = false;
    LaptopActivity.mHandler mHandler;
    boolean limitData = false; // check xem còn data không

    int page =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);

        Anhxa();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionToolbbar();
            GetDataLaptop(page);
            LoadMoreData();
        }
        else {
            CheckConnection.ShowToast_Short(getApplicationContext(), Constran.connectionErrorMessage);
            finish();
        }
    }

    private void LoadMoreData() {
        listViewLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LaptopActivity.this, ProducDetailActivity.class);
                intent.putExtra("thongtinsanpham", listLaptop.get(position));
                startActivity(intent);
            }
        });

        listViewLaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 &&
                        isLoading == false && limitData == false) {
                    isLoading = true;
                    LaptopActivity.threadData threadData = new LaptopActivity.threadData();
                    threadData.start();

                }
            }
        });
    }

    private void GetDataLaptop(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String pathLaptop = Constran.getLaptop_URL + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(pathLaptop, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int malap = 0;
                String tenlap = "";
                int gialap = 0;
                String imglap = "";
                String motalap = "";
                int maloaisp =0;
                int mahangsanxuat = 0;

                if (response !=null && response.length() != 2){
                    listViewLaptop.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            malap = jsonObject.getInt("masanpham");
                            tenlap = jsonObject.getString("tensanpham");
                            gialap = jsonObject.getInt("giasanpham");
                            imglap=jsonObject.getString("hinhanhsanpham");
                            motalap = jsonObject.getString("motasanpham");
                            maloaisp = jsonObject.getInt("maloaisanpham");
                            mahangsanxuat = jsonObject.getInt("mahangsanxuat");

                            listLaptop.add(new Sanpham(malap,tenlap,gialap,imglap,motalap,maloaisp, mahangsanxuat));
                            laptopAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    limitData = true;
                    listViewLaptop.removeFooterView(footerView);
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
        setSupportActionBar(toolbarLaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarLaptop = findViewById(R.id.toolBarLaptop);
        listViewLaptop = findViewById(R.id.listViewLaptop);
        listLaptop = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(getApplicationContext(), listLaptop);
        listViewLaptop.setAdapter(laptopAdapter);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
        mHandler = new LaptopActivity.mHandler();
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    listViewLaptop.addFooterView(footerView);
                    break;

                case 1:
                    GetDataLaptop(++page);
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
