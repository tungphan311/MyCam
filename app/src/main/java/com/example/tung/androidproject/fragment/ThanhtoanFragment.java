package com.example.tung.androidproject.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.tung.androidproject.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThanhtoanFragment extends Fragment {
    Button btnTieptuc, btnQuaylai;
    Fragment fragment;
    TextView tvDukien;
    RadioButton rbNapas, rbVisa, rbCod, rbHinhthuc;

    int today, month, year;

    public ThanhtoanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thanhtoan, container, false);

        initView(view);

        initEvent();

        calculateDay();

        initData();

        return view;
    }

    private void initData() {
        String text = tvDukien.getText() +  " " + String.valueOf(today) + "/" + String.valueOf(month) + "/" + String.valueOf(year) + ")";
        tvDukien.setText(text);

        rbNapas.setEnabled(false);
        rbVisa.setEnabled(false);
        rbCod.setChecked(true);
        rbHinhthuc.setChecked(true);
    }

    private void initEvent() {
        btnTieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.framelayout, fragment);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });
    }

    private void initView(View view) {
        btnQuaylai = view.findViewById(R.id.btn_quaylai);
        btnTieptuc = view.findViewById(R.id.btn_tieptuc);
        tvDukien = view.findViewById(R.id.tv_dukien);
        rbNapas = view.findViewById(R.id.rd_napas);
        rbVisa = view.findViewById(R.id.rd_visa);
        rbCod = view.findViewById(R.id.rd_cod);
        rbHinhthuc = view.findViewById(R.id.rd_hinhthuc);

        fragment = new XacnhanFragment();
    }

    private void calculateDay() {
        Calendar cal = Calendar.getInstance();
        today = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH) + 1;
        year = cal.get(Calendar.YEAR);

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
                if (today <= 24)
                    today += 7;
                else {
                    today = today + 7 - 31;
                    month += 1;
                }
                break;

            case 2:
                if (today <= 21)
                    today += 7;
                else {
                    today = today + 7 - 28;
                    month += 1;
                }
                break;

            case 4:
            case 6:
            case 9:
            case 11:
                if (today <= 23)
                    today += 7;
                else {
                    today = today + 7 - 30;
                    month += 1;
                }
                break;

            case 12:
                if (today <= 24)
                    today += 7;
                else {
                    today = today + 7 - 31;
                    month = 1;
                    year += 1;
                }
                break;
        }
    }

}
