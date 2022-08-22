package com.lat1.latihan10_nada;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.lat1.latihan10_nada.db.DBSource;
import com.lat1.latihan10_nada.models.Barang;

public class TambahActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSubmit;

    EditText edNama, edHarga, edMerk;

    DBSource dbSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        edNama = findViewById(R.id.ed_nama);
        edHarga = findViewById(R.id.ed_harga);
        edMerk = findViewById(R.id.ed_merk);

        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(this);

        dbSource = new DBSource(this);
        dbSource.open();

    }

    @Override
    public void onClick(View view) {

            switch (view.getId()){
                case R.id.btn_submit:
                    if(TextUtils.isEmpty(edNama.getText().toString().trim())
                            && TextUtils.isEmpty((edHarga.getText().toString().trim()))
                            && TextUtils.isEmpty(edMerk.getText().toString().trim())
                    ){

                        edNama.setError("Mohon Isi Data");
                        edHarga.setError("Mohon Isi Data");
                        edMerk.setError("Mohon Isi Data");

                    }else if(TextUtils.isEmpty((edNama.getText().toString().trim())) ){
                        edNama.setError("Mohon Isi Nama");

                    }else if(TextUtils.isEmpty((edHarga.getText().toString().trim())) ){
                        edHarga.setError("Mohon Isi Harga");

                    }else if(TextUtils.isEmpty((edMerk.getText().toString().trim())) ){
                        edMerk.setError("Mohon Isi Merk");

                    } else {
                            TambahItem();
                        Toast.makeText(TambahActivity.this, "Berhasil ditambah", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(TambahActivity.this, TampilActivity.class);
                            startActivity(intent);
                        }
                    break;
            }
        }

    private void TambahItem() {
        String nama = null;
        String merk = null;
        String harga = null;

        Barang barang = null;
        nama = edNama.getText().toString().trim();
        harga = edHarga.getText().toString().trim();
        merk = edMerk.getText().toString().trim();

        barang = dbSource.createBarang(nama, merk, harga);
    }
    void KonfirmasiTambah() {
        new AlertDialog.Builder(this).setTitle("Tambah Item")
                .setMessage("Apakah Anda ingin menambahkan Item ini?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        TambahItem();
                        Toast.makeText(TambahActivity.this, "Berhasil ditambah", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(TambahActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }).show();
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            KonfirmasiTambah();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStop() {
        super.onStop();
        dbSource.close();
    }
}