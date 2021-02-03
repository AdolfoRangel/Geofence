package com.rangel.geofencing.db;

import com.google.android.gms.maps.model.LatLng;

public class Ubicacion {
    private Integer Id;
    private Double Latitud;
    private Double Longitud;
    private String time;

    public Ubicacion(Integer id, Double latitud, Double longitud, String time) {
        Id = id;
        Latitud = latitud;
        Longitud = longitud;
        this.time = time;
    }

    public Ubicacion() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Double getLatitud() {
        return Latitud;
    }

    public void setLatitud(Double latitud) {
        Latitud = latitud;
    }

    public Double getLongitud() {
        return Longitud;
    }

    public void setLongitud(Double longitud) {
        Longitud = longitud;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
