package br.net.techsoft.techsoftmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import br.net.techsoft.techsoftmobile.FTP.Download;

public class MenuInicial extends AppCompatActivity {

    private ImageButton imgBVendas, imgBProd, imgBClien, imgBConsV, imgBConf, imgBSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);



        imgBVendas = (ImageButton) findViewById(R.id.imgBVendas);
        imgBProd = (ImageButton) findViewById(R.id.imgBProd);
        imgBClien = (ImageButton) findViewById(R.id.imgBClien);
        imgBConsV = (ImageButton) findViewById(R.id.imgBConsV);
        imgBConf = (ImageButton) findViewById(R.id.imgBConf);
        imgBSync = (ImageButton) findViewById(R.id.imgBSync);

        imgBVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), ClienteVenda.class);
                startActivity(it);
            }
        });

        imgBProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), ConsultaProd.class);
                startActivity(it);
            }
        });

        imgBClien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), ConsultaClie.class);
                startActivity(it);
            }
        });

        imgBConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        imgBConsV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConsultaVend.class);
                startActivity(intent);

            }
        });

        imgBSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Sync.class);
                startActivity(intent);
            }
        });
    }

}
