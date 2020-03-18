package com.sistempakarthtanak;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//class untuk koneksi ke database
public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "sp_tht.db"; //nama file database yang ada di folder assets
    private static String DB_PATH = ""; // path db set kosong, nanti diisi di bawah
    private static final int DB_VERSION = 1; //versi database, setiap kali melakukan perubahan database, ubah nilainya dengan menambahkan +1

    private SQLiteDatabase mDatabase;
    private final Context mContext;
    private boolean mNeedUpdate = false;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/"; //path database
        this.mContext = context;

        copyDatabase();

        this.getReadableDatabase();
    }

//    fungsi untuk update database, jika diperlukan
    public void updateDatabase() throws IOException {
        if (mNeedUpdate){
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDatabase();

            mNeedUpdate = false;
        }
    }

//    fungsi untuk membuka koneksi ke database
    public boolean openDatabase() throws SQLException {
        mDatabase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDatabase != null;
    }

//    fungsi untuk close koneksi database
    @Override
    public synchronized void close() {
        if (mDatabase != null)
            mDatabase.close();
        super.close();
    }

//    fungsi untuk cek apakah file database sudah ada atau tidak
    private boolean checkDatabase(){
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

//    fungsi untuk copy database yang sudah dibuat sebelumnya di folder assets ke dalam aplikasi
    private void copyDatabase() {
        if (!checkDatabase()){
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException e) {
                throw new Error("ErrorCopyingDatabase");
            }
        }
    }

//    fungsi untuk copy database dari folder asset
    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

//    jika versi database lebih baru maka perlu di update
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }
}
