package com.example.tung.androidproject.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
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
import com.example.tung.androidproject.activity.MainScreen;
import com.example.tung.androidproject.activity.MuahangActivity;
import com.example.tung.androidproject.activity.ThemdiachiActivity;
import com.example.tung.androidproject.adapter.DiachiAdapter;
import com.example.tung.androidproject.model.Diachi;
import com.example.tung.androidproject.util.Constran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiachiFragment extends Fragment {
    Button btnContinue;
    Fragment fragment;
    LinearLayout llThemdc;
    ListView listview_diachi;

    ArrayList<Diachi> listdiachi;
    DiachiAdapter adapter;

    RadioButton currentChecked;

    public DiachiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diachi, container, false);

        initView(view);

        initData(MainScreen.user.getMauser());

        initEvent();

        passData(view);

        return view;
    }

    private void passData(View view) {

    }

    private void initData(final int mauser) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constran.getDiachi_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String hoten="";
                String sodt= "";
                String diachi="";
                String iduser="";

                if (response !=null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<response.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            hoten = jsonObject.getString("hoten");
                            sodt = jsonObject.getString("sodienthoai");
                            diachi = jsonObject.getString("diachi");
                            iduser = jsonObject.getString("mauser");

                            listdiachi.add(new Diachi(id,hoten,sodt,diachi,iduser));
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("mauser",String.valueOf(mauser));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void initEvent() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentChecked = adapter.getSelected();

                if (currentChecked == null) {
                    Toast.makeText(getActivity(), "Vui lòng chọn 1 địa chỉ giao hàng!", Toast.LENGTH_SHORT).show();
                }
                else {
                    ((MuahangActivity)getActivity()).setTennguoinhan(adapter.getTen());
                    ((MuahangActivity)getActivity()).setSodtnguoinhan(adapter.getSodt());
                    ((MuahangActivity)getActivity()).setDiachinguoinhan(adapter.getdiachi());

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.framelayout, fragment);
                    getActivity().overridePendingTransition(R.anim.leftin, R.anim.rightout);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        llThemdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ThemdiachiActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView(View view) {
        btnContinue = view.findViewById(R.id.btn_diachi);
        fragment = new ThanhtoanFragment();
        llThemdc = view.findViewById(R.id.ll_themdchi);

        listdiachi = new ArrayList<>();
        adapter = new DiachiAdapter(getActivity().getApplicationContext(), listdiachi);
        listview_diachi = view.findViewById(R.id.listview_diachi);
        listview_diachi.setAdapter(adapter);
    }
}
