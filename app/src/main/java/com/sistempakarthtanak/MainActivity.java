package com.sistempakarthtanak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setElevation(0);

        setContentView(R.layout.activity_main);

        CardView btn_mulai_diagnosa = findViewById(R.id.btn_mulai_diagnosa);
        CardView btn_daftar_penyakit = findViewById(R.id.btn_daftar_penyakit);
        CardView btn_tentang_aplikasi = findViewById(R.id.btn_tentang_aplikasi);

        btn_mulai_diagnosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DiagnosaActivity.class);
                startActivity(intent);
            }
        });

        btn_daftar_penyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DaftarPenyakitActivity.class);
                startActivity(intent);
            }
        });

        btn_tentang_aplikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TentangActivity.class);
                startActivity(intent);
            }
        });
    }
}
