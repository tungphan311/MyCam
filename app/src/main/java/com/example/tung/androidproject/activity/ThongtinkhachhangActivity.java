package com.example.tung.androidproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tung.androidproject.R;
import com.example.tung.androidproject.fragment.ShoppingFragment;
import com.example.tung.androidproject.util.CheckConnection;
import com.example.tung.androidproject.util.Constran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThongtinkhachhangActivity extends AppCompatActivity {
    EditText edttenkhachhang, edtsdt, edtemail;
    Button btnxacnhan, btntrove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);
        Anhxa();
        getDetail();

        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
        }
    }

    private void getDetail() {
        edttenkhachhang.setText(MainScreen.user.getHoten());
        edtsdt.setText(MainScreen.user.getSodienthoai());
        edtemail.setText(MainScreen.user.getEmail());
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

    public int checkthongtin(){
        String ten = edttenkhachhang.getText().toString();
        String sdt = edtsdt.getText().toString();
        String email = edtemail.getText().toString();
        String emailPattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        int count_sdt =0;
        int count_email = 0;
        int count_phu =0;
        int count_tong =0;
        String dauso ="";
        Pattern regex_email = Pattern.compile(emailPattern);
        Matcher matcher_email = regex_email.matcher(email);
        if (ten.length()>0 && sdt.length()>0 &&email.length()>0 ){
            if(checksdt(sdt)){
                count_sdt =1;
                if (matcher_email.find()) {
                   count_email=1;
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Email của bạn không hợp lệ");
                    count_email =0;
                }
            }else {
                CheckConnection.ShowToast_Short(getApplicationContext(),"Số điện thoại của bạn không đúng");
            }
            count_phu=1;
        }else {

            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn cần nhập đủ các thông tin !");
        }
        count_tong = count_email*count_phu*count_sdt;
        return count_tong;
    }

    private void EventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = edttenkhachhang.getText().toString().trim();
                final String sdt = edtsdt.getText().toString().trim();
                final String email = edtemail.getText().toString().trim();
                if(checkthongtin()==1){
                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constran.donhang_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang", madonhang);
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Thêm thông tin liên hệ thành công");
                            if (Integer.parseInt(madonhang)>0){
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Constran.chitietdonhang_URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")){
                                            ShoppingFragment.carts.clear();
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn đã thêm dữ liệu giỏ hàng thành công");
                                            Intent intent = new Intent(getApplicationContext(),Chucmungactivity.class);
                                            startActivity(intent);
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Mời bạn tiếp tục mua hàng");
                                        }else {
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Thêm dữ liệu giỏ hàng không thành công");
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
                                        for (int i=0; i<ShoppingFragment.carts.size();i++){
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
                                        HashMap<String,String> hashMap = new HashMap<String, String>();
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
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String,String>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);

                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Hãy nhập đủ thông tin");
                }
            }
        });
    }

    private void Anhxa() {
        edttenkhachhang = findViewById(R.id.edittexttenkh);
        edtemail = findViewById(R.id.edittextemailkh);
        edtsdt = findViewById(R.id.edittextsdtkh);
        btntrove = findViewById(R.id.buttontrove);
        btnxacnhan = findViewById(R.id.buttonxacnhan);

    }
}
