package com.example.tung.androidproject.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.example.tung.androidproject.activity.ProducDetailActivity;
import com.example.tung.androidproject.adapter.LoaisanphamAdapter;
import com.example.tung.androidproject.adapter.LoaispAdapter;
import com.example.tung.androidproject.adapter.SanphamAdapter;
import com.example.tung.androidproject.adapter.SpAdapter;
import com.example.tung.androidproject.model.Loaisanpham;
import com.example.tung.androidproject.model.Sanpham;
import com.example.tung.androidproject.util.CheckConnection;
import com.example.tung.androidproject.util.Constran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Sanpham> listSanpham;
    SpAdapter spAdapter;
    EditText editText;
    ImageView imageView;
    String key ="";
    Toolbar toolbar;
    ListView listView;
    ArrayList<Loaisanpham> listLoaisp;
    LoaispAdapter loaispAdapter;
    int maloaisp = 0;
    String tenloaisp ="";
    String hinhanhloaisp ="";

    int loaisp;
    int hangsx;
    int sort;

    Spinner spnLoaisp, spnHangsx;
    TextView tvNoResult;

    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment newInstance(String parma1, String param2) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle saveInsanceState) {
        super.onCreate(saveInsanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_me, container, false);

        if (CheckConnection.haveNetworkConnection(getActivity().getApplicationContext())) {
            Anhxa(view);
            check();

            spinnerLoaispGetData();
        }
        else {
            CheckConnection.ShowToast_Short(getActivity().getApplicationContext(), Constran.connectionErrorMessage);
            getActivity().finish();
        }

        return view;
    }

    private void spinnerLoaispGetData() {
        String[] listLoaisp = new String[] {"--Loại sản phẩm--", "Điện thoại", "Laptop", "Tablet", "Phụ kiện"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listLoaisp);
        spnLoaisp.setAdapter(arrayAdapter);
    }

    private void spinnerHangsxNull() {
        String[] listHangsx = new String[] {"--Nhà sản xuất--"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listHangsx);
        spnHangsx.setAdapter(arrayAdapter);
    }

    private void spinnerHangsxGetData(int Loaisp) {
        if (Loaisp == 1) {
            String[] listHangsx = new String[] {"--Nhà sản xuất--", "Iphone", "Samsung", "Xiaomi", "Zenphone", "Oppo", "Huawei", "HTC", "Vivo"};
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listHangsx);
            spnHangsx.setAdapter(arrayAdapter);
        }
        else if (Loaisp == 2) {
            String[] listHangsx = new String[] {"--Nhà sản xuất--", "Asus", "Dell", "Macbook", "HP", "Acer", "MSI"};
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listHangsx);
            spnHangsx.setAdapter(arrayAdapter);
        }
        else if (Loaisp == 3) {
            String[] listHangsx = new String[] {"--Nhà sản xuất--", "Ipad", "Samsung", "Huawei", "Lenovo", "Masstel", "Sony", "Nexus"};
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listHangsx);
            spnHangsx.setAdapter(arrayAdapter);
        }
        else if (Loaisp == 4) {
            String[] listHangsx = new String[] {"--Nhà sản xuất--", "Bàn phím", "Chuột", "Tai nghe", "USB/ Ổ cứng", "Cáp sạc"};
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listHangsx);
            spnHangsx.setAdapter(arrayAdapter);
        }
    }

    private void check() {

        spnLoaisp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        loaisp = 0;
                        hangsx = 0;
                        if (!editText.isSelected())
                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                        spinnerHangsxNull();
                        break;

                    case 1:
                        loaisp = position;
                        hangsx = 0;
                        if (!editText.isSelected())
                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                        spinnerHangsxGetData(position);
                        spnHangsx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                                switch (index) {
                                    case 0:
                                        hangsx = index;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 1:
                                        hangsx = index;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 2:
                                        hangsx = index;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 3:
                                        hangsx = index;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 4:
                                        hangsx = index;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 5:
                                        hangsx = index;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 6:
                                        hangsx = index;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 7:
                                        hangsx = index;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 8:
                                        hangsx = index;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;

                    case 2:
                        loaisp = position;
                        hangsx = 0;
                        if (!editText.isSelected())
                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                        spinnerHangsxGetData(position);
                        spnHangsx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                                switch (index) {
                                    case 0:
                                        hangsx = 0;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 1:
                                        hangsx = index + 8;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 2:
                                        hangsx = index + 8;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 3:
                                        hangsx = index + 8;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 4:
                                        hangsx = index + 8;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 5:
                                        hangsx = index + 8;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 6:
                                        hangsx = index + 8;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;

                    case 3:
                        loaisp = position;
                        hangsx = 0;
                        if (!editText.isSelected())
                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));

                        spinnerHangsxGetData(position);
                        spnHangsx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                                switch (index) {
                                    case 0:
                                        hangsx = 0;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 1:
                                        hangsx = index + 16;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 2:
                                        hangsx = index + 16;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 3:
                                        hangsx = index + 16;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 4:
                                        hangsx = index + 16;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 5:
                                        hangsx = index + 16;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 6:
                                        hangsx = index + 16;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 7:
                                        hangsx = index + 16;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;

                    case 4:
                        loaisp = position;
                        hangsx = 0;
                        if (!editText.isSelected())
                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));

                        spinnerHangsxGetData(position);
                        spnHangsx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                                switch (index) {
                                    case 0:
                                        hangsx = 0;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 1:
                                        hangsx = index + 23;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 2:
                                        hangsx = index + 23;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 3:
                                        hangsx = index + 23;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 4:
                                        hangsx = index + 23;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;

                                    case 5:
                                        hangsx = index + 23;
                                        if (!editText.isSelected())
                                            search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
                                        break;
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search(editText.getText().toString(), String.valueOf(loaisp), String.valueOf(hangsx));
            }
        });

    }

    private void Anhxa(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        recyclerView = view.findViewById(R.id.rvtimkiemsp);
        listSanpham = new ArrayList<>();
        spAdapter = new SpAdapter(getActivity().getApplicationContext(), listSanpham);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
        recyclerView.setAdapter(spAdapter);
        editText = view.findViewById(R.id.edt_timkiem);
        imageView = view.findViewById(R.id.imv_timkiem);
        loaispAdapter = new LoaispAdapter(listLoaisp, getActivity().getApplicationContext());
        listLoaisp = new ArrayList<>();

        spnLoaisp = view.findViewById(R.id.spinner_loaisp);
        spnHangsx = view.findViewById(R.id.spinner_hangsx);
        tvNoResult = view.findViewById(R.id.tv_noresult);
    }

    private void search(String Key, final String Maloaisp, final String Mahangsx) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String duongdan = Constran.search_URL + String.valueOf(Key);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listSanpham.clear();
                int masanpham=0;
                String tensanpham="";
                int giasanpham=0;
                String hinhanhsanpham="";
                String motasanpham="";
                int maloaisanpham=0;
                int mahangsanxuat = 0;

                if (response !=null && response.length() != 2){
                    recyclerView.setVisibility(View.VISIBLE);
                    tvNoResult.setVisibility(View.INVISIBLE);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<response.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            masanpham = jsonObject.getInt("masanpham");
                            tensanpham = jsonObject.getString("tensanpham");
                            giasanpham = jsonObject.getInt("giasanpham");
                            hinhanhsanpham = jsonObject.getString("hinhanhsanpham");
                            motasanpham = jsonObject.getString("motasanpham");
                            maloaisanpham = jsonObject.getInt("maloaisanpham");
                            mahangsanxuat = jsonObject.getInt("mahangsanxuat");

                            listSanpham.add(new Sanpham(masanpham,tensanpham,giasanpham,hinhanhsanpham,motasanpham,maloaisanpham, mahangsanxuat));
                            spAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    tvNoResult.setVisibility(View.VISIBLE);
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
                param.put("key",String.valueOf(""));
                param.put("maloaisp", String.valueOf(Maloaisp));
                param.put("mahangsx", String.valueOf(Mahangsx));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
