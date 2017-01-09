package br.net.techsoft.techsoftmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.net.techsoft.techsoftmobile.Adapters.ConsVendAdapter;
import br.net.techsoft.techsoftmobile.DATA.Sop;
import br.net.techsoft.techsoftmobile.Modelo.Venda;

public class ConsultaVend extends AppCompatActivity {

    private EditText edtFiltroVend;
    private ListView listVVendasCons;
    ConsVendAdapter consVendAdapter;
    List<Venda> vendas = new ArrayList<>();
    List<String> enviados ;
    Sop sop = new Sop();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_vend);

        vendas = sop.separarVenda();

        edtFiltroVend = (EditText)findViewById(R.id.edtFiltroVend);

        listVVendasCons = (ListView)findViewById(R.id.listVVendasCons);

        consVendAdapter = new ConsVendAdapter(this, vendas);
        listVVendasCons.setAdapter(consVendAdapter);

        listVVendasCons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Venda venda = vendas.get(position);

                Bundle param = new Bundle();
                param.putSerializable("venda",  venda);
                Intent intent = new Intent(getApplicationContext(), ConsultaVendDetalhes.class);
                intent.putExtras(param);
                startActivity(intent);
            }
        });

    }
}
