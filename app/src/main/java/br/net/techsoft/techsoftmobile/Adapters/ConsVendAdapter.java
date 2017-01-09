package br.net.techsoft.techsoftmobile.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.net.techsoft.techsoftmobile.Modelo.Produto;
import br.net.techsoft.techsoftmobile.Modelo.Venda;
import br.net.techsoft.techsoftmobile.R;
import br.net.techsoft.techsoftmobile.Util.Util;

/**
 * Created by Jhef3y on 03/08/2016.
 */
public class ConsVendAdapter extends BaseAdapter {

    private List<Venda> listVenda;
    private final Activity activity;
    Util util = new Util();


    public ConsVendAdapter(Activity activity, List<Venda> listVenda) {
        this.listVenda = listVenda;
        this.activity = activity;
    }

    public void refresh(List<Venda> listVenda) {
        this.listVenda = listVenda;
        notifyDataSetChanged();

    }

    public void verificaSit(Venda venda) {
        List<String> enviados = new ArrayList<>(util.lerSends());

        for (String cod : enviados) {
            String aux = String.valueOf(cod.subSequence(0, 6));
            String gamb = String.valueOf(venda.getId());
            if(gamb.length() == 5){
                gamb = "0" + gamb;
            }

            if (gamb.equals(aux)) {
                venda.setSituacao("T");
                break;
            } else {
                venda.setSituacao("P");
            }

        }

    }

    @Override
    public int getCount() {
        return listVenda.size();
    }

    @Override
    public Object getItem(int i) {
        return listVenda.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listVenda.get(i).getId();
    }


    @Override
    public View getView(final int posit, View convertView, ViewGroup viewGroup) {

        final View view = activity.getLayoutInflater().inflate(R.layout.lista_vendas, viewGroup, false);


        final Venda venda = listVenda.get(posit);

        verificaSit(venda);

        Double total = 0d;
        for (Produto produto : venda.getProdutos()) {
            total += (produto.getValorU() * produto.getUnidade());
        }


        TextView tvIdVend = (TextView) view.findViewById(R.id.tvIdVend);
        TextView tvCliV = (TextView) view.findViewById(R.id.tvCliV);
        TextView tvSituacao = (TextView) view.findViewById(R.id.tvSituacao);
        TextView tvTotalVend = (TextView) view.findViewById(R.id.tvTotalVend);


        tvIdVend.setText(Long.toString(venda.getId()));
        tvCliV.setText(venda.getCliente().getNomeFantasia());
        tvSituacao.setText(venda.getSituacao());
        tvTotalVend.setText(util.formatarDouble2c(total));


        return view;
    }

    public void filtrar(CharSequence s, List<Venda> lista){
        List<Venda> listaFiltrada = new ArrayList<>();

        if(s.toString().equals("")){
            refresh(lista);
        }else{
            for (Venda venda : lista) {
                String id = String.valueOf(venda.getId());
                if(venda.getCliente().getNomeRazao().contains(s.toString().toUpperCase()) ||
                        venda.getCliente().getNomeFantasia().contains(s.toString().toUpperCase()) ||
                        id.contains(s)){
                    listaFiltrada.add(venda);
                }
            }
            refresh(listaFiltrada);

        }


    }



}
