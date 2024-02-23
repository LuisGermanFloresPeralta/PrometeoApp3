package es.resisg.prometeoapp3.controlador.CuentasActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.ConectadoActivity;
import es.resisg.prometeoapp3.clases.Cuenta;
import es.resisg.prometeoapp3.controlador.MainActivity;
import es.resisg.prometeoapp3.modelo.GestionSesion;

public class CuentasActivity extends AppCompatActivity implements cuentasInterface {

    //
    private RecyclerView rcvCuentas;
    private CuentasActivityAdapter cuentasActivityAdapter;
    private ArrayList<Cuenta> cuentaArrayList= new ArrayList<>();
    GestionSesion gestionSesion;
    TextView txtViewNombreSesion,txtViewLogoInicialSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas);
        init();
    }
    public void init(){
        //conseguir sesion
        gestionSesion = new GestionSesion(this);

        //relacionamos las variables a la parte gráfica de la aplicacion
        txtViewNombreSesion = (TextView) findViewById(R.id.txtViewNombreSesion);
        txtViewLogoInicialSesion =(TextView)findViewById(R.id.txtViewLogoInicialCuentas);
        txtViewNombreSesion.setText(gestionSesion.getNombre());
        txtViewLogoInicialSesion.setText(obtenerInicial(gestionSesion.getNombre()));

        //conseguir ArrayList de cuentas
        cuentaArrayList = conseguirCuentas();
        rcvCuentas= (RecyclerView) findViewById(R.id.rcvActivityCuentas);
        cuentasActivityAdapter = new CuentasActivityAdapter(this,cuentaArrayList);
        rcvCuentas.setAdapter(cuentasActivityAdapter);
        rcvCuentas.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void OnItemClick(int posicion) {
        String usurioCuenta=cuentaArrayList.get(posicion).getUsuario();
        String contrasenaCuenta=cuentaArrayList.get(posicion).getContrasena();
        String nombreCuenta=cuentaArrayList.get(posicion).getNombre();
        gestionSesion.iniciarSesion(usurioCuenta,contrasenaCuenta,nombreCuenta);
        irActivityConectado();
    }

    @Override
    public void OnLongItemClick(int posicion, View v) {
        // Manejar la selección de elementos del menú
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.configuracion_popup_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.itemEditarCuentaPopupMenu){
                    return true;
                } else if (item.getItemId()==R.id.itemElminarCuentaPopupMenu) {
                    cuentaArrayList.remove(posicion);
                    cuentasActivityAdapter.notifyItemRemoved(posicion);
                    return  true;
                }else{
                    return false;
                }
            }
        });
        popupMenu.show();

    }
    public ArrayList<Cuenta> conseguirCuentas(){
        ArrayList<Cuenta> cuentas = new ArrayList<>();
        cuentas.add(new Cuenta("1244884","1244884","Luis German"));
        cuentas.add(new Cuenta("1151863","1151863","Elias"));
        return cuentas;
    }
    public String obtenerInicial(String nombre) {
        String inicial= String.valueOf(nombre.charAt(0));
        return inicial;
    }
    public void volverAtras(View view) {
        irActivityConectado();
    }
    public void CerrarSesion(View view){
        gestionSesion.cerrarSesion();
        irMainActivity();
    }
    public void irActivityConectado(){
        Intent intent = new Intent(this, ConectadoActivity.class);
        finish();
        startActivity(intent);
    }
    public void irMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }


}