package com.example.tung.androidproject.fragment;


import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tung.androidproject.R;
import com.example.tung.androidproject.activity.LoginActivity;
import com.example.tung.androidproject.activity.MainScreen;
import com.example.tung.androidproject.model.User;
import com.example.tung.androidproject.util.Constran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private TextView btnDangNhap, tvQuenMatKhau;
    private EditText etPhone, etPassword;
    private CheckBox cbRemember;
    public SharedPreferences sp;


    ArrayList<User> listUsers;

    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInsanceState) {
        super.onCreate(savedInsanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        initView(view);

        getListUser();
        autologin();
        catchEvent();

        return view;
    }

    private void autologin() {
        String user="";
        String pwd="";
        SharedPreferences pre=getActivity().getSharedPreferences
                ("mydata",getContext().MODE_PRIVATE);
        //lấy giá trị checked ra, nếu không thấy thì giá trị mặc định là false
        boolean bchk=pre.getBoolean("checked", false);
        if(bchk)
        {
            //lấy user, pwd, nếu không thấy giá trị mặc định là rỗng
             user=pre.getString("Unm", "");
             pwd=pre.getString("Psw", "");
            etPhone.setText(user);
            etPassword.setText(pwd);
        }
        cbRemember.setChecked(bchk);
        for (int i=0; i<listUsers.size(); i++) {
            if (listUsers.get(i).getSodienthoai().equals(user) && listUsers.get(i).getMatkhau().equals(pwd)){
                MainScreen.isDangNhap = true;
                MainScreen.user = listUsers.get(i);
            }
        }

    }


    private void savedate(String phone, String passw) {
        sp = getActivity().getSharedPreferences("mydata", getContext().MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();

        boolean bchk = cbRemember.isChecked();
        if (!bchk) {
            //xóa mọi lưu trữ trước đó
            Ed.clear();
        } else {
            //lưu vào editor
            Ed.putString("Unm", phone);
            Ed.putString("Psw", passw);
            Ed.putBoolean("checked", bchk);
        }
        Ed.commit();
    }



    private void catchEvent() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString().trim();     // remove space
                String pass = etPassword.getText().toString().trim();

                if (phone.length() == 0) {
                    Toast.makeText(getActivity(), "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    etPhone.requestFocus();
                }
                else if (pass.length() == 0) {
                    Toast.makeText(getActivity(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                }
                else {
                    for (int i=0; i<listUsers.size(); i++) {
                        if (listUsers.get(i).getSodienthoai().equals(phone) && listUsers.get(i).getMatkhau().equals(pass)) {
                             savedate(listUsers.get(i).getSodienthoai(),listUsers.get(i).getMatkhau());
                             MainScreen.isDangNhap = true;
                             MainScreen.user = listUsers.get(i);
                             continue;
                        }
                    }

                    if (MainScreen.isDangNhap) {
                        Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.leftin, R.anim.rightout);
                        Intent intent = new Intent(getActivity().getApplicationContext(), MainScreen.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getActivity(), "Sai số điện thoại hoặc mật khẩu.\n" +
                                "Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getListUser() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constran.getUser_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int mauser = 0;
                    String sodt = "";
                    String pass = "";
                    String hoten = "";
                    String gioitinh = "";
                    int namsinh = 0;
                    String diachi = "";
                    String email = "";

                    for (int i= 0; i<response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            mauser = jsonObject.getInt("mauser");
                            sodt = jsonObject.getString("sodienthoai");
                            pass = jsonObject.getString("matkhau");
                            hoten = jsonObject.getString("hoten");
                            gioitinh = jsonObject.getString("gioitinh");
                            namsinh = jsonObject.getInt("namsinh");
                            diachi = jsonObject.getString("diachi");
                            email = jsonObject.getString("email");

                            listUsers.add(new User(mauser, sodt, pass, hoten, gioitinh, namsinh, diachi, email));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    private void initView(View view) {
        btnDangNhap = view.findViewById(R.id.btn_dangnhap);
        tvQuenMatKhau = view.findViewById(R.id.tv_quenmatkhau);
        etPhone = view.findViewById(R.id.et_phone);
        etPassword = view.findViewById(R.id.et_password);
        listUsers = new ArrayList<>();
        cbRemember = view.findViewById(R.id.cb_remember);
    }


}
