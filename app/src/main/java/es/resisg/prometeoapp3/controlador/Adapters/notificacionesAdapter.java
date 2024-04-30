package es.resisg.prometeoapp3.controlador.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.resisg.prometeoapp3.R;

public class notificacionesAdapter extends RecyclerView.Adapter<notificacionesAdapter.notificacionesViewHolder> {

    //Atributos de la calse
    private ArrayList<String> notificacionesStringArrayList;

    //constructor
    public notificacionesAdapter(ArrayList<String> notificacionesStringArrayList) {
        this.notificacionesStringArrayList = notificacionesStringArrayList;
    }

    @NonNull
    @Override
    public notificacionesAdapter.notificacionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notificacion_item_layout,parent,false);
        return new notificacionesAdapter.notificacionesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notificacionesAdapter.notificacionesViewHolder holder, int position) {
        String notificacion = notificacionesStringArrayList.get(position);
        holder.txtViewTextoNotificacacion.setText(notificacion);
    }

    @Override
    public int getItemCount() {
        return notificacionesStringArrayList.size();
    }

    public class notificacionesViewHolder extends RecyclerView.ViewHolder{

        private TextView txtViewTextoNotificacacion;

        public notificacionesViewHolder(@NonNull View itemView) {
            super(itemView);

            txtViewTextoNotificacacion = itemView.findViewById(R.id.txtViewTexto_notificacionItemLayout);
        }
    }
}
