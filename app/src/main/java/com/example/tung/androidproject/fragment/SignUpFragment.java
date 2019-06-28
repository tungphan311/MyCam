package com.example.tung.androidproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tung.androidproject.R;
import com.example.tung.androidproject.activity.LoginActivity;
import com.example.tung.androidproject.model.User;
import com.example.tung.androidproject.util.Constran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment  {

    private EditText etHoTen, etDiaChi, etEmail, etSoDienThoai, etPassWord, etRePassWord;
    private RadioButton rbMale, rbFemale;
    private Spinner spnNamsinh;
    private TextView btnDangKy;
    private CheckBox cbDieuKhoan;

    boolean success = false;

    ArrayList<User> listUsers;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);

        initView(view);

        spinnerNamSinhSetData();

        getListUser();

        initEvent();

        return view;
    }

    private void spinnerNamSinhSetData() {
        ArrayList<Integer> listnamsinh = new ArrayList<>();
        for (Integer i= 2018; i>=1960; i--) {
            listnamsinh.add(i);
        }
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listnamsinh);
        spnNamsinh.setAdapter(arrayAdapter);
    }

    private void initView(View view) {
        etHoTen = view.findViewById(R.id.et_hoten);
        etDiaChi = view.findViewById(R.id.et_diachi);
        etEmail = view.findViewById(R.id.et_email);
        etSoDienThoai = view.findViewById(R.id.et_phone);
        etPassWord = view.findViewById(R.id.et_su_password);
        etRePassWord = view.findViewById(R.id.et_retype_password);
        rbMale = view.findViewById(R.id.rb_male);
        rbFemale = view.findViewById(R.id.rb_female);
        spnNamsinh = view.findViewById(R.id.spinner_namsinh);

        cbDieuKhoan = view.findViewById(R.id.cb_dieukhoan);
        btnDangKy = view.findViewById(R.id.btn_dangky);

        listUsers = new ArrayList<>();
    }

    private void initEvent() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyDangKy();
            }
        });
    }



    private void xuLyDangKy() {
        String hoten = etHoTen.getText().toString();
        String diachi = etDiaChi.getText().toString();
        String email = etEmail.getText().toString();
        String sodt = etSoDienThoai.getText().toString();
        String pass = etPassWord.getText().toString();
        String repass = etRePassWord.getText().toString();

        if (etHoTen.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Họ và tên không được bỏ trống!", Toast.LENGTH_SHORT).show();
            etHoTen.requestFocus();
        }
        else if (sodt.isEmpty()) {
            Toast.makeText(getActivity(), "Số điện thoại không được bỏ trống!", Toast.LENGTH_SHORT).show();
        }
        else if (pass.isEmpty()) {
            Toast.makeText(getActivity(), "Mật khẩu không được bỏ trống!", Toast.LENGTH_SHORT).show();
        }
        else if (repass.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập lại mật khẩu!", Toast.LENGTH_SHORT).show();
        }
        else if (!rbMale.isChecked() && !rbFemale.isChecked()) {
            Toast.makeText(getActivity(), "Vui lòng nhập chọn giới tính!", Toast.LENGTH_SHORT).show();
        }
        else {
            String gioitinh = rbMale.isChecked()? "nam" : "nữ";
            int namsinh = 2018 - spnNamsinh.getSelectedItemPosition();

            if (!repass.equals(pass)) {
                Toast.makeText(getActivity(), "Mật khẩu không khớp, vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
            }
            else if (!cbDieuKhoan.isChecked()) {
                Toast.makeText(getActivity(), "Bạn chưa chấp nhận điều khoản của eShopping", Toast.LENGTH_SHORT).show();
            }
            else if (checkPhone(sodt)){
                Toast.makeText(getActivity(), "Số điện thoại này đã được đăng ký", Toast.LENGTH_SHORT).show();
            }
            else {
                if (!checksdt(sodt)) {
                    Toast.makeText(getActivity(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                }
                else if (!checkthongtin(email)) {
                    Toast.makeText(getActivity(), "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                }
                else {
                    addNewUser(sodt, pass, hoten, gioitinh, namsinh, diachi, email);

                    getActivity().finish();
                    getActivity().overridePendingTransition(R.anim.leftin, R.anim.rightout);
                    Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        }

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

    private void addNewUser(final String Sodt, final String Pass, final String Hoten, final String Gioitinh, final int Namsinh, final String Diachi, final String Email) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constran.insertUser_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                success = true;
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("sodt", String.valueOf(Sodt));
                param.put("pass", String.valueOf(Pass));
                param.put("hoten", String.valueOf(Hoten));
                param.put("gioitinh", String.valueOf(Gioitinh));
                param.put("namsinh", String.valueOf(Namsinh));
                param.put("diachi", String.valueOf(Diachi));
                param.put("email", String.valueOf(Email));
                return param;
            }
        };
        requestQueue.add(stringRequest);
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

    private boolean checkPhone(String phone) {
        for (int i=0; i<listUsers.size(); i++) {
            if (listUsers.get(i).getSodienthoai().equals(phone))
                return true;
        }
        return false;
    }
}
