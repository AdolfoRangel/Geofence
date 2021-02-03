package com.rangel.geofencing.utils;

public class Utils {

    //Constantes campos tabla Ubicacion
    public static final String DB_NOMBRE = "db_ubicacion";
    public static final String TABLA_UBICACION = "ubicacion";
    public static final String TABLA_GEOFONCE = "geofonce";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_LATITUD = "latitud";
    public static final String CAMPO_LONGITUD = "longitud";
    public static final String CAMPO_FECHA= "fecha";

    public static final String CREAR_TABLA_UBICACION =
            "CREATE TABLE "+TABLA_UBICACION+" ("+CAMPO_ID+" INTEGER, "+ CAMPO_LATITUD +" DOUBLE, " + CAMPO_LONGITUD +" DOUBLE, " +CAMPO_FECHA+" TEXT)";
    public static final String CREAR_TABLA_MARCADOR_GEOFONCE =
            "CREATE TABLE "+TABLA_GEOFONCE+" ("+CAMPO_ID+" INTEGER, "+ CAMPO_LATITUD +" DOUBLE, " + CAMPO_LONGITUD +" DOUBLE) " ;

}
