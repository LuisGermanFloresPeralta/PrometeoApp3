package es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos.recyclerViewAdapters;

import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos.recyclerViewInterface.actuacionesInterface;
import es.resisg.prometeoapp3.clases.ActuacionParticular;

public class actuacionesAdapter extends RecyclerView.Adapter<actuacionesAdapter.actuacionesViewHodler> {
    //atributos
    private final actuacionesInterface actuacionesInterface;
    private ArrayList<ActuacionParticular> actuacionParticularArrayList;

    //Constructor
    public actuacionesAdapter(es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos.recyclerViewInterface.actuacionesInterface actuacionesInterface, ArrayList<ActuacionParticular> actuacionParticularArrayList) {
        this.actuacionesInterface = actuacionesInterface;
        this.actuacionParticularArrayList = actuacionParticularArrayList;
    }

    @NonNull
    @Override
    //aqui es donde se infla el layout con los items
    public actuacionesAdapter.actuacionesViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actuacion_item_layout,parent,false);
        return new actuacionesAdapter.actuacionesViewHodler(view,actuacionesInterface);
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
    //aqui relacionamos los elementos de las vistas
    //como si fuera en un OnCreate de un MainActivity
    public static class actuacionesViewHodler extends RecyclerView.ViewHolder{
        TextView textView1,textView2,textView3,textView4;
        SearchView shView;
        public actuacionesViewHodler(@NonNull View itemView, actuacionesInterface actuacionesInterface) {
            super(itemView);
            textView1=itemView.findViewById(R.id.txtViewNombreProfesorItem);
            textView2=itemView.findViewById(R.id.txtViewOtrasInformacionesItem);
            textView3=itemView.findViewById(R.id.txtViewFechaHoraItem);
            textView4=itemView.findViewById(R.id.txtViewComentarioItem);
            shView=itemView.findViewById(R.id.shViewActuacionesFragment);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(actuacionesInterface != null){
                        int pos = getAdapterPosition();
                        if(pos!= RecyclerView.NO_POSITION){
                            actuacionesInterface.OnItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
