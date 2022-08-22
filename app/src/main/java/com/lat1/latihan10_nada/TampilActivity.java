package com.lat1.latihan10_nada;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.lat1.latihan10_nada.db.DBSource;
import com.lat1.latihan10_nada.models.Barang;

import java.util.ArrayList;

public class TampilActivity extends AppCompatActivity {
    ListView listView;

    private DBSource dbSource;

    private ArrayList<Barang> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        listView = findViewById(R.id.listView);
        dbSource = new DBSource(this);

        dbSource.open();

        values = dbSource.getAllBarang();

        ArrayAdapter<Barang> adapter = new ArrayAdapter<Barang>(this,
                android.R.layout.simple_list_item_1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Barang b = (Barang) adapterView.getAdapter().getItem(i);

                Barang edit = dbSource.getBarang(b.getId());

                Intent intent = new Intent(TampilActivity.this, UbahActivity.class);
                Bundle bun = new Bundle();

                bun.putLong("id", edit.getId());
                bun.putString("nama", edit.getNama());
                bun.putString("harga", edit.getHarga());
                bun.putString("merk", edit.getMerk());
                intent.putExtras(bun);

                startActivity(intent);

                TampilActivity.this.finish();


            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Barang b = (Barang) adapterView.getAdapter().getItem(i);
                dbSource.deleteBarang(b.getId());
                Toast.makeText(TampilActivity.this, "Item " + b.getNama() + " dihapus", Toast.LENGTH_LONG).show();

                finish();
                startActivity(getIntent());
                return true;
            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();

        dbSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

        dbSource.open();
    }
}