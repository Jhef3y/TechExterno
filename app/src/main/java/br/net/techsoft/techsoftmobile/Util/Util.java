package br.net.techsoft.techsoftmobile.Util;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.net.techsoft.techsoftmobile.Modelo.Produto;
import br.net.techsoft.techsoftmobile.Modelo.Venda;

/**
 * Created by Jhef3y on 05/10/2016.
 */
public class  Util {
    public String formatarDouble2c(Double d) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        df.format(d);
        Log.e("Formatado>>>>>>", df.format(d));
        return df.format(d);
    }

    public String trocarVpP(String numeroForm) {
        char array[] = numeroForm.toCharArray();
        String flag = ",";
        String ponto = ".";

        for (int i = 0; i < numeroForm.length(); ) {
            if (array[i] == flag.charAt(0)) {
                array[i] = ponto.charAt(0);
            }
            i++;
        }
        numeroForm = String.valueOf(array);
        return numeroForm;
    }

    public int containsObject(List<Produto> lista, Produto obj){

        for(int i = 0; i < lista.size();){
            if(lista.get(i).getId() == obj.getId()){
                return i;
            }
            i++;
        }
        return -1;
    }

    public void gravarEnviados(String chave) throws IOException {
        String diretorio = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Techsoft" + File.separator + "Enviados";

        File fileEnv = new File(diretorio, "sends.env");
        FileInputStream fileInp = new FileInputStream(fileEnv);

        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileEnv));

        String linha;
        List<String> dados = new ArrayList<>();
        while ((linha = bufferedReader.readLine()) != null) {
            dados.add(linha);
        }

        String linGravar = chave;
        dados.add(linGravar);

        BufferedWriter bufW = new BufferedWriter(new FileWriter(fileEnv));
        try {
            for (String linh : dados) {
                bufW.write(linh + "\r\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        bufW.close();

    }

    public List<String> lerSends(){
        List<String> dados = new ArrayList<>();

        String diretorio = Environment.getExternalStorageDirectory() + File.separator + "Techsoft" + File.separator + "Enviados";

        try {
            File file = new File(diretorio, "sends.env");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String linha;

            while ((linha = bufferedReader.readLine()) != null) {
                dados.add(linha);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dados;
    }

}
