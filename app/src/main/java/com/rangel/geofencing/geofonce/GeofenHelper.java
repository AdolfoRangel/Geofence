package com.rangel.geofencing.geofonce;

import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.model.LatLng;

public class GeofenHelper extends ContextWrapper {
    private static final String TAG = "GeofenHelper";
    PendingIntent pendingIntent;

    public GeofenHelper(Context base) {
        super(base);
    }

    public GeofencingRequest getGeofecingRequest(Geofence geofence) {
        return new GeofencingRequest.Builder()
                .addGeofence(geofence)
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .build();
    }

    public Geofence getGeofence(String Id, LatLng latLng, float radius, int transitionTypes) {
        return new Geofence.Builder()
                .setCircularRegion(latLng.latitude,latLng.longitude,radius)
                .setRequestId(Id)
                .setTransitionTypes(transitionTypes)
                .setLoiteringDelay(500)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();

    }

    public PendingIntent getPendingIntent() {
        Log.d(TAG, "getPendingIntent: entra");
        if (pendingIntent != null) {
            Log.d(TAG, "getPendingIntent: nno es null y lo regresa");
            return pendingIntent;
        }
        Log.d(TAG, "getPendingIntent: sige pero ya valido que no sea null, crear un nuevo intent");
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,2607,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d(TAG, "getPendingIntent: regresa el intent creado");
        return pendingIntent;
    }
}
