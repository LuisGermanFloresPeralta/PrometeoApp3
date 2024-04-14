package es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos.recyclerViewAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.clases.Actividad;

public class actividadesAdapter extends RecyclerView.Adapter<actividadesAdapter.actividadesViewHolder> {

    private List<Actividad> actividadesArraylList;
    public actividadesAdapter(List<Actividad> actividadesArraylList) {
        this.actividadesArraylList = actividadesArraylList;
    }

    @NonNull
    @Override
    public actividadesAdapter.actividadesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actividad_item_layout,parent,false);
        return new actividadesAdapter.actividadesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull actividadesAdapter.actividadesViewHolder holder, int position) {
        Actividad actividad = actividadesArraylList.get(position);
        holder.txtViewActividad.setText("Actividad: "+actividad.getActividad());
        holder.txtViewNombreProfesor.setText("Docente: "+actividad.getNombre_Profesor());
        holder.txtViewFechaActividad.setText("Fecha "+actividad.getFecha());
        holder.txtViewNotaActividad.setText("Nota: "+actividad.getNota());
    }

    @Override
    public int getItemCount() {
        return actividadesArraylList.size();
    }

    public class actividadesViewHolder extends RecyclerView.ViewHolder{
        private TextView txtViewActividad,txtViewNombreProfesor,txtViewFechaActividad,txtViewNotaActividad;
        public actividadesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewActividad = itemView.findViewById(R.id.tituloActividad_ActividadItemLayout);
            txtViewNombreProfesor = itemView.findViewById(R.id.nombreProfesor_ActividadItemLayout);
            txtViewFechaActividad = itemView.findViewById(R.id.fechaActividad_ActividadItemLayout);
            txtViewNotaActividad = itemView.findViewById(R.id.notaActividad_ActividadItemLayout);
        }
    }
}
