package br.net.techsoft.techsoftmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.net.techsoft.techsoftmobile.Adapters.ClienteAdapter;
import br.net.techsoft.techsoftmobile.Modelo.Cliente;
import br.net.techsoft.techsoftmobile.Modelo.Produto;

public class ClienteVenda extends AppCompatActivity {

    private AutoCompleteTextView autoCompCli;
    private TextView tvCodCli, tvNomeCli, tvEnder, tvBairro, tvCep, tvCidade, tvEstado;
    private List<Cliente> lisClientes;
    private Button btnCancel, btnProx;
    Cliente cliente = new Cliente();
    List<Produto> listaItens = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        preencheLista();

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            listaItens = (List<Produto>) bundle.getSerializable("listProd");
            cliente = (Cliente) bundle.getSerializable("cliente");
        }

        Log.e(Integer.toString(lisClientes.size()), "SIZE LISTA CLIENTE");

////////////////////////DECLARACOES//////////////////////

        autoCompCli = (AutoCompleteTextView)findViewById(R.id.autoCompCli);
        autoCompCli.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        tvCodCli = (TextView)findViewById(R.id.tvCodCli);
        tvNomeCli = (TextView)findViewById(R.id.tvNomeCli);
        tvEnder = (TextView)findViewById(R.id.tvEnder);
        tvBairro = (TextView)findViewById(R.id.tvBairro);
        tvCep = (TextView)findViewById(R.id.tvCep);
        tvCidade = (TextView)findViewById(R.id.tvCidade);
        tvEstado = (TextView)findViewById(R.id.tvEstado);
        btnProx = (Button)findViewById(R.id.btnProx);
        btnCancel = (Button)findViewById(R.id.btnCancel);

        if(cliente.getId() != null){
            tvCodCli.setText(Long.toString(cliente.getId()));
            tvNomeCli.setText(cliente.getNomeRazao());
            tvEnder.setText(cliente.getEndereco());
            tvBairro.setText(cliente.getBairro());
            tvCep.setText(Integer.toString(cliente.getCep()));
            tvCidade.setText(cliente.getCidade());
            tvEstado.setText(cliente.getEstado());
        }


//////////////////////AUTOCOMPLETE//////////////////////
        final ClienteAdapter clienteAdapter = new ClienteAdapter(this, R.layout.activity_cliente, lisClientes);
        autoCompCli.setAdapter(clienteAdapter);

        autoCompCli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cliente = clienteAdapter.getItem(position);

                tvCodCli.setText(Long.toString(cliente.getId()));
                tvNomeCli.setText(cliente.getNomeRazao());
                tvEnder.setText(cliente.getEndereco());
                tvBairro.setText(cliente.getBairro());
                tvCep.setText(Integer.toString(cliente.getCep()));
                tvCidade.setText(cliente.getCidade());
                tvEstado.setText(cliente.getEstado());

                desabilitarButtons();

                autoCompCli.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

            }
        });

        btnProx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cliente.getId() == null){
                    Toast.makeText(getApplicationContext(), "Cliente não Informado!", Toast.LENGTH_SHORT).show();
                }else{

                    if(listaItens == null){

                        Bundle param = new Bundle();
                        param.putSerializable("cliente",  cliente);

                        Intent intent = new Intent(getApplicationContext(), ProdutosVenda.class);
                        intent.putExtras(param);
                        startActivity(intent);

                    }else{

                        Bundle param = new Bundle();
                        param.putSerializable("cliente",  cliente);
                        param.putSerializable("listProd", (Serializable) listaItens);

                        Intent intent = new Intent(getApplicationContext(), ProdutosVenda.class);
                        intent.putExtras(param);
                        startActivity(intent);
                    }


                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuInicial.class);
                startActivity(intent);
            }
        });


    }


    public void desabilitarButtons(){
        tvCodCli.setEnabled(false);
        tvNomeCli.setEnabled(false);
        tvEnder.setEnabled(false);
        tvBairro.setEnabled(false);
        tvCep.setEnabled(false);
        tvCidade.setEnabled(false);
        tvEstado.setEnabled(false);
    }

    public void preencheLista(){

        lisClientes = new ArrayList<>();

        for(int i = 0; i < 10;){
            Cliente c = new Cliente();

            c.setId(Long.valueOf(i)+10);
            c.setNomeFantasia("Fantasia"+i);
            c.setNomeRazao("Razao"+i);
            c.setEndereco("Endereco"+i);
            c.setCpfCnpj(i*12);
            c.setCidade("Cidade"+i);
            c.setCep(i*8);
            c.setBairro("Bairro"+i);
            c.setNumero(i*10);

            i++;
            lisClientes.add(c);
        }
    }

}
