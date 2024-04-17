package es.resisg.prometeoapp3.controlador.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.modelo.clases.Asignatura;

public class AsignaturasAdapter extends RecyclerView.Adapter<AsignaturasAdapter.asignaturasViewHolder> {

    private ArrayList<Asignatura> asignaturaArrayList;
    public AsignaturasAdapter(ArrayList<Asignatura> asignaturaArrayList) {
        this.asignaturaArrayList = asignaturaArrayList;
    }

    @NonNull
    @Override
    public asignaturasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.asignatura_item_layout,parent,false);
        return new AsignaturasAdapter.asignaturasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull asignaturasViewHolder holder, int position) {
        Asignatura asignatura = asignaturaArrayList.get(position);
        holder.txtViewTituloAsignatura.setText("▸ "+asignatura.getAsignatura());

        holder.layoutExpandibleAsignatura.setVisibility(asignatura.isExpandible() ? View.VISIBLE : View.GONE);
        holder.imgBtnExpandirAsignatura.setImageResource(asignatura.isExpandible() ? R.drawable.ic_arrowup : R.drawable.ic_arrowdown);

        holder.linearLayoutAsignatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asignatura.setExpandible(!asignatura.isExpandible());
                notifyItemChanged(holder.getBindingAdapterPosition());
            }
        });

        actividadesAdapter actividadesAdapter = new actividadesAdapter(asignatura.getActividades());
        holder.recyclerViewActividades.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerViewActividades.setHasFixedSize(true);
        holder.recyclerViewActividades.setAdapter(actividadesAdapter);
    }

    @Override
    public int getItemCount() {
        return asignaturaArrayList.size();
    }


    public class asignaturasViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayoutAsignatura;
        private TextView txtViewTituloAsignatura;
        private ImageView imgBtnExpandirAsignatura;
        private RelativeLayout layoutExpandibleAsignatura;
        private RecyclerView recyclerViewActividades;
        public asignaturasViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayoutAsignatura = itemView.findViewById(R.id.linearLayoutAsignaturaItemLayout);
            txtViewTituloAsignatura=itemView.findViewById(R.id.txtViewTituloAsignatura_aignaturaItemLayout);
            imgBtnExpandirAsignatura=itemView.findViewById(R.id.imgViewExpadirAsignaturaItemLayout);
            layoutExpandibleAsignatura=itemView.findViewById(R.id.layoutExpandibleAsignaturaItemLayout);
            recyclerViewActividades=itemView.findViewById(R.id.recyclerViewActividadAsignaturaItemLayout);
        }
    }
}