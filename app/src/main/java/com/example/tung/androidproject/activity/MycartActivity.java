package com.example.tung.androidproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tung.androidproject.R;
import com.example.tung.androidproject.adapter.MycartAdapter;
import com.example.tung.androidproject.model.Cart;
import com.example.tung.androidproject.model.Loaisanpham;
import com.example.tung.androidproject.util.Constran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MycartActivity extends AppCompatActivity {

    ListView listViewmyCart;
    Button btntrove;
    MycartAdapter mycartAdapter;
    ArrayList<Cart> mycart;
    int masanpham = 0;
    TextView thongbao;

    String tensanpham  ="";
    long giasanpham =0;
    String hinhanhsanpham="";
    int soluongsanpham =0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);
        Anhxa();
        loaddata();
        Event();
    }


    private void Event() {
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loaddata() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String request = Constran.getmycart_URL+String.valueOf(MainScreen.user.getMauser());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(request, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null && response.length() > 0) {
                    thongbao.setVisibility(View.INVISIBLE);
                    for (int i=0; i<response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            masanpham = jsonObject.getInt("masanpham");
                            tensanpham = jsonObject.getString("tensanpham");

                            giasanpham = jsonObject.getInt("giasanpham");
                            hinhanhsanpham = jsonObject.getString("hinhanhsanpham");
                            soluongsanpham = jsonObject.getInt("soluongsanpham");

                            mycart.add(new Cart(masanpham,tensanpham, giasanpham, hinhanhsanpham,soluongsanpham));
                            mycartAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    thongbao.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "get loaisanpham error", Toast.LENGTH_SHORT).show();
            }
        });


        requestQueue.add(jsonArrayRequest);
    }

    private void Anhxa() {
        listViewmyCart = findViewById(R.id.mylistcart);
        btntrove = findViewById(R.id.btn_mycart_trove);
        mycart= new ArrayList<>();
        mycartAdapter = new MycartAdapter(MycartActivity.this,mycart);
        listViewmyCart.setAdapter(mycartAdapter);
        thongbao = findViewById(R.id.thongbao_mycart);
    }
}
