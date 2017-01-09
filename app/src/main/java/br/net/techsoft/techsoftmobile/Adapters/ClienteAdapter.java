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

import br.net.techsoft.techsoftmobile.Modelo.Cliente;
import br.net.techsoft.techsoftmobile.R;
import br.net.techsoft.techsoftmobile.Util.Util;

/**
 * Created by Jhef3y on 03/08/2016.
 */
public class ClienteAdapter extends ArrayAdapter<Cliente> {

    int resource;
    Context context;
    List<Cliente> items, tempItems, suggestions;
    Util util = new Util();

    public ClienteAdapter(Context context, int resource, List<Cliente> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
        tempItems = new ArrayList<Cliente>(items); // this makes the difference.
        suggestions = new ArrayList<Cliente>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_clientes, parent, false);
        }
        Cliente Cliente = items.get(position);
        if (Cliente != null) {
            TextView tvCodCliente = (TextView) view.findViewById(R.id.tvCodCliente);
            TextView tvNomeR = (TextView) view.findViewById(R.id.tvNomeR);
            TextView tvNomeF = (TextView) view.findViewById(R.id.tvNomeF);

            if (tvCodCliente != null && tvNomeR != null && tvNomeF != null)
                tvCodCliente.setText(Long.toString(Cliente.getId()));
                tvNomeR.setText(Cliente.getNomeRazao());
                tvNomeF.setText(Cliente.getNomeFantasia());

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
            String str = ((Cliente) resultValue).getNomeRazao();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Cliente Cliente : tempItems) {
                    if (Cliente.getNomeFantasia().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                            Cliente.getNomeRazao().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                            Cliente.getId().toString().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(Cliente);
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
            List<Cliente> filterList = (ArrayList<Cliente>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Cliente Cliente : filterList) {
                    add(Cliente);
                    notifyDataSetChanged();
                }
            }
        }
    };
}