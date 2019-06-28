package com.example.tung.androidproject.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tung.androidproject.R;
import com.example.tung.androidproject.activity.Chucmungactivity;
import com.example.tung.androidproject.activity.MainScreen;
import com.example.tung.androidproject.activity.MuahangActivity;
import com.example.tung.androidproject.adapter.XacnhanAdapter;
import com.example.tung.androidproject.util.CheckConnection;
import com.example.tung.androidproject.util.Constran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class XacnhanFragment extends Fragment {
    TextView tvTongtien, tvTen, tvSodt, tvDiachi;
    ListView listView;
    XacnhanAdapter adapter;
    Button confirm;
    int mauser;

    public XacnhanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_xacnhan, container, false);

        initView(view);

        initData();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertdckh(MainScreen.user.getMauser());
            }
        });

        return view;
    }

    private void initView(View view) {
        tvTongtien = view.findViewById(R.id.tv_tongtien);
        listView = view.findViewById(R.id.listitem);
        adapter = new XacnhanAdapter(getActivity().getApplicationContext(), ShoppingFragment.carts);
        listView.setAdapter(adapter);
        tvTen = view.findViewById(R.id.textview_ten);
        tvSodt = view.findViewById(R.id.textview_sodt);
        tvDiachi = view.findViewById(R.id.textview_dchi);
        confirm = view.findViewById(R.id.btn_confirm);
    }

    private void initData() {
        String total =((MuahangActivity)getActivity()).getTongtien();
        total = tvTongtien.getText().toString() + total;
        tvTongtien.setText(total);

        String ten = ((MuahangActivity)getActivity()).getTennguoinhan();
        tvTen.setText(ten);

        String sophone = ((MuahangActivity)getActivity()).getSodtnguoinhan();
        tvSodt.setText(sophone);

        String address = ((MuahangActivity)getActivity()).getDiachinguoinhan();
        tvDiachi.setText(address);
    }
    private void insertdckh(final int Mauser) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constran.donhang_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(final String madonhang) {
                Toast.makeText(getActivity().getApplicationContext(),"Thanh cong",Toast.LENGTH_SHORT).show();
                if(Integer.parseInt(madonhang)>0){
                    RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    StringRequest request = new StringRequest(Request.Method.POST, Constran.chitietdonhang_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("1")){
                                ShoppingFragment.carts.clear();
                                CheckConnection.ShowToast_Short(getActivity().getApplicationContext(),"Bạn đã đặt hàng thành công");
                                Intent intent = new Intent(getActivity().getApplicationContext(),Chucmungactivity.class);
                                startActivity(intent);
                            }else {
                                CheckConnection.ShowToast_Short(getActivity().getApplicationContext(),"Đặt hàng không thành công");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            JSONArray jsonArray = new JSONArray();
                            for (int i= 0; i<ShoppingFragment.carts.size();i++){
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("madonhang",madonhang);
                                    jsonObject.put("masanpham",ShoppingFragment.carts.get(i).getMasp());
                                    jsonObject.put("tensanpham",ShoppingFragment.carts.get(i).getTensp());
                                    jsonObject.put("giasanpham",ShoppingFragment.carts.get(i).getGiasp());
                                    jsonObject.put("soluongsanpham",ShoppingFragment.carts.get(i).getSoluong());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonArray.put(jsonObject);
                            }
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("json",jsonArray.toString());
                            return hashMap;
                        }
                    };
                    queue.add(request);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("mauser", String.valueOf(Mauser));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
