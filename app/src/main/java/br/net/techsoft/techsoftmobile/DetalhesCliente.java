package br.net.techsoft.techsoftmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import br.net.techsoft.techsoftmobile.Modelo.Cliente;

public class DetalhesCliente extends AppCompatActivity {

    private TextView tvNomeR, tvNomeF, tvEndereco, tvNumero, tvBairro, tvCep, tvCidade, tvEstado, tvTel, tvCel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_cliente);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        Cliente cliente = (Cliente) bundle.getSerializable("cliente");

        tvNomeR = (TextView)findViewById(R.id.tvNomeR);
        tvNomeF = (TextView)findViewById(R.id.tvNomeF);
        tvEndereco = (TextView)findViewById(R.id.tvEndereco);
        tvNumero = (TextView)findViewById(R.id.tvNumero);
        tvBairro = (TextView)findViewById(R.id.tvBairro);
        tvCep = (TextView)findViewById(R.id.tvCep);
        tvCidade = (TextView)findViewById(R.id.tvCidade);
        tvEstado = (TextView)findViewById(R.id.tvEstado);
        tvTel = (TextView)findViewById(R.id.tvTel);
        tvCel = (TextView)findViewById(R.id.tvCel);

        tvNomeR.setText(cliente.getNomeRazao());
        tvNomeF.setText(cliente.getNomeFantasia());
        tvEndereco.setText(cliente.getEndereco());
        tvNumero.setText(Integer.toString(cliente.getNumero()));
        tvBairro.setText(cliente.getBairro());
        tvCep.setText(Integer.toString(cliente.getCep()));
        tvCidade.setText(cliente.getCidade());
        tvEstado.setText(cliente.getEstado());
        tvTel.setText(Integer.toString(cliente.getTelefone()));
        tvCel.setText(Integer.toString(cliente.getCelular()));

    }
}
