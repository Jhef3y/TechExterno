package br.net.techsoft.techsoftmobile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.net.techsoft.techsoftmobile.Modelo.Produto;
import br.net.techsoft.techsoftmobile.R;
import br.net.techsoft.techsoftmobile.Util.Util;

/**
 * Created by Jhef3y on 03/08/2016.
 */
public class ProdutoAdapter extends ArrayAdapter<Produto> {

    Context context;
    int resource;
    List<Produto> items, tempItems, suggestions;
    Util util = new Util();

    public ProdutoAdapter(Context context, int resource, List<Produto> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
        tempItems = new ArrayList<Produto>(items); // this makes the difference.
        suggestions = new ArrayList<Produto>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_produtos, parent, false);
        }
        Produto produto = items.get(position);
        if (produto != null) {
            TextView txtVNomeProd = (TextView) view.findViewById(R.id.txtVNomeProd);
            TextView txtVId = (TextView) view.findViewById(R.id.txtVId);
            TextView txtVVal = (TextView) view.findViewById(R.id.txtVVal);

            if (txtVNomeProd != null && txtVVal != null && txtVId != null)
                txtVId.setText(Long.toString(produto.getId()));
                txtVNomeProd.setText(produto.getDescricao());
                txtVVal.setText(util.formatarDouble2c(produto.getValorU()));

        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((Produto) resultValue).getDescricao();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Produto Produto : tempItems) {
                    if (Produto.getDescricao().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(Produto);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Produto> filterList = (ArrayList<Produto>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Produto Produto : filterList) {
                    add(Produto);
                    notifyDataSetChanged();
                }
            }
        }
    };
}