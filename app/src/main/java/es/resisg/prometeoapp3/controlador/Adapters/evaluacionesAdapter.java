package es.resisg.prometeoapp3.controlador.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.modelo.clases.Asignatura;
import es.resisg.prometeoapp3.modelo.clases.Evaluacion;

public class evaluacionesAdapter extends RecyclerView.Adapter<evaluacionesAdapter.evaluacionesViewHolder>{

    //atributos

    private EvaluacionInterface evaluacionSeleccionada;
    private ArrayList<Evaluacion> evaluacionArrayList;
    //Constructor
    public evaluacionesAdapter(ArrayList<Evaluacion> evaluacionArrayList,EvaluacionInterface evaluacionSeleccionada) {
        this.evaluacionSeleccionada= evaluacionSeleccionada;
        this.evaluacionArrayList = evaluacionArrayList;
    }

    @NonNull
    @Override
    //aqui es donde se infla el layout con los items de evaluacion
    public evaluacionesAdapter.evaluacionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.evaluacion_item_layout,parent,false);
        return new evaluacionesAdapter.evaluacionesViewHolder(view);
    }

    @Override
    //aqui es donde se asignan los valores a las vistas de cada Item
    //dependiendo de su posicion en el recyclerView
    public void onBindViewHolder(@NonNull evaluacionesAdapter.evaluacionesViewHolder holder, int position) {
        Evaluacion evaluacion = evaluacionArrayList.get(position);
        holder.txtViewTituloEvaluacion.setText(evaluacion.getEvaluacion());
    }

    @Override
    public int getItemCount() {
        return evaluacionArrayList.size();
    }

    public class evaluacionesViewHolder extends RecyclerView.ViewHolder{
        private TextView txtViewTituloEvaluacion;
        public evaluacionesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewTituloEvaluacion=itemView.findViewById(R.id.txtViewTituloEvaluacion_evaluacionItemLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Asignatura> asignaturas =evaluacionArrayList.get(getBindingAdapterPosition()).getAsignaturas();
                    String nombreEvaluacion = evaluacionArrayList.get(getBindingAdapterPosition()).getEvaluacion();
                    if(asignaturas.size()!=0){
                        evaluacionSeleccionada.OnClickEvaluacion(asignaturas,nombreEvaluacion);
                    }
                }
            });
        }
    }

    public interface EvaluacionInterface{
        void OnClickEvaluacion(ArrayList<Asignatura> asignaturas, String nombreevaluacion);
    }
}
