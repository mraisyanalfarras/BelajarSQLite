package com.example.belajarsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorit.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FAVORIT = "tb_favorit";
    private static final String COLUMN_NAMA = "nama";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_FAVORIT + " (" +
                COLUMN_NAMA + " TEXT PRIMARY KEY)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_FAVORIT;
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean simpan(String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, nama);
        long result = db.insert(TABLE_FAVORIT, null, values);
        return result != -1;
    }

    public List<String> tampilSemua() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_FAVORIT;
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA)));
        }
        cursor.close();
        return list;
    }

    //PR Update dan Hapus

    public boolean update(String oldName, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, newName);
        int rowsAffected = db.update(TABLE_FAVORIT, values, COLUMN_NAMA + " = ?", new String[]{oldName});
        return rowsAffected > 0;
    }

    public boolean delete(String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_FAVORIT, COLUMN_NAMA + " = ?", new String[]{nama});
        return rowsAffected > 0;
    }
}
