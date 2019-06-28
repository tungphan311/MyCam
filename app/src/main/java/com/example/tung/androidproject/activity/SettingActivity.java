package com.example.tung.androidproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.tung.androidproject.util.CheckConnection;
import com.example.tung.androidproject.util.Constran;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingActivity extends AppCompatActivity {
    LinearLayout btnDoimatkhau, llDoimk, llDoithongtin, btnDoiInfo, llDangxuat;

    boolean isDoimk = false;
    boolean isDoiinfo = false;

    ImageView imgDoimk, imgDoiinfo, btnThoat;
    TextView btnCapnhat, btnCapnhatDiachi;
    EditText edtMkmoi, edtXacnhan;
    EditText edtHoten, edtNamsinh, edtDiachi, edtEmail;
    Spinner spnGioitinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();

        initData();

        initEvent();
    }

    private void initData() {
        String[] sex = new String[] {"Nam", "Nữ"};
        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, sex);
        spnGioitinh.setAdapter(adapter);

        edtHoten.setText(MainScreen.user.getHoten());
        if (MainScreen.user.getGioitinh().equals("nam")) {
            spnGioitinh.setSelection(0);
        }
        else if (MainScreen.user.getGioitinh().equals("nữ")) {
            spnGioitinh.setSelection(1);
        }
        edtNamsinh.setText(String.valueOf(MainScreen.user.getNamsinh()));
        edtDiachi.setText(MainScreen.user.getDiachi());
        edtEmail.setText(MainScreen.user.getEmail());
    }

    private void initEvent() {
        btnDoimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDoimk) {
                    isDoimk = true;
                    llDoimk.setVisibility(View.VISIBLE);
                    imgDoimk.setImageResource(R.drawable.icons8_expand_arrow_50);
                }
                else {
                    isDoimk = false;
                    llDoimk.setVisibility(View.GONE);
                    imgDoimk.setImageResource(R.drawable.icons8_forward);
                }
            }
        });

        btnDoiInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDoiinfo) {
                    isDoiinfo = true;
                    llDoithongtin.setVisibility(View.VISIBLE);
                    imgDoiinfo.setImageResource(R.drawable.icons8_expand_arrow_50);
                }
                else {
                    isDoiinfo = false;
                    llDoithongtin.setVisibility(View.GONE);
                    imgDoiinfo.setImageResource(R.drawable.icons8_forward);
                }
            }
        });

        btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
                edtMkmoi.setText("");
                edtXacnhan.setText("");
                llDoimk.setVisibility(View.GONE);
                isDoimk = false;
                imgDoimk.setImageResource(R.drawable.icons8_forward);
            }
        });

        llDangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainScreen.isDangNhap = false;
                MainScreen.user = null;

                Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                startActivity(intent);
            }
        });

        btnCapnhatDiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();
                isDoiinfo = false;
                llDoithongtin.setVisibility(View.GONE);
                imgDoiinfo.setImageResource(R.drawable.icons8_forward);
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.leftin,R.anim.rightout);
            }
        });
    }

    private void updateInfo() {
        String hoten = edtHoten.getText().toString();
        String gioitinh = spnGioitinh.getSelectedItem().toString();
        String namsinh = edtNamsinh.getText().toString();
        String diachi = edtDiachi.getText().toString();
        String email = edtEmail.getText().toString();

        if (hoten.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập họ tên đầy đủ", Toast.LENGTH_SHORT).show();
        }
        else if (namsinh.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập năm sinh", Toast.LENGTH_SHORT).show();
        }
        else if (diachi.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
        }
        else if (email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
        }
        else {
            if (!checkthongtin(email)) {
                Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            }
            else {
                updateToDatabase(hoten, gioitinh, namsinh, diachi, email, MainScreen.user.getMauser());
            }
        }
    }

    private void updateToDatabase(final String hoten, final String gioitinh, final String namsinh, final String diachi, final String email, final int id) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Constran.updateinfo_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SettingActivity.this, "Đổi thông tin thành công", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("mauser",String.valueOf(id));
                param.put("hoten",String.valueOf(hoten));
                param.put("gioitinh",String.valueOf(gioitinh));
                param.put("namsinh",String.valueOf(namsinh));
                param.put("diachi",String.valueOf(diachi));
                param.put("email",String.valueOf(email));
                return param;
            }
        };
        requestQueue.add(request);
    }

    public boolean checkthongtin(String email){
        String emailPattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern regex_email = Pattern.compile(emailPattern);
        Matcher matcher_email = regex_email.matcher(email);
        if (email.length()>0 ) {
            return matcher_email.find();
        }
        return false;
    }

    private void updatePassword() {
        String pass = edtMkmoi.getText().toString();
        String repass = edtXacnhan.getText().toString();

        if (pass.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
        }
        else if (repass.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
        }
        else {
            if (!pass.equals(repass)) {
                Toast.makeText(this, "Mật khẩu không khớp, vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }
            else {
                // update mật khẩu
                updateDatabase(MainScreen.user.getMauser(), pass);
            }
        }
    }

    private void updateDatabase(final int id, final String pass) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Constran.updatepassword_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SettingActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("mauser",String.valueOf(id));
                param.put("matkhau",String.valueOf(pass));
                return param;
            }
        };
        requestQueue.add(request);
    }


    private void initView() {
        btnDoimatkhau = findViewById(R.id.ll_doimatkhau);
        llDoimk = findViewById(R.id.ll_doimk);
        llDoithongtin = findViewById(R.id.ll_doithongtin);
        btnDoiInfo = findViewById(R.id.ll_changeinfo);
        imgDoiinfo = findViewById(R.id.img_doithongtin);
        imgDoimk = findViewById(R.id.img_doimatkhau);
        edtMkmoi = findViewById(R.id.edt_mk_moi);
        edtXacnhan = findViewById(R.id.edt_xnmk_moi);
        btnCapnhat = findViewById(R.id.btn_capnhap_mk);
        llDangxuat = findViewById(R.id.ll_dangxuat);
        btnCapnhatDiachi = findViewById(R.id.btn_capnhap_diachi);
        edtHoten = findViewById(R.id.edt_hoten_moi);
        edtNamsinh = findViewById(R.id.edt_namsinh_moi);
        edtDiachi = findViewById(R.id.edt_diachi_moi);
        edtEmail = findViewById(R.id.edt_email_moi);
        spnGioitinh = findViewById(R.id.spn_gioitinh);
        btnThoat = findViewById(R.id.btn_thoat);

        llDoimk.setVisibility(View.GONE);
        llDoithongtin.setVisibility(View.GONE);
    }
}
