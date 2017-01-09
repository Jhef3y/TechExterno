package br.net.techsoft.techsoftmobile.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.net.techsoft.techsoftmobile.Modelo.Cliente;
import br.net.techsoft.techsoftmobile.R;
import br.net.techsoft.techsoftmobile.Util.Util;

/**
 * Created by Jhef3y on 03/08/2016.
 */
public class ConsClieAdapter extends BaseAdapter {

    private List<Cliente> listClient;
    private final Activity activity;
    Util util = new Util();


    public ConsClieAdapter(Activity activity, List<Cliente> listClient) {
        this.listClient = listClient;
        this.activity = activity;
    }

    public void refresh(List<Cliente> listClient) {
        this.listClient = listClient;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return listClient.size();
    }

    @Override
    public Object getItem(int i) {
        return listClient.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listClient.get(i).getId();
    }


    @Override
    public View getView(final int posit, View convertView, ViewGroup viewGroup) {

        final View view = activity.getLayoutInflater().inflate(R.layout.lista_clientes, viewGroup, false);

        final Cliente cliente = listClient.get(posit);

        TextView tvCodCliente = (TextView) view.findViewById(R.id.tvCodCliente);
        TextView tvNomeR = (TextView) view.findViewById(R.id.tvNomeR);
        TextView tvNomeF = (TextView) view.findViewById(R.id.tvNomeF);


        tvCodCliente.setText(Long.toString(cliente.getId()));
        tvNomeR.setText(cliente.getNomeRazao());
        tvNomeF.setText(cliente.getNomeFantasia());

        return view;
    }

    public void filtrar(CharSequence s, List<Cliente> lista){
        List<Cliente> listaFiltrada = new ArrayList<>();

        if(s.toString().equals("")){
            refresh(lista);
        }else{
            for (Cliente cliente : lista) {
                String id = String.valueOf(cliente.getId());
                if(cliente.getNomeRazao().contains(s.toString().toUpperCase()) ||
                        cliente.getNomeFantasia().contains(s.toString().toUpperCase()) ||
                        id.contains(s)){
                    listaFiltrada.add(cliente);
                }
            }
            refresh(listaFiltrada);

        }


    }



}
