package com.sistempakarthtanak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class DaftarPenyakitActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_penyakit);
        setTitle("Daftar Penyakit");

//        load database
        DatabaseHelper mDBHelper = new DatabaseHelper(this);
        if (mDBHelper.openDatabase())
            db = mDBHelper.getReadableDatabase();

        ListView lv_daftar_penyakit = findViewById(R.id.list);

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
//        ambil data penyakit dari database
        String query_penyakit = "SELECT kode_penyakit,nama_penyakit FROM penyakit ORDER BY kode_penyakit";
        Cursor cursor_penyakit = db.rawQuery(query_penyakit, null);
        while (cursor_penyakit.moveToNext()) {
//            masukkan ke list
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("kode_penyakit", cursor_penyakit.getString(0));
            map.put("nama_penyakit", cursor_penyakit.getString(1));
            list.add(map);
        }
        cursor_penyakit.close();

//        adapter untuk listview
        SimpleAdapter adapter = new SimpleAdapter(
                DaftarPenyakitActivity.this,
                list,
                R.layout.list_penyakit,
                new String[]{"kode_penyakit", "nama_penyakit"},
                new int[]{R.id.kode_penyakit, R.id.nama_penyakit});
        lv_daftar_penyakit.setAdapter(adapter);

//        jika di klik salah satu penyakit tampilkan activity detail penyakit
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                LinearLayout linearLayout = (LinearLayout) container;
                TextView tv_kode_penyakit = (TextView) linearLayout.getChildAt(0);
                Intent intent = new Intent(DaftarPenyakitActivity.this, DetailPenyakitActivity.class);
                intent.putExtra("KODE_PENYAKIT", tv_kode_penyakit.getText().toString());
                startActivity(intent);
            }
        };

        lv_daftar_penyakit.setOnItemClickListener(itemClickListener);
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
