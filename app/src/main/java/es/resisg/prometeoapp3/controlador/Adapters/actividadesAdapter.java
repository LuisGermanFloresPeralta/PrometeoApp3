package es.resisg.prometeoapp3.controlador.Adapters;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
        holder.txtViewActividad.setText(actividad.getActividad()+":");
        holder.txtViewNombreProfesorFecha.setText("("+actividad.getNombre_Profesor()+")"+actividad.getFecha());

        String nota = actividad.getNota();
        try {

            double numero = Double.parseDouble(nota);
            // Si es un número
            if (numero > 5) {
                // Si es mayor que 5, pinta el texto de verde
                SpannableString spannableString = new SpannableString(nota);
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.GREEN);
                spannableString.setSpan(colorSpan, 0, nota.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.txtViewNotaActividad.setText(spannableString);
            } else if (numero <= 5) {
                // Si es menor o igual que 5, pinta el texto de rojo
                SpannableString spannableString = new SpannableString(nota);
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
                spannableString.setSpan(colorSpan, 0, nota.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.txtViewNotaActividad.setText(spannableString);
            }
        } catch (NumberFormatException e) {
            // Si no se puede convertir a número, simplemente muestra el texto sin modificar
            holder.txtViewNotaActividad.setText(nota);
        }
    }

    @Override
    public int getItemCount() {
        return actividadesArraylList.size();
    }

    public class actividadesViewHolder extends RecyclerView.ViewHolder{
        private TextView txtViewActividad,txtViewNombreProfesorFecha,txtViewNotaActividad;
        public actividadesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewActividad = itemView.findViewById(R.id.Actividad_ActividadItemLayout);
            txtViewNombreProfesorFecha = itemView.findViewById(R.id.nombreProfesorYFecha_ActividadItemLayout);
            txtViewNotaActividad = itemView.findViewById(R.id.notaActividad_ActividadItemLayout);

        }
    }
}
