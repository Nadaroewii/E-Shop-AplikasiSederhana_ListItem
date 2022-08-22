package com.lat1.latihan10_nada;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lat1.latihan10_nada.db.DBSource;
import com.lat1.latihan10_nada.models.Barang;

public class UbahActivity extends AppCompatActivity {

    EditText edNama, edHarga, edMerk;
    Button btnSubmit, btnCancel;

    DBSource dbSource;

    Barang barang;

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        edHarga = findViewById(R.id.ed_harga);
        edNama = findViewById(R.id.ed_nama);
        edMerk = findViewById(R.id.ed_merk);

        btnSubmit = findViewById(R.id.btn_submit);
        btnCancel = findViewById(R.id.btn_cancel);

        Bundle bun = this.getIntent().getExtras();

        id = bun.getLong("id");
        String harga = bun.getString("harga");
        String merk = bun.getString("merk");
        String nama = bun.getString("nama");

        edNama.setText(nama);
        edHarga.setText(harga);
        edMerk.setText(merk);

        dbSource = new DBSource(this);
        dbSource.open();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                KonfirmasiUpdate();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UbahActivity.this, TampilActivity.class);
                startActivity(i);

            }
        });
    }

    private void updateData() {
        Barang barang = new Barang();
        barang.setNama(edNama.getText().toString().trim());
        barang.setHarga(edHarga.getText().toString().trim());
        barang.setMerk(edMerk.getText().toString().trim());
        barang.setId(id);
        dbSource.updateBarang(barang);

        Intent i = new Intent(UbahActivity.this, TampilActivity.class);

        startActivity(i);

        UbahActivity.this.finish();


    }

    void KonfirmasiUpdate() {
        new AlertDialog.Builder(this).setTitle("Update Item")
                .setMessage("Apakah Anda ingin mengubah Item ini?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        updateData();
                        Toast.makeText(UbahActivity.this, "Berhasil diupdate", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(UbahActivity.this, TampilActivity.class);
                startActivity(intent);
                finish();
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
            KonfirmasiUpdate();
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