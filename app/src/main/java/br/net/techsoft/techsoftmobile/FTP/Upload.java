package br.net.techsoft.techsoftmobile.FTP;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import br.net.techsoft.techsoftmobile.Util.Util;

/**
 * Created by Jhef3y on 31/08/2016.
 */
public class Upload extends AsyncTask {

    String host;
    int porta;
    String usuario;
    String senha;
    FTPClient mFtp;
    String nomeArquivo;

    @Override
    protected Object doInBackground(Object[] objects) {
        lerConfigs();
        if (conectar()) {

            try {
                String diret = Environment.getExternalStorageDirectory() + File.separator + "Techsoft" + File.separator +"Salvo" + File.separator + nomeArquivo;
                FileInputStream arqEnviar = new FileInputStream(diret);
                mFtp.setFileTransferMode(FTPClient.STREAM_TRANSFER_MODE);
                mFtp.setFileType(FTPClient.STREAM_TRANSFER_MODE);

                Boolean ret = mFtp.storeFile("/vendas/" + nomeArquivo, arqEnviar);

                if(ret){
                    new Util().gravarEnviados(nomeArquivo);
                }

                arqEnviar.close();
                Log.e("RETORNO <----", ret.toString());

            } catch (Exception e) {
                Log.e("Tente outra vezzzz", e.getMessage());
            } finally {
                try {
                    if (mFtp.isConnected()) {
                        mFtp.logout();
                        mFtp.disconnect();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    public void lerConfigs(){
        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Techsoft" , "configs.ini");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String linha;
            List<String> linhas = new ArrayList<>();
            while ((linha = bufferedReader.readLine()) != null) {
                linhas.add(linha);
            }
            host = linhas.get(0);
            usuario = linhas.get(1);
            senha = linhas.get(2);
            porta = Integer.parseInt(linhas.get(3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean conectar() {
        boolean status = false;
        mFtp = new FTPClient();
        //Conectando com o Host
        try {
            mFtp.connect(host, porta);
        } catch (IOException e) {

            e.printStackTrace();
        }
        //Checa o código de resposta, se for positivo, a conexão foi feita
        if (FTPReply.isPositiveCompletion(mFtp.getReplyCode())) {

            //Logando com username e senha
            try {
                status = mFtp.login(usuario, senha);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Setando para o modo de transferência de Arquivos
            try {
                mFtp.setFileType(FTP.BINARY_FILE_TYPE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mFtp.enterLocalPassiveMode();
            Log.i("STATUS", "CONECTADO");
        }
        return status;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
}
