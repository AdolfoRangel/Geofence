package com.rangel.geofencing.db;

public class MarcadorGeofence {

    private Integer Id;
    private Double Latitud;
    private Double Longitud;

    public MarcadorGeofence(Integer id, Double latitud, Double longitud) {
        Id = id;
        Latitud = latitud;
        Longitud = longitud;
    }

    public MarcadorGeofence() {
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
}
