package com.hackatianos.energo.energo;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    Presenter presenter;
    BufferedReader br;
    private FirebaseAuth mAuth;
    private ListView simpleList;
    private String email;
    ArrayList<String> listNameVeh = new ArrayList<>();
    private EstacionRecarga e;
    private double latitud, longitud;
    private Location location;



    @Override
    public void onBackPressed() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        super.onBackPressed();
    }

    public void cerrarSesion() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }

    //Add Vehicle BTN
    public void addVehicle(View view) {
        Intent intent = new Intent(this, AddVehicle.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        email = getIntent().getStringExtra("email");
        Log.d("DEBUG", "EMAIL:" + email);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        simpleList = findViewById(R.id.simpleListView);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String vehName = listNameVeh.get(arg2);
                Intent intent = new Intent(getBaseContext(), VehicleView.class);
                intent.putExtra("veh",vehName);
                intent.putExtra("email",email);
                startActivity(intent);
            }

        });

/*
        //Localizacion
        LocationManager locationManager = (LocationManager)
                getSystemService(this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    */
        email = getIntent().getStringExtra("email");
        Log.d("DEBUG","EMAIL:"+email);
        simpleList = findViewById(R.id.simpleListView);


        myRef.child("usuarios").child(email).child("vehiculos").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Vehiculo value = dataSnapshot.getValue(Vehiculo.class);
                listNameVeh.add(value.getMarca());
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_listview, R.id.textView, listNameVeh);
                simpleList.setAdapter(arrayAdapter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Vehiculo value = dataSnapshot.getValue(Vehiculo.class);
                listNameVeh.remove(s.replaceAll("\\d",""));
                listNameVeh.add(value.getMarca());
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_listview, R.id.textView, listNameVeh);
                simpleList.setAdapter(arrayAdapter);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Vehiculo value = dataSnapshot.getValue(Vehiculo.class);
                listNameVeh.remove(value.getMarca());

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getBaseContext(), R.layout.activity_listview, R.id.textView, listNameVeh);
                simpleList.setAdapter(arrayAdapter);

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

        presenter = new Presenter();
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open("estaciones.csv")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new CargaDatosEstacionesTask(MainActivity.this).execute();


    }
    private class CargaDatosEstacionesTask extends AsyncTask<Void, Void, Boolean> {

        Activity activity;

        /**
         * Constructor de la tarea asincrona
         *
         * @param activity
         */
        public CargaDatosEstacionesTask(Activity activity) {
            this.activity = activity;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(Void... voids) {
            return presenter.cargaEstaciones(br);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            //No es necesario hacer nada en el metodo postExecute.
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    class CalculaMasCercana extends AsyncTask<Void, Void, Boolean> {

        Activity activity;

        /**
         * Constructor de la tarea asincrona
         *
         * @param activity
         */
        public CalculaMasCercana(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            e  = presenter.calculaMasCercama( latitud,  longitud);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            //No es necesario hacer nada en el metodo postExecute.
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
