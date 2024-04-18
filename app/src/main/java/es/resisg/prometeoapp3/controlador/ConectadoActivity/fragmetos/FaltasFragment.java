package es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos;

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
import es.resisg.prometeoapp3.controlador.Adapters.faltasAdapter;
import es.resisg.prometeoapp3.modelo.GestionSesion;
import es.resisg.prometeoapp3.modelo.clases.Falta;
import es.resisg.prometeoapp3.modelo.conexionHTTP.peticiones;

public class FaltasFragment extends Fragment {
    public FaltasFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //--------------------------------------------------------------------------------------------------------------------------
    //declarar variables

    private SearchView shViewFaltas;
    private RecyclerView rvFaltas;
    private faltasAdapter faltasAdapter;
    private ArrayList<Falta> faltasArrayList = new ArrayList<>();
    private GestionSesion gestionSesion;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout de este fragment
        View view = inflater.inflate(R.layout.fragment_faltas, container, false);

        //Recuperamos datos de la sesion(usuario y contraseña)
        gestionSesion = new GestionSesion(getContext());

        // llamamos a conexion.tareas.conseguirActuacionesParticulares pasando la URL,Usuario,Contraseña y conseguimos un ArrayList<actuacionParticular
        faltasArrayList = new peticiones("http://ieslassalinas.org/APP/appFaltas2.php", String.valueOf(gestionSesion.getUsuario()), gestionSesion.getContrasena()).conseguirFaltas();

        // Inicializar el RecyclerView y su Adapter
        rvFaltas = view.findViewById(R.id.recyclerViewFaltas);
        faltasAdapter = new faltasAdapter(faltasArrayList);

        // Configurar el RecyclerView
        rvFaltas.setAdapter(faltasAdapter);
        rvFaltas.setHasFixedSize(true);
        rvFaltas.setLayoutManager(new LinearLayoutManager(getContext()));

        //-------------------------------------------------------------------------------
        //inicializo el SearchView y añadimos un onQueryTextChange para ejecutar esta accion por cada caracter pulsado
        shViewFaltas = view.findViewById(R.id.shViewFaltasFragment);
        shViewFaltas.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                faltasAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return view;
    }
}