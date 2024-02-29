package es.resisg.prometeoapp3.modelo.cuentasSQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import es.resisg.prometeoapp3.clases.Cuenta;
import es.resisg.prometeoapp3.controlador.MainActivity;

public class cuentasSQLiteOpenHelper extends SQLiteOpenHelper {

    private Context context;
    private  static final String DATABASE_NAME ="cuentas.db";
    private  static final int DATABASE_VERSION=1;
    private  static final String TABLE_NAME="mis_cuentas";
    private  static final String COLUMN_USUARIO="usuario";
    private  static final String COLUMN_CONTRASENA="contrasena";
    private  static final String COLUMN_NOMBRE="nombre";

    public cuentasSQLiteOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query ="CREATE TABLE  "+ TABLE_NAME +
                " ( " + COLUMN_USUARIO + " INTEGER PRIMARY KEY , "+
                COLUMN_CONTRASENA + " TEXT, " +
                COLUMN_NOMBRE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    //--------------------------------------------------------------------------
    public void anadirCuenta(int usuario,String contrasena,String nombre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USUARIO,usuario);
        cv.put(COLUMN_CONTRASENA,contrasena);
        cv.put(COLUMN_NOMBRE,nombre);
        long result = db.insert(TABLE_NAME,null,cv);
        if(result ==-1){
            Toast.makeText(context, "Fallo al añadir la cuenta", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Cuenta Añadida Correctamete", Toast.LENGTH_SHORT).show();
        }
    }
    public ArrayList<Cuenta> obtenerTodasLasCuentas() {
        ArrayList<Cuenta> cuentasList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int usuarioIndex = cursor.getColumnIndex(COLUMN_USUARIO);
                int contrasenaIndex = cursor.getColumnIndex(COLUMN_CONTRASENA);
                int nombreIndex = cursor.getColumnIndex(COLUMN_NOMBRE);

                int usuario = cursor.getInt(usuarioIndex);
                String contrasena = cursor.getString(contrasenaIndex);
                String nombre = cursor.getString(nombreIndex);

                Cuenta cuenta = new Cuenta(usuario, contrasena, nombre);
                cuentasList.add(cuenta);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return cuentasList;
    }
    public void editarCuenta(int usuario ,String nuevaContrasena, String nuevoNombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CONTRASENA, nuevaContrasena);
        cv.put(COLUMN_NOMBRE, nuevoNombre);

        int rowsAffected = db.update(TABLE_NAME, cv, COLUMN_USUARIO + " = ?", new String[]{String.valueOf(usuario)});

        if (rowsAffected > 0) {
            Toast.makeText(context, "Cuenta editada correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al editar la cuenta", Toast.LENGTH_SHORT).show();
        }
    }
    public void borrarCuenta(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int rowsAffected = db.delete(TABLE_NAME, COLUMN_USUARIO + " = ?", new String[]{String.valueOf(id)});

        if (rowsAffected > 0) {
            Toast.makeText(context, "Cuenta eliminada correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show();
        }
    }

}
