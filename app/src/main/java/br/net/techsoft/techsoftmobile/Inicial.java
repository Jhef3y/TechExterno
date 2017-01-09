package br.net.techsoft.techsoftmobile;

import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.io.File;

import br.net.techsoft.techsoftmobile.FTP.Download;

public class Inicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        Download download = new Download();
        download.execute();
//
        //Cria Diretorios
        File salvos = new File(Environment.getExternalStorageDirectory() + File.separator + "Techsoft" + File.separator + "Salvo");
        salvos.mkdirs();
        File enviados = new File(Environment.getExternalStorageDirectory() + File.separator + "Techsoft" + File.separator + "Enviados");
        enviados.mkdirs();

        Handler handler = new Handler();
        long delay = 6000;
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MenuInicial.class);
                startActivity(intent);
            }
        }, delay);


    }
}
