package com.example.tung.androidproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import com.example.tung.androidproject.util.Constran;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThemdiachiActivity extends AppCompatActivity {
    Spinner spnTinh, spnHuyen;
    ArrayList<String> listTinh;
    ArrayList<String> listHuyen;
    ArrayList<String> listXa;

    Button btnLuu;
    TextView tvHoten, tvSodt, tvDiachi;

    String tinh, huyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themdiachi);

        initView();

        try {
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getDataSpinnerTinh();

        initEvent();
    }

    private void initEvent() {
        spnTinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    listHuyen.clear();
                    listHuyen.add("Quận / Huyện");
                }
                else {
                    try {
                        tinh = listTinh.get(position);
                        getListHuyen(tinh);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                getDataSpinnerHuyen();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = tvHoten.getText().toString();
                String sodt = tvSodt.getText().toString();
                String diachi = tvDiachi.getText().toString();

                if (hoten.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền họ tên người nhận", Toast.LENGTH_SHORT);
                }
                else if (sodt.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền số điện thoại người nhận", Toast.LENGTH_SHORT);
                }
                else if (spnTinh.getSelectedItemId() == 0) {
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn tỉnh / thành phố", Toast.LENGTH_SHORT);
                }
                else if (spnHuyen.getSelectedItemId() == 0) {
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn quận / huyện", Toast.LENGTH_SHORT);
                }
                else if (diachi.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền địa chỉ người nhận", Toast.LENGTH_SHORT);
                }
                else {
                    if (!checksdt(sodt)) {
                        Toast.makeText(ThemdiachiActivity.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        themDiachigiaohang(hoten, sodt, spnTinh.getSelectedItem().toString(), spnHuyen.getSelectedItem().toString(), diachi, MainScreen.user.getMauser());

                        finish();
                        overridePendingTransition(R.anim.leftin, R.anim.rightout);
                        Intent intent = new Intent(getApplicationContext(), MuahangActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public boolean checkdauso(String dauso){
        if (dauso.equals("032")||dauso.equals("033")||dauso.equals("034")||dauso.equals("035")||dauso.equals("036")||dauso.equals("037")||dauso.equals("038")||dauso.equals("039")||
                dauso.equals("056")||dauso.equals("057")||dauso.equals("058")||
                dauso.equals("70")||dauso.equals("076")||dauso.equals("077")||dauso.equals("077")||dauso.equals("078")||dauso.equals("079")||
                dauso.equals("081")||dauso.equals("082")||dauso.equals("083")||dauso.equals("084")||dauso.equals("085")||dauso.equals("086")||
                dauso.equals("088")||dauso.equals("089")||dauso.equals("091")||dauso.equals("090")||dauso.equals("092")||dauso.equals("093")||dauso.equals("094")||
                dauso.equals("096")||dauso.equals("097")||dauso.equals("098")){
            return true;
        }
        return false;
    }
    public boolean checksdt(String sdt){
        if (sdt.length()==10){
            if (checkdauso(sdt.substring(0,3))){
                return true;
            }
        }
        return false;
    }

    private void getDataSpinnerTinh() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listTinh);
        spnTinh.setAdapter(adapter);
    }

    private void getDataSpinnerHuyen() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listHuyen);
        spnHuyen.setAdapter(adapter);
    }

    private void initView() {
        spnTinh = findViewById(R.id.spinner_tinh);
        spnHuyen = findViewById(R.id.spinner_huyen);
        btnLuu = findViewById(R.id.btn_luu);
        tvHoten = findViewById(R.id.et_tennguoinhan);
        tvSodt = findViewById(R.id.et_phonengnhan);
        tvDiachi = findViewById(R.id.et_diachicuthe);

        listTinh = new ArrayList<>();
        listHuyen = new ArrayList<>();
        listXa = new ArrayList<>();
    }

    public void readData() throws IOException {
        String data = "";
        InputStream in = getAssets().open("tinh.txt");
        InputStreamReader inreader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(inreader);
        String tinh = "";
        listTinh.clear();
        listTinh.add("Tỉnh / Thành phố");

        if (in != null) {
            try {
                while ((data = bufferedReader.readLine()) != null) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(data);
                    tinh = builder.toString();
                    listTinh.add(tinh);
                }
                in.close();
            }
            catch (IOException ex) {
                Log.e("ERROR", ex.getMessage());
            }
        }
    }

    public void getListHuyen(String huyen) throws  IOException {
        String data = "";
        InputStream in = getAssets().open(huyen + ".txt");
        InputStreamReader inreader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(inreader);
        String Huyen = "";
        listHuyen.clear();
        listHuyen.add("Quận / Huyện");

        if (in != null) {
            try {
                while ((data = bufferedReader.readLine()) != null) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(data);
                    Huyen = builder.toString();
                    listHuyen.add(Huyen);
                }
                in.close();
            }
            catch (IOException ex) {
                Log.e("ERROR", ex.getMessage());
            }
        }
    }

    private void themDiachigiaohang(final String name, final String userphone, String thanhpho, String quan, String chitiet, final int mauser) {
        final String result = chitiet + ", " + quan + ", " + thanhpho;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constran.insertDiachi_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("hoten", String.valueOf(name));
                param.put("sodt", String.valueOf(userphone));
                param.put("diachi", String.valueOf(result));
                param.put("mauser", String.valueOf(mauser));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
