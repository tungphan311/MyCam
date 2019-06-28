package com.example.tung.androidproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tung.androidproject.fragment.MeFragment;
import com.example.tung.androidproject.fragment.MoreFragment;
import com.example.tung.androidproject.fragment.NotificationFragment;
import com.example.tung.androidproject.R;
import com.example.tung.androidproject.fragment.ShoppingFragment;
import com.example.tung.androidproject.model.Sanpham;
import com.example.tung.androidproject.model.User;
import com.example.tung.androidproject.util.Constran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.http.POST;

public class MainScreen extends AppCompatActivity {
    public static boolean isDangNhap;
    public static User user;

    BottomNavigationView navigation;
    Fragment fragment;

    public boolean close = false;
    public SharedPreferences pre;
    boolean isRead = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadState();
        // load app default
        loadFragment(new ShoppingFragment());
    }

    private void loadState() {
        SharedPreferences preferences = getSharedPreferences("my_state", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        boolean check = preferences.getBoolean("dangnhap", false);

        if (check) {
            int mauser = preferences.getInt("mauser", 0);
            isDangNhap = true;

            findUser(mauser);
        }

        editor.clear();
        editor.commit();
    }

    private void findUser(final int id) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constran.finduserbyid_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int iduser = 0;
                String sodt = "";
                String pass = "";
                String hoten = "";
                String gioitinh = "";
                int namsinh = 0;
                String diachi = "";
                String email = "";

                if (response !=null && response.length() != 2){
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i=0;i<response.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            iduser = jsonObject.getInt("mauser");
                            sodt = jsonObject.getString("sodienthoai");
                            pass = jsonObject.getString("matkhau");
                            hoten = jsonObject.getString("hoten");
                            gioitinh = jsonObject.getString("gioitinh");
                            namsinh = jsonObject.getInt("namsinh");
                            diachi = jsonObject.getString("diachi");
                            email = jsonObject.getString("email");

                            user = new User(iduser, sodt, pass, hoten, gioitinh, namsinh, diachi, email);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    user = null;
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("mauser",String.valueOf(id));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        saveState();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_shopping:
                    fragment = new ShoppingFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_user:
                    fragment = new MeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications:
                    Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_more:
                    fragment = new MoreFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment)
    {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getSelectedMenu(navigation) == R.id.navigation_shopping) {
                if (close) {
                    finish();
                }
                else {
                    Toast.makeText(this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();
                    loadFragment(new ShoppingFragment());
                    close = true;
                }
            }
            else {
                close = false;
            }
        }


        return super.onKeyDown(keyCode, event);
    }

    private int getSelectedMenu(BottomNavigationView btmNavigation) {
        Menu menu = btmNavigation.getMenu();

        for (int i=0; i<btmNavigation.getMenu().size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem.isChecked()) {
                return menuItem.getItemId();
            }
        }
        return 0;
    }

    public void saveState() {
        pre = getSharedPreferences("my_state", MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();

        editor.clear();

        if (isDangNhap) {
            editor.putBoolean("dangnhap", isDangNhap);
            editor.putInt("mauser", user.getMauser());
        }

        editor.commit();
    }
}
