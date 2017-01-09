package br.net.techsoft.techsoftmobile.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import br.net.techsoft.techsoftmobile.Modelo.Produto;
import br.net.techsoft.techsoftmobile.R;
import br.net.techsoft.techsoftmobile.Util.Util;

/**
 * Created by Jhef3y on 03/08/2016.
 */
public class ConsProdVDAdapter extends BaseAdapter {

    private List<Produto> listProd;
    private final Activity activity;
    private Double doubAux;
    boolean status = false;
    Util util = new Util();


    public ConsProdVDAdapter(Activity activity, List<Produto> listProd) {
        this.listProd = listProd;
        this.activity = activity;

    }

    public void refresh(List<Produto> listProd) {
        this.listProd = listProd;
        notifyDataSetChanged();

        Double total = 0d;
        for (Produto p : listProd) {
            total += p.getValorU() * p.getUnidade();
        }
        TextView tvTotal = (TextView) activity.findViewById(R.id.edtTItens);

        if(total == 0){
            tvTotal.setText("");
        }else{
            tvTotal.setText(util.formatarDouble2c((double) total));
        }

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

        final View view = activity.getLayoutInflater().inflate(R.layout.lista_prod_det, viewGroup, false);

        final Produto produto = listProd.get(posit);

        EditText tvIdProd = (EditText) view.findViewById(R.id.tvIdProd);
        EditText tvDesc = (EditText) view.findViewById(R.id.tvDesc);
        final EditText tvUnit = (EditText) view.findViewById(R.id.tvUnit);
        final EditText tvValorU = (EditText) view.findViewById(R.id.tvValorU);
        final EditText tvValorT = (EditText) view.findViewById(R.id.tvValorT);


        tvIdProd.setText(Long.toString(produto.getId()));
        tvDesc.setText(produto.getDescricao());
        tvUnit.setText(Integer.toString(produto.getUnidade()));
        tvValorU.setText(util.formatarDouble2c(produto.getValorU()));
        doubAux = produto.getUnidade() * produto.getValorU();
        tvValorT.setText(util.formatarDouble2c(doubAux));


        return view;
    }
}
