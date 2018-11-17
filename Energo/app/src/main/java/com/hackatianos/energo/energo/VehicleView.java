package com.hackatianos.energo.energo;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class VehicleView extends AppCompatActivity {
    final int REQUEST_PERMISSION_PHONE_STATE=1;
    private  String veh;
    private String email;
    Location location = null;
    String destino;
    Vehiculo value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_view);
        veh = getIntent().getStringExtra("veh");
        email = getIntent().getStringExtra("email");
    }


    public void iniciarViaje(View view){
        TextView txtdestino = findViewById(R.id.txtDestino);
        destino = txtdestino.getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("usuarios").child(email).child("vehiculos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                value = dataSnapshot.getValue(Vehiculo.class);
                if(value.getMarca().contains(veh)){
                    //Localizacion
                    LocationManager locationManager = (LocationManager)
                            getSystemService(VehicleView.LOCATION_SERVICE);
                    ActivityCompat.requestPermissions(VehicleView.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    if(checkLocationPermission()){
                        Toast.makeText(VehicleView.this, "GPS PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                   new CalculaMasCercanaView(VehicleView.this).execute();
                }

            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException());
            }
        });





    }
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_PHONE_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(VehicleView.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VehicleView.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }

    public  boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    class CalculaMasCercanaView extends AsyncTask<Void, Void, Boolean> {

        Activity activity;

        /**
         * Constructor de la tarea asincrona
         *
         * @param activity
         */
        public CalculaMasCercanaView(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            double[] coordenadas = new double[0];
            try {
                coordenadas = ParserCoordenadas.getCoordenadas(destino);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(VehicleView.this, MapsActivity.class);
            intent.putExtra("destino1",coordenadas[0]);
            intent.putExtra("destino2",coordenadas[1]);
            intent.putExtra("inicio1",location.getLatitude());
            intent.putExtra("inicio2",location.getLongitude());
            intent.putExtra("autonomia",value.getAutonomia());
            startActivity(intent);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            //No es necesario hacer nada en el metodo postExecute.

        }


    }
}
