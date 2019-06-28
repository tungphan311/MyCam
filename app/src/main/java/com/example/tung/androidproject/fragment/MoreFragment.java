package com.example.tung.androidproject.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tung.androidproject.R;
import com.example.tung.androidproject.activity.HelpActivity;
import com.example.tung.androidproject.activity.LoginActivity;
import com.example.tung.androidproject.activity.MainScreen;
import com.example.tung.androidproject.activity.MycartActivity;
import com.example.tung.androidproject.activity.SettingActivity;
import com.example.tung.androidproject.adapter.MoreAdapter;
import com.example.tung.androidproject.util.Constran;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {

    private ListView lvChucnang, lvCaidat;
    private TextView tvDangnhapDK, tvTenuser;
    private ImageView imgAvata;
    private FrameLayout toolbar;
    private LinearLayout llUser;

    private MoreAdapter adapter;

    public MoreFragment() {
        // Required empty public constructor
    }

    public static MoreFragment newInstance(String param1, String param2) {
        MoreFragment fragment = new MoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        InitView(view);

        InitData();

        InitEvent();

        return view;
    }

    private void InitEvent() {
        tvDangnhapDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        lvCaidat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // chính sách
                        showChinhsach();
                        break;

                    case 1:
                        // Trợ giúp
                        showTrogiup();
                        break;

                    case 2:
                        // giới thiệu
                        showGioiThieu();
                        break;
                }
            }
        });

        lvChucnang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getActivity().getApplicationContext(), MycartActivity.class);
                        startActivity(intent);
                        break;

                    case 1:
                        if (MainScreen.isDangNhap) {
                            Intent intent2 = new Intent(getActivity().getApplicationContext(), SettingActivity.class);
                            startActivity(intent2);
                            getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        }
                        else {
                            Toast.makeText(getActivity(), "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });
    }

    private void showChinhsach() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Chính sách của eShopping");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_chinhsach);
        TextView btnOK = dialog.findViewById(R.id.btn_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showTrogiup() {
        Intent intent = new Intent(getActivity().getApplicationContext(), HelpActivity.class);
        startActivity(intent);
    }

    private void showGioiThieu() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Giới thiệu");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_about);
        TextView btnQuit = dialog.findViewById(R.id.btn_done);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void InitData() {
        if (MainScreen.isDangNhap) {
            tvDangnhapDK.setVisibility(View.GONE);
            tvTenuser.setVisibility(View.VISIBLE);
            tvTenuser.setText(MainScreen.user.getHoten());

            adapter = new MoreAdapter(getActivity(), Constran.getListChucnang());
            lvChucnang.setAdapter(adapter);
        }
        else {
            tvDangnhapDK.setVisibility(View.VISIBLE);
            tvTenuser.setVisibility(View.GONE);
        }

        adapter = new MoreAdapter(getActivity(), Constran.getListCaidat());
        lvCaidat.setAdapter(adapter);
    }

    private void InitView(View view) {
        lvCaidat = view.findViewById(R.id.lv_caidat);
        lvChucnang = view.findViewById(R.id.lv_chucnang);
        tvDangnhapDK = view.findViewById(R.id.btn_dangnhap_dangky);
        tvTenuser = view.findViewById(R.id.tv_tenuser);
        imgAvata = view.findViewById(R.id.iv_avatar);
        toolbar = view.findViewById(R.id.fl_toolbar);
        llUser = view.findViewById(R.id.ll_user);
    }
}
