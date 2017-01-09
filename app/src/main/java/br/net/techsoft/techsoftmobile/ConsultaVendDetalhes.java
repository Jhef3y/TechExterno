package br.net.techsoft.techsoftmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import br.net.techsoft.techsoftmobile.Adapters.ConsProdAdapter;
import br.net.techsoft.techsoftmobile.Adapters.ConsProdVDAdapter;
import br.net.techsoft.techsoftmobile.Modelo.Cliente;
import br.net.techsoft.techsoftmobile.Modelo.Venda;

public class ConsultaVendDetalhes extends AppCompatActivity {

    private TextView tvClieDV;
    private ListView listVProdVendD;
    private Venda venda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_vend_detalhes);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        venda = (Venda) bundle.getSerializable("venda");

        tvClieDV = (TextView) findViewById(R.id.tvClieDV);
        listVProdVendD = (ListView)findViewById(R.id.listVProdVendD);

        tvClieDV.setText(venda.getCliente().getNomeFantasia());

        ConsProdVDAdapter adp = new ConsProdVDAdapter(this, venda.getProdutos());
        listVProdVendD.setAdapter(adp);
    }
}
