package br.net.techsoft.techsoftmobile.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.net.techsoft.techsoftmobile.Modelo.Produto;
import br.net.techsoft.techsoftmobile.R;
import br.net.techsoft.techsoftmobile.Util.Util;

/**
 * Created by Jhef3y on 03/08/2016.
 */
public class ItensVAdapter extends BaseAdapter {

    private List<Produto> listProd;
    private final Activity activity;
    private Double doubAux;
    boolean status = false;
    Util util = new Util();


    public ItensVAdapter(Activity activity, List<Produto> listProd) {
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

        final View view = activity.getLayoutInflater().inflate(R.layout.lista_itens, viewGroup, false);

        final Produto produto = listProd.get(posit);

        EditText tvIdProd = (EditText) view.findViewById(R.id.tvIdProd);
        EditText tvDesc = (EditText) view.findViewById(R.id.tvDesc);
        final EditText tvUnit = (EditText) view.findViewById(R.id.tvUnit);
        final EditText tvValorU = (EditText) view.findViewById(R.id.tvValorU);
        final EditText tvValorT = (EditText) view.findViewById(R.id.tvValorT);
        Button btnRemov = (Button) view.findViewById(R.id.btnRemov);
        Button btnEdit = (Button) view.findViewById(R.id.btnEdit);

        btnRemov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = listProd.indexOf(getItem(posit));
                listProd.remove(id );
                refresh(listProd);

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(status == false){
                    status = true;

                    tvUnit.setEnabled(true);
                    tvValorU.setEnabled(true);

                    tvUnit.setBackgroundColor(Color.parseColor("#e2e2e2"));
                    tvValorU.setBackgroundColor(Color.parseColor("#e2e2e2"));
                }else{

                    int unidade = Integer.parseInt(tvUnit.getText().toString());
                    Double valU = Double.valueOf(util.trocarVpP(tvValorU.getText().toString()));

                    if(unidade <= 0){
                        Toast.makeText(v.getContext(), "Digite a quantidade do(s) produto(s)!", Toast.LENGTH_SHORT).show();
                    }else if(valU <= 0){
                        Toast.makeText(v.getContext(), "Digite o valor Unitario do(s) produto(s)!", Toast.LENGTH_SHORT).show();
                    }else{
                        int id = listProd.indexOf(getItem(posit));

                        listProd.get(id).setUnidade(unidade);
                        listProd.get(id).setValorU(valU);

                        tvValorT.setText(util.formatarDouble2c(unidade * valU));
                        tvValorU.setText(util.formatarDouble2c(valU));

                        tvUnit.setEnabled(false);
                        tvValorU.setEnabled(false);

                        tvUnit.setBackgroundColor(Color.TRANSPARENT);
                        tvValorU.setBackgroundColor(Color.TRANSPARENT);

                        status = false;

                        refresh(listProd);

                    }

                }

            }
        });

//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int id = listProd.indexOf(getItem(posit));
//                listProd.get(id).setUnidade(listProd.get(id).getUnidade() + 1);
//                tvUnit.setText(Integer.toString(produto.getUnidade()));
//                doubAux = produto.getUnidade() * produto.getValorU();
//                tvValorT.setText(formatarDouble2c(doubAux));
//                //tvValorT.setText(Double.toString(doubAux));
//            }
//        });
//
//        btnRet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listProd.get(posit).setUnidade(listProd.get(posit).getUnidade() - 1);
//                doubAux = produto.getUnidade() * produto.getValorU();
//                tvValorT.setText(formatarDouble2c(doubAux));
//
//                if (produto.getUnidade() <= 0) {
//                    listProd.remove(posit);
//                    refresh(listProd);
//                } else {
//                    tvUnit.setText(Integer.toString(produto.getUnidade()));
//                }
//
//            }
//        });

        tvIdProd.setText(Long.toString(produto.getId()));
        tvDesc.setText(produto.getDescricao());
        tvUnit.setText(Integer.toString(produto.getUnidade()));
        tvValorU.setText(util.formatarDouble2c(produto.getValorU()));
        doubAux = produto.getUnidade() * produto.getValorU();
        tvValorT.setText(util.formatarDouble2c(doubAux));

        tvIdProd.setEnabled(false);
        tvDesc.setEnabled(false);
        tvUnit.setEnabled(false);
        tvValorU.setEnabled(false);
        tvValorT.setEnabled(false);

        return view;
    }
}
