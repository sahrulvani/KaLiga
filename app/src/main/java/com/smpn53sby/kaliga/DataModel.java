package com.smpn53sby.kaliga;

public class DataModel {
    public String imageURL;
    public String judul;
    public String pengarang;
    public String key;
    public String deskripsi;
    public String klasifikasi;
    public String tahun;
    public String isbn;


    public DataModel() {

    }

    public DataModel(String imageURL, String judul, String pengarang) {
        this.imageURL = imageURL;
        this.judul = judul;
        this.pengarang = pengarang;
    }

    public DataModel(String imageURL, String judul, String pengarang, String key, String deskripsi, String klasifikasi, String tahun, String isbn) {
        this.imageURL = imageURL;
        this.judul = judul;
        this.pengarang = pengarang;
        this.key = key;
        this.deskripsi = deskripsi;
        this.klasifikasi = klasifikasi;
        this.tahun = tahun;
        this.isbn = isbn;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKlasifikasi() {
        return klasifikasi;
    }

    public void setKlasifikasi(String klasifikasi) {
        this.klasifikasi = klasifikasi;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
