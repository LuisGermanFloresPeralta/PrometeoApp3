package es.resisg.prometeoapp3.controlador.CuentasActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.modelo.clases.Cuenta;

public class CuentasActivityAdapter extends RecyclerView.Adapter<CuentasActivityAdapter.cuentasViewHolder> {

    private final cuentasInterface cuentasInterface;
    private ArrayList<Cuenta> cuentaArrayList;

    //


    public CuentasActivityAdapter(es.resisg.prometeoapp3.controlador.CuentasActivity.cuentasInterface cuentasInterface, ArrayList<Cuenta> cuentaArrayList) {
        this.cuentasInterface = cuentasInterface;
        this.cuentaArrayList = cuentaArrayList;
    }
    @NonNull
    @Override
    public cuentasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.cuenta_item_layout,parent,false);
        return new CuentasActivityAdapter.cuentasViewHolder(view,cuentasInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CuentasActivityAdapter.cuentasViewHolder holder, int position) {
        Cuenta cuenta = cuentaArrayList.get(position);
        holder.textView1.setText(cuenta.getNombre());
    }

    @Override
    public int getItemCount() {
        return cuentaArrayList.size();
    }

    public  static class cuentasViewHolder extends RecyclerView.ViewHolder{

        TextView textView1;
        public cuentasViewHolder(@NonNull View itemView, cuentasInterface cuentasInterface) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.txtViewNombre_cuenta);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cuentasInterface != null){
                        int pos = getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            cuentasInterface.OnItemClick(pos);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (cuentasInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            cuentasInterface.OnLongItemClick(pos,v);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }
}
