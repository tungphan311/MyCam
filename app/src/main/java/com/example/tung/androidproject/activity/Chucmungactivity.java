package com.example.tung.androidproject.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tung.androidproject.R;

public class Chucmungactivity extends AppCompatActivity {
    Button btnTrangchinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dathang);
        Anhxa();

        btnTrangchinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainScreen.class);
                startActivity(intent);
            }
        });
    }

    private void Anhxa() {
        btnTrangchinh = findViewById(R.id.buttontrangchu);

    }
}
