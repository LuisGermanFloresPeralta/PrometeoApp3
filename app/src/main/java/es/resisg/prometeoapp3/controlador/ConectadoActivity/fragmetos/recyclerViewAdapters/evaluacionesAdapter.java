package es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos.recyclerViewAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.clases.Asignatura;
import es.resisg.prometeoapp3.clases.Evaluacion;

public class evaluacionesAdapter extends RecyclerView.Adapter<evaluacionesAdapter.evaluacionesViewHolder>{

    //atributos
    private List<Evaluacion> evaluacionArrayList;
    private List<Asignatura> asignaturasDeEstaEvaluacionArrayList;

    //Constructor
    public evaluacionesAdapter(List<Evaluacion> evaluacionArrayList) {
        this.evaluacionArrayList = evaluacionArrayList;
        this.asignaturasDeEstaEvaluacionArrayList = new ArrayList<>();
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

        holder.layoutExpandibleEvaluacion.setVisibility(evaluacion.isExpandible() ? View.VISIBLE : View.GONE);
        if(evaluacion.isExpandible()){
            holder.imgBtnExpandirEvaluacion.setImageResource(R.drawable.ic_arrowup);
        }else {
            holder.imgBtnExpandirEvaluacion.setImageResource(R.drawable.ic_arrowdown);
        }

        AsignaturasAdapter asignaturasAdapter = new AsignaturasAdapter(asignaturasDeEstaEvaluacionArrayList);
        holder.recyclerViewAsignaturas.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerViewAsignaturas.setHasFixedSize(true);
        holder.recyclerViewAsignaturas.setAdapter(asignaturasAdapter);
        holder.linearLayoutEvaluacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluacion.setExpandible(!evaluacion.isExpandible());
                asignaturasDeEstaEvaluacionArrayList= evaluacion.getAsignaturas();
                notifyItemChanged(holder.getBindingAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return evaluacionArrayList.size();
    }

    public class evaluacionesViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout linearLayoutEvaluacion;
        private TextView txtViewTituloEvaluacion;
        private ImageView imgBtnExpandirEvaluacion;
        private RelativeLayout layoutExpandibleEvaluacion;
        private RecyclerView recyclerViewAsignaturas;


        public evaluacionesViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayoutEvaluacion=itemView.findViewById(R.id.linearLayoutEvaluacionItemLayout);
            txtViewTituloEvaluacion=itemView.findViewById(R.id.txtViewTituloEvaluacion_evaluacionItemLayout);
            imgBtnExpandirEvaluacion=itemView.findViewById(R.id.imgViewExpandirEvaluacionItemLayout);
            layoutExpandibleEvaluacion=itemView.findViewById(R.id.layoutExpandibleEvaluacionItemLayout);
            recyclerViewAsignaturas=itemView.findViewById(R.id.recyclerViewAsiganturaEvaluacionItemLayout);

        }
    }
}
