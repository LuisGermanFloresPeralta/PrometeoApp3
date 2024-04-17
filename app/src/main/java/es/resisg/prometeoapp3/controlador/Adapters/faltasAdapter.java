package es.resisg.prometeoapp3.controlador.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.modelo.clases.Falta;

public class faltasAdapter extends RecyclerView.Adapter<faltasAdapter.faltasViewHolder> implements Filterable {
    private  ArrayList<Falta> faltasArrayList;
    private ArrayList<Falta>  faltasArrayListFiltrada;

    public faltasAdapter(ArrayList<Falta> faltasArrayList) {
        this.faltasArrayList = faltasArrayList;
        this.faltasArrayListFiltrada = faltasArrayList;
    }

    @NonNull
    @Override
    public faltasAdapter.faltasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.falta_item_layout,parent,false);
        return new faltasAdapter.faltasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull faltasAdapter.faltasViewHolder holder, int position) {
        Falta falta = faltasArrayList.get(position);
        holder.txtViewDiaSemanaFecha.setText(falta.getDia_semana_y_Fecha());
        holder.txtViewTipoFalta.setText(falta.getTipo_falta());
        holder.txtViewHoraFalta.setText(falta.getHora_falta());
    }

    @Override
    public int getItemCount() {
        return faltasArrayList.size();
    }

    public class faltasViewHolder extends RecyclerView.ViewHolder {

        private TextView txtViewDiaSemanaFecha,txtViewTipoFalta,txtViewHoraFalta;
        public faltasViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewDiaSemanaFecha = itemView.findViewById(R.id.txtViewDiaSemanaFecha_FaltatemLayout);
            txtViewTipoFalta= itemView.findViewById(R.id.txtViewTipoFalta_FaltatemLayout);
            txtViewHoraFalta= itemView.findViewById(R.id.txtViewHoraFalta_FaltatemLayout);
        }
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                /*si la cadena de texto esta vacia, el resultado filtrado sera igual a lista de faltas sin filtrar*/
                if(constraint == null || constraint.length()==0 ){
                    filterResults.count = faltasArrayListFiltrada.size();
                    filterResults.values= faltasArrayListFiltrada;
                }else {
                    /*por el contrario, revisaremos si la cadena coincide con el DiaSEmanaFecha de cada falta,
                     * si se da el caso añadimos esa falta ala lista de faltas que tambien coincide con la cadena,
                     * y por ultimo añadimos los datos nuevos a la respuesta */
                    ArrayList<Falta> faltasBuscadas = new ArrayList<>();
                    for (Falta f:faltasArrayListFiltrada) {
                        if(f.getDia_semana_y_Fecha().toLowerCase().contains(constraint.toString().toLowerCase())){
                            faltasBuscadas.add(f);
                        }
                    }
                    filterResults.count = faltasBuscadas.size();
                    filterResults.values= faltasBuscadas;
                }
                return filterResults;
            }

            // aqui publicamos los resultados que hemos procesado anteriormente a la lista de faltas original, notificamos los cambios al adapter
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                faltasArrayList=(ArrayList<Falta>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
