package es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.clases.Evaluacion;
import es.resisg.prometeoapp3.controlador.Adapters.evaluacionesAdapter;
import es.resisg.prometeoapp3.modelo.GestionSesion;
import es.resisg.prometeoapp3.modelo.conexionHTTP.peticiones;

public class NotasFragment extends Fragment {
    public NotasFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private RecyclerView recyclerViewEvaluaciones;
    private TextView txtPruevaJson;
    private ArrayList<Evaluacion> evaluacionArrayList = new ArrayList<>();
    private evaluacionesAdapter evaluacionesAdapter;
    private GestionSesion gestionSesion;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Infalmos el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_notas, container, false);

        //Recuperamos datos de la sesion(usuario y contraseña)
        gestionSesion = new GestionSesion(getContext());

        // llamamos a conexion.tareas.conseguirEvaluaciones pasando la URL,Usuario,Contraseña y conseguimos un ArrayList<actuacionParticular
        evaluacionArrayList = new peticiones("http://192.168.1.194/WEB/APP/NotasLuisPrueva.php", String.valueOf(gestionSesion.getUsuario()), gestionSesion.getContrasena()).conseguirEvaluaciones();

        //relacionamos el Recycler view con la parte gráfica de la aplicacion
        recyclerViewEvaluaciones = view.findViewById(R.id.recyclerViewEvaluaciones);
        evaluacionesAdapter = new evaluacionesAdapter(evaluacionArrayList);
        recyclerViewEvaluaciones.setAdapter(evaluacionesAdapter);
        recyclerViewEvaluaciones.setHasFixedSize(true);
        recyclerViewEvaluaciones.setLayoutManager(new LinearLayoutManager(getActivity()));




        // Devolvemos la vista
        return view;
    }
}