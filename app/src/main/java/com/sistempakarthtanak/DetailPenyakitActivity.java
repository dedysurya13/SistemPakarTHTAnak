package com.sistempakarthtanak;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailPenyakitActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penyakit);
        setTitle("Detail Penyakit");

//        load database
        DatabaseHelper mDBHelper = new DatabaseHelper(this);
        if (mDBHelper.openDatabase())
            db = mDBHelper.getReadableDatabase();

//        ambil kode penyakit yang dipilih dari menu sebelumnya, menu daftar penyakit
        String kode_penyakit = getIntent().getStringExtra("KODE_PENYAKIT");

//        ambil data penyakit dari database
        String query_penyakit = "SELECT nama_penyakit,deskripsi,penanganan FROM penyakit WHERE kode_penyakit = '" + kode_penyakit + "'";
        Cursor cursor_penyakit = db.rawQuery(query_penyakit, null);
        cursor_penyakit.moveToFirst();

//        tampilkan data penyakit kedalam textview
        TextView tv_nama_penyakit = findViewById(R.id.tv_nama_penyakit);
        TextView tv_deskripsi = findViewById(R.id.tv_deskripsi);
        TextView tv_penanganan = findViewById(R.id.tv_penanganan);

        tv_nama_penyakit.setText(cursor_penyakit.getString(0));
        tv_deskripsi.setText(cursor_penyakit.getString(1));
        tv_penanganan.setText(cursor_penyakit.getString(2));

        cursor_penyakit.close();
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
