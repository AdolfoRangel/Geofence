package com.rangel.geofencing.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.rangel.geofencing.databinding.ActivityMainBinding;
import com.rangel.geofencing.db.ConexionSQLLiteHelper;
import com.rangel.geofencing.db.Ubicacion;
import com.rangel.geofencing.geofonce.MapsActivity;
import com.rangel.geofencing.utils.Utils;

import java.util.ArrayList;

import static com.rangel.geofencing.geofonce.MapsActivity.LATITUD;
import static com.rangel.geofencing.geofonce.MapsActivity.LONGITUD;
import static com.rangel.geofencing.utils.Utils.DB_NOMBRE;

public class MainActivity extends AppCompatActivity {
    public final int FINE_LOCATION_ACCESS_REQUEST_CODE = 1001;
    public final int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 10002;
    public final int ACCESS_COARSE_LOCATION_REQUEST_CODE = 10003;
    private String TAG = "MainActivity";
    ActivityMainBinding binding;
    ConexionSQLLiteHelper conn;
    ArrayList<Ubicacion> ubicacionArrayList;
    ArrayList<String> ubicaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        conn = new ConexionSQLLiteHelper(this, DB_NOMBRE, null, 1);

        ubicacionArrayList = new ArrayList<Ubicacion>();
        ubicaciones = new ArrayList<>();

        binding.tvIniciar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            intent.putExtra(LATITUD, "");
            intent.putExtra(LONGITUD, "");
            startActivity(intent);
        });
        enabledUserLocation();
        binding.tvPermisos.setOnClickListener(v -> {
            enabledUserLocation();
        });

        binding.tvAceptar.setOnClickListener(v -> {
            if (binding.etLatitud.getText().toString().equals("")) {
                Toast.makeText(this, "Porfavor introduce una latiud, del punto que quieres que sea del GeoFence", Toast.LENGTH_SHORT).show();
            } else if (binding.etLongitud.getText().toString().equals("")) {
                Toast.makeText(this, "Porfavor introduce una longitud, del punto que quieres que sea del GeoFence", Toast.LENGTH_SHORT).show();
            } else {
                registraGeofenceSql(binding.etLatitud.getText().toString(), binding.etLongitud.getText().toString());
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra(LATITUD, binding.etLatitud.getText().toString());
                intent.putExtra(LONGITUD, binding.etLongitud.getText().toString());
                startActivity(intent);
            }

        });
        consultarUbicaciones();

    }
    private void creaLista(){
        if (ubicaciones.size() > 0) {
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ubicaciones);
            binding.lvUbicaciones.setAdapter(adapter);

            binding.lvUbicaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    Log.d(TAG, "onItemClick: "+ubicacionArrayList.get(position).getLatitud());
                    Log.d(TAG, "onItemClick: "+ubicacionArrayList.get(position).getLongitud());
                    intent.putExtra(LATITUD, String.valueOf(ubicacionArrayList.get(position).getLatitud()));
                    intent.putExtra(LONGITUD, String.valueOf(ubicacionArrayList.get(position).getLongitud()));
                    startActivity(intent);
                }
            });
        }
    }

    private void consultarUbicaciones() {
        ubicaciones = new ArrayList<>();
        SQLiteDatabase db = conn.getReadableDatabase();
        Ubicacion ubicacion = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utils.TABLA_GEOFONCE, null);
        while (cursor.moveToNext()) {
            ubicacion = new Ubicacion();
            ubicacion.setId(cursor.getInt(0));
            ubicacion.setLatitud(cursor.getDouble(1));
            ubicacion.setLongitud(cursor.getDouble(2));
            ubicacionArrayList.add(ubicacion);
            ubicaciones.add("Latitud: " + cursor.getDouble(1) + "\n" + "Longitud: " + cursor.getDouble(2));
        }
        if (ubicaciones.size() > 0) {
            binding.tvList.setText("Lista de puntos Geofence");
            Log.d(TAG, "consultarUbicaciones: "+ubicaciones.size());
        } else {
            binding.tvList.setText("Aun no tienes puntos guardados de Geofence");
        }
        creaLista();
    }

    private void registraGeofenceSql(String latitud, String longitud) {
        ConexionSQLLiteHelper conn = new ConexionSQLLiteHelper(this, "db_ubicacion", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        //String insert = "INSERT INTO " + Utils.TABLA_GEOFONCE + " (" + Utils.CAMPO_ID + "," + Utils.CAMPO_LATITUD + "," + Utils.CAMPO_LONGITUD + ") VALUES (" + "id," + latitud + "," + longitud + ");";
        //db.execSQL(insert);
        ContentValues values = new ContentValues();
        values.put(Utils.CAMPO_LATITUD, latitud);
        values.put(Utils.CAMPO_LONGITUD, longitud);
        Long idResultante = db.insert(Utils.TABLA_UBICACION, Utils.CAMPO_ID, values);
        db.close();
    }

    private void enabledUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Has concedido los pemisos de localizacón ", Toast.LENGTH_SHORT).show();
        } else {
            //ask for permision
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            } else {
                //We do not have the permission..
                Toast.makeText(this, "Fine location access is neccessary for geofences to trigger...", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == BACKGROUND_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                Toast.makeText(this, "You can add geofences...", Toast.LENGTH_SHORT).show();
            } else {
                //We do not have the permission..
                Toast.makeText(this, "Background location access is neccessary for geofences to trigger...", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == ACCESS_COARSE_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                Toast.makeText(this, "You can add geofences 2...", Toast.LENGTH_SHORT).show();
            } else {
                //We do not have the permission..
                Toast.makeText(this, "Background location access is neccessary for geofences to trigger 2...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        consultarUbicaciones();
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onStart() {
        consultarUbicaciones();
        Log.d(TAG, "onStart: ");
        super.onStart();
    }
}