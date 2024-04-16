package es.resisg.prometeoapp3.controlador.Adapters;

import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.clases.ActuacionParticular;

public class actuacionesAdapter extends RecyclerView.Adapter<actuacionesAdapter.actuacionesViewHodler> implements Filterable {
    //atributos
    private ActuacionInterface actuacionSeleccionada;
    private List<ActuacionParticular> actuacionParticularArrayList;
    private List<ActuacionParticular> actuacionParticularArrayListFiltrada;

    //Constructor
    public actuacionesAdapter(ArrayList<ActuacionParticular> actuacionParticularArrayList,ActuacionInterface actuacionSeleccionada ) {
        this.actuacionSeleccionada= actuacionSeleccionada;
        this.actuacionParticularArrayList = actuacionParticularArrayList;
        this.actuacionParticularArrayListFiltrada = actuacionParticularArrayList;
    }

    @NonNull
    @Override
    //aqui es donde se infla el layout con los items
    public actuacionesAdapter.actuacionesViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actuacion_item_layout,parent,false);
        return new actuacionesAdapter.actuacionesViewHodler(view);
    }

    @Override
    //aqui es donde se asignan los valores a las vistas de cada Item
    //dependiendo de su posicion en el recyclerView
    public void onBindViewHolder(@NonNull actuacionesAdapter.actuacionesViewHodler holder, int position) {
        ActuacionParticular actuacion = actuacionParticularArrayList.get(position);
        holder.textView1.setText(actuacion.getNombre_profesor());
        holder.textView2.setText(actuacion.getTipo_actuacion());
        holder.textView3.setText(new SimpleDateFormat("dd/MM/yyyy").format(actuacion.getFecha()));
        holder.textView4.setText(actuacion.getComentario());
    }

    @Override
    //aqui solo se devuelve el número de Item que tiene el recyclerView
    public int getItemCount() {
        return actuacionParticularArrayList.size();
    }

    /*aqui relacionamos los elementos de las vistas
       como si fuera en un OnCreate de un MainActivity
       , y setteamos el onclick a cada elemento del RecyclerView*/
    public class actuacionesViewHodler extends RecyclerView.ViewHolder{
        TextView textView1,textView2,textView3,textView4;
        public actuacionesViewHodler(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.txtViewNombreProfesorItem);
            textView2=itemView.findViewById(R.id.txtViewOtrasInformacionesItem);
            textView3=itemView.findViewById(R.id.txtViewFechaHoraItem);
            textView4=itemView.findViewById(R.id.txtViewComentarioItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actuacionSeleccionada.OnClickActuacion(actuacionParticularArrayList.get(getBindingAdapterPosition()));                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                /*si la cadena de texto esta vacia, el resultado filtrado sera igual a lista de actuaciones sin filtrar*/
                if(constraint == null | constraint.length()==0){
                    filterResults.count = actuacionParticularArrayListFiltrada.size();
                    filterResults.values= actuacionParticularArrayListFiltrada;
                }else {
                    /*por el contrario, revisaremos si la cadena coincide con el nombre del profesor de cada actuacion,
                    * si se da el caso añadimos esa actuacion ala lista de actuacione que tambien coincide con la cadena,
                    * y por ultimo añadimos los datos nuevos a la respuesta */
                    ArrayList<ActuacionParticular> actuacionesBuscadas = new ArrayList<>();
                    for (ActuacionParticular a: actuacionParticularArrayListFiltrada) {
                        if( (a.getNombre_profesor().toLowerCase().contains(constraint.toString().toLowerCase())) || (new SimpleDateFormat("dd/MM/yyyy").format(a.getFecha())).contains(constraint.toString().toLowerCase()) ){
                            actuacionesBuscadas.add(a);
                        }
                    }
                    filterResults.count = actuacionesBuscadas.size();
                    filterResults.values= actuacionesBuscadas;
                }
                return filterResults;
            }

            // aqui publicamos los resultados que hemos procesado anteriormente a la lista de actuaciones original, notificamos los cambios al adapter
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                actuacionParticularArrayList=(ArrayList<ActuacionParticular>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
    // esta es el interface que nos abilita para anadir un OnClick a los elementos del Recycler View
    // pasando como argumento el objeto actuacion particular
    public interface ActuacionInterface{
        void OnClickActuacion(ActuacionParticular actuacionParticular);
    }


}
