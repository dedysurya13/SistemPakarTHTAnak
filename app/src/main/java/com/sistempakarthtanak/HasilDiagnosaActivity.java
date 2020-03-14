package com.sistempakarthtanak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HasilDiagnosaActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_diagnosa);
        setTitle("Hasil Diagnosa");

        //        load database
        DatabaseHelper mDBHelper = new DatabaseHelper(this);
        if (mDBHelper.openDatabase())
            db = mDBHelper.getReadableDatabase();

        String str_hasil = getIntent().getStringExtra("HASIL"); //ambil hasil diagnosa dari activity diagnosa
        String[] gejala_terpilih = new String[0];
        if (str_hasil != null) {
//            pisahkan berdasarkan tanda # dan masukkan ke array
            gejala_terpilih = str_hasil.split("#");
        }

//        perhitungan metode CF
        double cf_gabungan;
        double cf;
        HashMap<String, Double> mapHasil = new HashMap<>(); //untuk menyimpan hasil penilaian CF

//        ambil data penyakit
        String query_penyakit = "SELECT kode_penyakit FROM penyakit order by kode_penyakit";
        Cursor cursor_penyakit = db.rawQuery(query_penyakit, null);

//        lakukan perulangan untuk setiap data penyakit
        while (cursor_penyakit.moveToNext()) {
            cf_gabungan = (double) 0; // set cf gabungan/kombinasi menjadi 0
            int i = 0; // index untuk jumlah gejala yang dipilih
//            ambil data dari tabel rule berdasarkan kode penyakit
            String query_rule = "SELECT nilai_cf,kode_gejala FROM rule where kode_penyakit = '" + cursor_penyakit.getString(0) + "'";
            Cursor cursor_rule = db.rawQuery(query_rule, null);
//            perulangan sebanyak gejala sesuai dengan rule penyakitnya
            while (cursor_rule.moveToNext()) {
                cf = cursor_rule.getDouble(0);
                for (String s_gejala_terpilih : gejala_terpilih) { //perulangan gejala yang dipilih pengguna
//                    ambil kode gejala berdasarkan nama gejala yang dipilih pengguna
                    String query_gejala = "SELECT kode_gejala FROM gejala where nama_gejala = '" + s_gejala_terpilih + "'";
                    Cursor cursor_gejala = db.rawQuery(query_gejala, null);
                    cursor_gejala.moveToFirst();
//                    cek apakah gejala dari penyakit dipilih/diceklis oleh pengguna
                    if (cursor_rule.getString(1).equals(cursor_gejala.getString(0))) {
//                        jika diceklis, hitung CF
                        if (i > 1) { // perhitungan 3 gejala atau lebih
                            cf_gabungan = cf + (cf_gabungan * (1 - cf));
                        } else if (i == 1) { // perhitungan hanya 2 gejala
                            cf_gabungan = cf_gabungan + (cf * (1 - cf_gabungan));
                        } else { // gejala pertama
                            cf_gabungan = cf;
                        }
                        i++; // index jumlah gejala terpilih +1
                    }
                    cursor_gejala.close();
                }
            }
            cursor_rule.close();
            //simpan hasil perhitungan CF ke array untuk nanti diurutkan
            mapHasil.put(cursor_penyakit.getString(0), cf_gabungan * 100); //nilai CF dikali 100 agar hasilnya berupa persentase
            // karena rentang nilai metode CF antara 0 - 1, jadi cukup dikalikan 100 biar hasilnya berupa persentase
        }
        cursor_penyakit.close();

//        untuk menampilkan daftar gejala yang dipilih pengguna
        StringBuffer output_gejala_terpilih = new StringBuffer();
        int no = 1;
        for (String s_gejala_terpilih : gejala_terpilih) {
            output_gejala_terpilih.append(no++)
                    .append(". ")
                    .append(s_gejala_terpilih)
                    .append("\n");
        }
//        tampilkan variabel kedalam textview
        TextView tv_list_gejala_dipilih = findViewById(R.id.tv_list_gejala_dipilih);
        tv_list_gejala_dipilih.setText(output_gejala_terpilih);

//        pengurutan hasil penilaian CF berdasarkan nilai terbesar
        Map<String, Double> sortedHasil = sortByValue(mapHasil);

        // ambil kode penyakit dengan nilai terbesar
        Map.Entry<String, Double> entry = sortedHasil.entrySet().iterator().next();
        String kode_penyakit = entry.getKey();
        double hasil_cf = entry.getValue();
        int persentase = (int) hasil_cf;

//        ambil data penyakit dari database
        String query_penyakit_hasil = "SELECT nama_penyakit FROM penyakit where kode_penyakit='" + kode_penyakit + "'";
        Cursor cursor_hasil = db.rawQuery(query_penyakit_hasil, null);
        cursor_hasil.moveToFirst();

        TextView tv_nama_penyakit = findViewById(R.id.tv_nama_penyakit);
        tv_nama_penyakit.setText(cursor_hasil.getString(0));
        TextView tv_persentase = findViewById(R.id.tv_persentase);
        tv_persentase.setText(persentase+"%");

        cursor_hasil.close();

//        button ulangi diagnosa
        Button btn_diagnosa_ulang = findViewById(R.id.btn_diagnosa_ulang);
        btn_diagnosa_ulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button btn_lihat_daftar_penyakit = findViewById(R.id.btn_lihat_daftar_penyakit);
        btn_lihat_daftar_penyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HasilDiagnosaActivity.this, DaftarPenyakitActivity.class);
                startActivity(intent);
            }
        });
    }

    // fungsi untuk mengurutkan nilai dari yang terbesar
    public static HashMap<String, Double> sortByValue(HashMap<String, Double> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double>> list =
                new LinkedList<Map.Entry<String, Double>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    //    biar tombol back di toolbar dan tombol back di device tidak me restart menu sebelumnya/menu activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
