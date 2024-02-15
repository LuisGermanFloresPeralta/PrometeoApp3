package es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos.recyclerViewAdapters.actuacionesAdapter;
import es.resisg.prometeoapp3.modelo.ActuacionParticular;
import es.resisg.prometeoapp3.modelo.conexion.datos;

public class ActuacionesFragment extends Fragment {
    public ActuacionesFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //--------------------------------------------------------------------------------------------------------------------------
    //declarar variables
    private RecyclerView recyclerViewActuacionesParticulares;
    private actuacionesAdapter actuacionesAdapter;
    private ArrayList<ActuacionParticular> actuacionParticularArrayList= new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout de este fragment
        View view =inflater.inflate(R.layout.fragment_actuaciones_particulares, container, false);

        //Recuperamos el usuario y contraseña pasado por bundle desde ConectadoActivity2
        Bundle bundle = getArguments();
        if (bundle != null) {
            String usuario = bundle.getString("usuario");
            String contrasena = bundle.getString("contrasena");
            // llamamos a conexion.tareas.conseguirActuacionesParticulares pasando la URL,Usuario,Contraseña y conseguimos un ArrayList<actuacionParticular
            actuacionParticularArrayList = new datos("http://www.ieslassalinas.org/APP/appActuaciones2.php", usuario, contrasena).conseugirActuaciones();
            //relacionamos el Recycler view con la parte gráfica de la aplicacion
            recyclerViewActuacionesParticulares = view.findViewById(R.id.recyclerViewActuacionesParticulares);
            actuacionesAdapter = new actuacionesAdapter(actuacionParticularArrayList);
            recyclerViewActuacionesParticulares.setAdapter(actuacionesAdapter);
            recyclerViewActuacionesParticulares.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        return  view;
    }
}