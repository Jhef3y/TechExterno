package br.net.techsoft.techsoftmobile;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.net.techsoft.techsoftmobile.Adapters.ConsClieAdapter;
import br.net.techsoft.techsoftmobile.Modelo.Cliente;
import br.net.techsoft.techsoftmobile.Modelo.Produto;

public class ConsultaClie extends AppCompatActivity {

    private ListView listVClieConsulta;
    private EditText edtFiltro;
    private List<Cliente> listaClientes;
    private ConsClieAdapter consClieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_clie);

        listaClientes = new ArrayList<>();

        listaClientes = new ArrayList<>(preecheLista());

        edtFiltro = (EditText) findViewById(R.id.edtFiltro);
        listVClieConsulta = (ListView) findViewById(R.id.listVClieConsulta);

        consClieAdapter = new ConsClieAdapter(this, listaClientes);
        listVClieConsulta.setAdapter(consClieAdapter);

        listVClieConsulta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cliente cl = new Cliente();
                for (Cliente c : listaClientes) {
                    if(c.getId() == id){
                        cl = c;
                    }
                }
                Bundle param = new Bundle();
                param.putSerializable("cliente",  cl);
                Intent intent = new Intent(getApplicationContext(), DetalhesCliente.class);
                intent.putExtras(param);
                startActivity(intent);
            }
        });

        edtFiltro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                consClieAdapter.filtrar(s, listaClientes);
                consClieAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        listaClientes = new ArrayList<>();
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

          //  listaClientes.add(produto);
            j++;
        }

    }

    public List<Cliente> preecheLista( ){

        List<Cliente> lista = new ArrayList<>();

        for(int i = 0; i < 20;){
            Cliente cliente = new Cliente();

            cliente.setId(Long.valueOf(i));
            cliente.setNomeFantasia("FANTASIA"+i);
            cliente.setNomeRazao("RAZAO"+i);
            cliente.setCpfCnpj(i * 11111);
            cliente.setEndereco("RUA XXX"+i);
            cliente.setCidade("CIDADE"+i);
            cliente.setNumero(i);
            cliente.setBairro("BAIRRO"+i);

            lista.add(cliente);
            i++;
        }
        return lista;
    }
}



