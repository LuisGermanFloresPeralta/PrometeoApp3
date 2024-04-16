package es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.controlador.Adapters.actuacionesAdapter;
import es.resisg.prometeoapp3.controlador.DetallesItemActuacionesActivity;
import es.resisg.prometeoapp3.clases.ActuacionParticular;
import es.resisg.prometeoapp3.modelo.GestionSesion;
import es.resisg.prometeoapp3.modelo.conexionHTTP.peticiones;

public class ActuacionesFragment extends Fragment implements actuacionesAdapter.ActuacionInterface {
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
    private SearchView shViewActuacionesFragment;
    private actuacionesAdapter actuacionesAdapter;
    private ArrayList<ActuacionParticular> actuacionParticularArrayList= new ArrayList<>();
    private GestionSesion gestionSesion;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout de este fragment
        View view =inflater.inflate(R.layout.fragment_actuaciones_particulares, container, false);

        //Recuperamos datos de la sesion(usuario y contraseña)
        gestionSesion = new GestionSesion(getContext());

        // llamamos a conexion.tareas.conseguirActuacionesParticulares pasando la URL,Usuario,Contraseña y conseguimos un ArrayList<actuacionParticular
        actuacionParticularArrayList = new peticiones("http://192.168.1.194/WEB/APP/appActuaciones.php", String.valueOf(gestionSesion.getUsuario()), gestionSesion.getContrasena()).conseguirActuaciones();

        //relacionamos el Recycler view con la parte gráfica de la aplicacion
        recyclerViewActuacionesParticulares = view.findViewById(R.id.recyclerViewActuacionesParticulares);
        actuacionesAdapter = new actuacionesAdapter(actuacionParticularArrayList,this);
        recyclerViewActuacionesParticulares.setAdapter(actuacionesAdapter);
        recyclerViewActuacionesParticulares.setLayoutManager(new LinearLayoutManager(getActivity()));

        //inicializo el SearchView y añadimos un onQueryTextChange para ejecutar esta accion por cada caracter pulsado
        shViewActuacionesFragment = view.findViewById(R.id.shViewActuacionesFragment);
        shViewActuacionesFragment.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                actuacionesAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return  view;
    }

    // Accion sel onClick anadido a cada item del RecyclerView de ActuacionesParticularesSS
    @Override
    public void OnClickActuacion(ActuacionParticular actuacionParticular) {
        Intent i = new Intent(getActivity(), DetallesItemActuacionesActivity.class);
        i.putExtra("Nombre_profesor",actuacionParticular.getNombre_profesor());
        i.putExtra("Fecha",new SimpleDateFormat("dd/MM/yyyy\nHH:mm:ss").format(actuacionParticular.getFecha()));
        i.putExtra("Tipo_actuacion",actuacionParticular.getTipo_actuacion());
        i.putExtra("Comentario",actuacionParticular.getComentario());
        startActivity(i);
    }
}