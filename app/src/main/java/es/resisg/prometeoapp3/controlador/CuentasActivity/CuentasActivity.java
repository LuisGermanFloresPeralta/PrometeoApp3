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
import java.util.Iterator;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.controlador.AnadirCuentaActivity;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.ConectadoActivity;
import es.resisg.prometeoapp3.modelo.clases.Cuenta;
import es.resisg.prometeoapp3.controlador.EditarCuentaActivity;
import es.resisg.prometeoapp3.controlador.MainActivity;
import es.resisg.prometeoapp3.modelo.GestionSesion;
import es.resisg.prometeoapp3.modelo.cuentasSQLite.cuentasSQLiteOpenHelper;

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
        txtViewLogoInicialSesion.setText(String.valueOf(gestionSesion.getNombre().charAt(0)));

        //conseguir ArrayList de cuentas y crear la lista de cuentas en el recycler view
        cuentaArrayList = quitarSesionActualDelArray(new cuentasSQLiteOpenHelper(CuentasActivity.this).obtenerTodasLasCuentas());
        rcvCuentas= (RecyclerView) findViewById(R.id.rcvActivityCuentas);
        cuentasActivityAdapter = new CuentasActivityAdapter(this,cuentaArrayList);
        rcvCuentas.setAdapter(cuentasActivityAdapter);
        rcvCuentas.setLayoutManager(new LinearLayoutManager(this));

         // Reemplaza R.color.colorPrimaryDark con el color que desees
    }

    @Override
    public void OnItemClick(int posicion) {
        int usurioCuenta=cuentaArrayList.get(posicion).getUsuario();
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
                Cuenta c = cuentaArrayList.get(posicion);
                if(item.getItemId()==R.id.itemEditarCuentaPopupMenu){
                    irEditarCuenta(c.getUsuario(),c.getContrasena(),c.getNombre());
                    return true;
                } else if (item.getItemId()==R.id.itemElminarCuentaPopupMenu) {
                    new cuentasSQLiteOpenHelper(CuentasActivity.this).borrarCuenta(c.getUsuario());
                    cuentaArrayList.remove(posicion);
                    cuentasActivityAdapter.notifyItemRemoved(posicion);// Reemplaza con el ID de la cuenta que quieres eliminar
                    return  true;
                }else{
                    return false;
                }
            }
        });
        popupMenu.show();
    }
    public void volverAtras(View view) {
        irActivityConectado();
    }
    public void CerrarSesion(View view){
        cuentasSQLiteOpenHelper cuentasSQLiteOpenHelper = new cuentasSQLiteOpenHelper(this);
        cuentasSQLiteOpenHelper.borrarCuenta(Integer.valueOf(gestionSesion.getUsuario()));
        gestionSesion.cerrarSesion();
        irMainActivity();
    }
    public void irActivityConectado(){
        Intent i = new Intent(this,ConectadoActivity.class);
        finish();
        startActivity(i);
    }
    public void irMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }
    //metodo para botno anadirCuenta en CuentasActivity
    public void irAnadirCuenta(View v){
        Intent intent = new Intent(this, AnadirCuentaActivity.class);
        finish();
        startActivity(intent);
    }
    //metodo para botonEditar en CuentasActivity
    public void irEditar(View v){
        irEditarCuenta(gestionSesion.getUsuario(),gestionSesion.getContrasena(),gestionSesion.getNombre());
    }
    public void irEditarCuenta(int usuario,String contrasena,String nombre){
        Intent intent = new Intent(this, EditarCuentaActivity.class);
        intent.putExtra("usuario_PUT_EXTRA",usuario);
        intent.putExtra("contrasena_PUT_EXTRA",contrasena);
        intent.putExtra("nombre_PUT_EXTRA",nombre);
        finish();
        startActivity(intent);
    }
    public ArrayList<Cuenta> quitarSesionActualDelArray(ArrayList<Cuenta> cuentas) {
        //creamos un objeto 'cuenta' con la sesion actual
        Cuenta cuentaAsacar = new Cuenta(gestionSesion.getUsuario(),gestionSesion.getContrasena(),gestionSesion.getNombre());

        //buscamos en el Array la cuenta
        Iterator<Cuenta> iterator = cuentas.iterator();
        while (iterator.hasNext()) {
            Cuenta c = iterator.next();
            if (c.equals(cuentaAsacar)) {
                iterator.remove();
                break;
            }
        }
        return cuentas;
    }
}