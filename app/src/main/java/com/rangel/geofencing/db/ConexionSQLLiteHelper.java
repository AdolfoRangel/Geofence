package com.rangel.geofencing.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.rangel.geofencing.utils.Utils.CREAR_TABLA_MARCADOR_GEOFONCE;
import static com.rangel.geofencing.utils.Utils.CREAR_TABLA_UBICACION;

public class ConexionSQLLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_UBICACION);
        db.execSQL(CREAR_TABLA_MARCADOR_GEOFONCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ubicacion");
        db.execSQL("DROP TABLE IF EXISTS geofonce");
    }
}
