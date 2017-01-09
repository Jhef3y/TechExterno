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

import br.net.techsoft.techsoftmobile.Modelo.Produto;
import br.net.techsoft.techsoftmobile.R;
import br.net.techsoft.techsoftmobile.Util.Util;

/**
 * Created by Jhef3y on 03/08/2016.
 */
public class ConsProdAdapter extends BaseAdapter {

    private List<Produto> listProd;
    private final Activity activity;
    Util util = new Util();


    public ConsProdAdapter(Activity activity, List<Produto> listProd) {
        this.listProd = listProd;
        this.activity = activity;
    }

    public void refresh(List<Produto> listProd) {
        this.listProd = listProd;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return listProd.size();
    }

    @Override
    public Object getItem(int i) {
        return listProd.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listProd.get(i).getId();
    }


    @Override
    public View getView(final int posit, View convertView, ViewGroup viewGroup) {

        final View view = activity.getLayoutInflater().inflate(R.layout.lista_produtos, viewGroup, false);

        final Produto produto = listProd.get(posit);

        TextView tvNomeProd = (TextView) view.findViewById(R.id.txtVNomeProd);
        TextView tvId = (TextView) view.findViewById(R.id.txtVId);
        TextView tvVal = (TextView) view.findViewById(R.id.txtVVal);


        tvNomeProd.setText(produto.getDescricao());
        tvId.setText(Long.toString(produto.getId()));
        tvVal.setText(util.formatarDouble2c(produto.getValorU()));

        return view;
    }

    public void filtrar(CharSequence s, List<Produto> lista){
        List<Produto> listaFiltrada = new ArrayList<>();

        if(s.toString().equals("")){
            refresh(lista);
        }else{
            for (Produto produto : lista) {
                if(produto.getDescricao().contains(s.toString().toUpperCase()) ||
                        produto.getId().toString().contains(s.toString())){
                    listaFiltrada.add(produto);
                }
            }
            refresh(listaFiltrada);

        }


    }



}
