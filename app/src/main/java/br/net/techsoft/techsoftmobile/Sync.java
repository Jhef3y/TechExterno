package br.net.techsoft.techsoftmobile;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.net.techsoft.techsoftmobile.DATA.Sop;
import br.net.techsoft.techsoftmobile.FTP.Upload;
import br.net.techsoft.techsoftmobile.Util.Util;

public class Sync extends AppCompatActivity {
    Util util = new Util();
    Sop sop = new Sop();
    Upload up = new Upload();
    List<String> enviados = new ArrayList<>();
    List<String> vendas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enviados = util.lerSends();
        vendas = sop.listarDir();

        for(int i = 0; i < enviados.size(); i++){
            for(int j = 0; j < vendas.size(); j++){
                if(vendas.get(j).equals(enviados.get(i))){
                    vendas.remove(j);
                }
            }
        }

        for(int i = 0; i < vendas.size(); i++){
            up.setNomeArquivo(vendas.get(i));
            up.execute();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 1500);
        }

        Intent it = new Intent(getApplicationContext(), MenuInicial.class);
        startActivity(it);


    }
}
