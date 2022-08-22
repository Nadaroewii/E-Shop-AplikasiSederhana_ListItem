package com.lat1.latihan10_nada.models;

public class Barang {
    private long id;
    private String nama;
    private String merk;
    private String harga;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    @Override
    public String toString() {
        return "Barang " +
                "nama = " + nama +
                ", merk = " + merk +
                ", harga = " + harga;
    }


    public Barang() {
    }

}