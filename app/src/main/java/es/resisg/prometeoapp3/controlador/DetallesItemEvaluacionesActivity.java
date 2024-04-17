package es.resisg.prometeoapp3.controlador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.clases.Asignatura;
import es.resisg.prometeoapp3.controlador.Adapters.AsignaturasAdapter;
import es.resisg.prometeoapp3.controlador.Adapters.evaluacionesAdapter;

public class DetallesItemEvaluacionesActivity extends AppCompatActivity {


    private RecyclerView rvAsignaturas;
    private AsignaturasAdapter asignaturasAdapter;
    private ArrayList<Asignatura> asignaturaArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_item_evaluaciones);


        // Recoger los Extras del Intent
        Intent intent = getIntent();
        asignaturaArrayList = (ArrayList<Asignatura>) intent.getSerializableExtra("Asignaturas");

        // Inicializar el RecyclerView y su Adapter
        rvAsignaturas = findViewById(R.id.recyclerViewAsignaturasDetallesEvaluaciones);
        asignaturasAdapter = new AsignaturasAdapter(asignaturaArrayList);

        // Configurar el RecyclerView
        rvAsignaturas.setAdapter(asignaturasAdapter);
        rvAsignaturas.setHasFixedSize(true);
        rvAsignaturas.setLayoutManager(new LinearLayoutManager(this));
    }

    public void volverAEvaluacines(View v){
        onBackPressed();
        finish();
    }

    /*
    * Intent i = new Intent(getActivity(), DetallesItemActuacionesActivity.class);
        i.putExtra("Nombre_profesor",actuacionParticular.getNombre_profesor());
        i.putExtra("Fecha",new SimpleDateFormat("dd/MM/yyyy\nHH:mm:ss").format(actuacionParticular.getFecha()));
        i.putExtra("Tipo_actuacion",actuacionParticular.getTipo_actuacion());
        i.putExtra("Comentario",actuacionParticular.getComentario());
        startActivity(i);*/
}