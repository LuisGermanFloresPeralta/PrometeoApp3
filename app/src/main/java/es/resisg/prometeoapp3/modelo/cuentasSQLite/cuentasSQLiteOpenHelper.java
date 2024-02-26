package es.resisg.prometeoapp3.modelo.cuentasSQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class cuentasSQLiteOpenHelper extends SQLiteOpenHelper {

    private Context context;
    private  static final String DATABASE_NOMBRE ="cuentas.db";
    private  static final int DATABASE_VERSION=1;
    private  static final String TABLE_NOMBRE="mis_cuentas";
    private  static final String COLUMN_ID="id";
    private  static final String COLUMN_USUARIO="usuario";
    private  static final String COLUMN_CONTRASENA="contrasena";
    private  static final String COLUMN_NOMBRE="nombre";

    public cuentasSQLiteOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query ="CREATE TABLE "+ TABLE_NOMBRE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_USUARIO + " TEXT, "+
                COLUMN_CONTRASENA + " TEXT, " +
                COLUMN_NOMBRE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NOMBRE);
        onCreate(db);
    }

    void anadirCuenta(String usuario,String contrasena,String nombre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USUARIO,usuario);
        cv.put(COLUMN_CONTRASENA,contrasena);
        cv.put(COLUMN_NOMBRE,nombre);
        long result = db.insert(TABLE_NOMBRE,null,cv);
        if(result ==-1){
            Toast.makeText(context, "Fallo al añadir la cuenta", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Cuenta Añadida Correctamete", Toast.LENGTH_SHORT).show();
        }
    }
}
