package com.lat1.latihan10_nada.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lat1.latihan10_nada.models.Barang;

import java.util.ArrayList;

public class DBSource {
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    private String[] allColumns = {DBHelper.COLUMN_ID, DBHelper.COLUMN_NAME,
            DBHelper.COLUMN_MERK, DBHelper.COLUMN_HARGA};

    public DBSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Barang createBarang(String nama, String merk, String harga) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, nama);
        values.put(DBHelper.COLUMN_MERK, merk);
        values.put(DBHelper.COLUMN_HARGA, harga);


        long insertId = database.insert(DBHelper.TABLE_NAME, null, values);

        Cursor cursor = database.query(DBHelper.TABLE_NAME, allColumns,
                DBHelper.COLUMN_ID + " = " + insertId,
                null, null, null, null);

        cursor.moveToFirst();

        Barang newBarang = cursorToBarang(cursor);
        cursor.close();

        return newBarang;

    }

    private Barang cursorToBarang(Cursor cursor) {
        Barang barang = new Barang();

        barang.setId(cursor.getLong(0));
        barang.setNama(cursor.getString(1));
        barang.setMerk(cursor.getString(2));
        barang.setHarga(cursor.getString(3));

        return barang;

    }

    public ArrayList<Barang> getAllBarang() {
        ArrayList<Barang> daftarBarang = new ArrayList<Barang>();

        Cursor cursor = database.query(DBHelper.TABLE_NAME, allColumns,
                null, null, null, null, null);


        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Barang barang = cursorToBarang(cursor);
            daftarBarang.add(barang);
            cursor.moveToNext();
        }
        cursor.close();
        return daftarBarang;

    }

    public Barang getBarang(long id) {
        Barang barang = new Barang();
        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                allColumns,
                "_id =" + id, null, null, null,
                null);

        cursor.moveToFirst();
        barang = cursorToBarang(cursor);

        cursor.close();
        return barang;
    }


    public void updateBarang(Barang b) {
        String strFilter = "_id=" + b.getId();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.COLUMN_NAME, b.getNama());
        contentValues.put(DBHelper.COLUMN_HARGA, b.getHarga());
        contentValues.put(DBHelper.COLUMN_MERK, b.getMerk());

        database.update(DBHelper.TABLE_NAME, contentValues, strFilter, null);


    }

    public void deleteBarang(long id) {
        String strFilter = "_id=" + id;


        database.delete(DBHelper.TABLE_NAME, strFilter, null);
    }


}