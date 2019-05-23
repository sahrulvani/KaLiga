package com.smpn53sby.kaliga;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private TextView tvJudul, tvPengarang, tvDesk, tvKlasifikasi, tvTahun, tvISBN;
    private ImageView image;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvJudul = findViewById(R.id.detail_judul);
        tvPengarang = findViewById(R.id.detail_pengarang);
        tvDesk = findViewById(R.id.detail_desk);
        tvKlasifikasi = findViewById(R.id.detail_klasifikasi);
        tvTahun = findViewById(R.id.detail_tahun);
        tvISBN = findViewById(R.id.detail_isbn);

        image = findViewById(R.id.detail_image);

        getData();
    }

    private void getData(){
        final String getJudul = getIntent().getExtras().getString("dataJudul");
        final String getPengarang = getIntent().getExtras().getString("dataPengarang");
        final String getImage = getIntent().getExtras().getString("dataImage");
        final String getDesk = getIntent().getExtras().getString("dataDesk");
        final String getKlasifikasi = getIntent().getExtras().getString("dataKlasifikasi");
        final String getTahun = getIntent().getExtras().getString("dataTahun");
        final String getISBN = getIntent().getExtras().getString("dataISBN");

        tvJudul.setText(getJudul);
        tvPengarang.setText(getPengarang);
        tvDesk.setText(getDesk);
        tvKlasifikasi.setText(getKlasifikasi);
        tvTahun.setText(getTahun);
        tvISBN.setText(getISBN);
        Glide.with(this).load(getImage).fitCenter().into(image);
    }
}
