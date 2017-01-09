package br.net.techsoft.techsoftmobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jhef3y on 12/09/2016.
 */
public class Configs extends AppCompatActivity {

    Button btnConfSalvar, btnConfCanc;
    EditText edtEndereco, edtPorta, edtUserFtp, edtSenhaFtp;
    String endereco, porta, user, senha;
    //Parametros
    Boolean mesa = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configs);

        edtEndereco = (EditText)findViewById(R.id.edtEndereco);
        edtPorta = (EditText)findViewById(R.id.edtPorta);
        edtUserFtp = (EditText)findViewById(R.id.edtUserFtp);
        edtSenhaFtp = (EditText)findViewById(R.id.edtSenhaFtp);
        btnConfSalvar = (Button)findViewById(R.id.btnConfSalvar);
        btnConfCanc = (Button)findViewById(R.id.btnConfCancelar);

        carregarConfig();

        btnConfCanc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Configs.this, MenuInicial.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnConfSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                endereco = String.valueOf(edtEndereco.getText());
                porta = String.valueOf(edtPorta.getText());
                user = String.valueOf(edtUserFtp.getText());
                senha = String.valueOf(edtSenhaFtp.getText());

                try {
                    salvarConf();
                    Toast.makeText(Configs.this, "Configurações salvas com sucesso!", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    new AlertDialog.Builder(Configs.this).setTitle("Error").setMessage("Falha ao salvar configuracões").show();
                }


                Runnable mRun = new Runnable () {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Configs.this, MenuInicial.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                };

                Handler mHandler = new Handler();
                mHandler.postDelayed(mRun, 1500);
            }
        });

    }

    public boolean salvarConf() throws FileNotFoundException {
        String diretorio = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Techsoft";
        String nomeArquivo = "configs.ini";

        File fileExt = new File(diretorio, nomeArquivo);

        //Cria o arquivo
        fileExt.getParentFile().mkdirs();

        //Abre o arquivo
        FileOutputStream fosExt = null;
        fosExt = new FileOutputStream(fileExt);

        try {
            fosExt.write(endereco.getBytes());
            fosExt.write("\r\n".getBytes());
            fosExt.write(user.getBytes());
            fosExt.write("\r\n".getBytes());
            fosExt.write(senha.getBytes());
            fosExt.write("\r\n".getBytes());
            fosExt.write(porta.getBytes());
            fosExt.write("\r\n".getBytes());
            fosExt.write(("mesa  - " + mesa.toString()).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try {
            fosExt.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rdSim:
                if (checked)
                    mesa = true;
                break;
            case R.id.rdNao:
                if (checked)
                    mesa = false;
                break;
        }
    }

    public void carregarConfig(){
        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Techsoft" , "configs.ini");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String linha;
            List<String> linhas = new ArrayList<>();
            while ((linha = bufferedReader.readLine()) != null) {
                linhas.add(linha);
            }
            edtEndereco.setText(linhas.get(0));
            edtUserFtp.setText(linhas.get(1));
            edtSenhaFtp.setText(senha = linhas.get(2));
            edtPorta.setText(porta = linhas.get(3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
