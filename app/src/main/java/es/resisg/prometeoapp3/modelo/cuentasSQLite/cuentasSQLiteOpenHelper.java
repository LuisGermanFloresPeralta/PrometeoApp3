package es.resisg.prometeoapp3.modelo.cuentasSQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class cuentasSQLiteOpenHelper extends SQLiteOpenHelper {

    public cuentasSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase prometeoAppCuentasDB) {
        prometeoAppCuentasDB.execSQL("create table cuentas(usuario text primary key,contrasena text,nombre text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
