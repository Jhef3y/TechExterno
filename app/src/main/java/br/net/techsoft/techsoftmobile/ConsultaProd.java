package br.net.techsoft.techsoftmobile;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.net.techsoft.techsoftmobile.Adapters.ConsProdAdapter;
import br.net.techsoft.techsoftmobile.Modelo.Produto;

public class ConsultaProd extends AppCompatActivity {

    private ListView listVProdConsulta;
    private EditText edtFiltro;
    private List<Produto> listaProdutos;
    private ConsProdAdapter consProdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_prod);

        lerTxt();

        edtFiltro = (EditText) findViewById(R.id.edtFiltro);
        listVProdConsulta = (ListView) findViewById(R.id.listVProdConsulta);

        consProdAdapter = new ConsProdAdapter(this, listaProdutos);
        listVProdConsulta.setAdapter(consProdAdapter);

        edtFiltro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("digitou Before " +s.toString(), "<------");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("digitou On "+s.toString(), "<------");

                consProdAdapter.filtrar(s, listaProdutos);
                consProdAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("digitou After " + s.toString(), "<------");
            }
        });
    }

    public boolean lerTxt() {
        List<String> dados = new ArrayList<>();

        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Techsoft", "produtos.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                dados.add(linha);
            }

            compararString(dados);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void compararString(List<String> lista) {
        listaProdutos = new ArrayList<>();
        for (int j = 0; j < lista.size(); ) {
            Produto produto = new Produto();
            String flag = ";";
            String cat = new String();
            String id = new String();
            String desc = new String();
            String valU = new String();
            String cor = "#";

            int i = 0;
            int tamLinha = lista.get(j).length() - 1;
            String str = lista.get(j);

            while (str.charAt(i) != flag.charAt(0) && tamLinha > i) {
                cat += str.charAt(i);
                i++;
            }
            i++;
            while (str.charAt(i) != flag.charAt(0) && tamLinha > i) {
                id += str.charAt(i);
                i++;
            }
            i++;
            while (str.charAt(i) != flag.charAt(0) && tamLinha > i) {
                desc += str.charAt(i);
                i++;
            }
            i++;
            while (str.charAt(i) != flag.charAt(0) && tamLinha >= i) {
                valU += str.charAt(i);
                i++;
            }
            i++;
            while (str.charAt(i) != flag.charAt(0) && tamLinha >= i) {
                cor += str.charAt(i);
                i++;
            }

            produto.setId(Long.parseLong(id));
            produto.setDescricao(desc);
            produto.setValorU(Double.parseDouble(valU));
            produto.setCor(cor);

            listaProdutos.add(produto);
            j++;
        }

    }
}
