package com.hackatianos.energo.energo;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

class Presenter {
    ListaEstacionesRecarga lista;

    public Presenter() {
        lista = new ListaEstacionesRecarga();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean cargaEstaciones(BufferedReader br) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day == 1) {
            try {
                lista.cargaEstaciones(br);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();


            myRef.child("estaciones").addChildEventListener(new ChildEventListener() {

                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    EstacionRecarga value = dataSnapshot.getValue(EstacionRecarga.class);
                    Log.d("DEBUG",value.getDireccion()+" CARGADA");
                    lista.getLista().add(value);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    EstacionRecarga value = dataSnapshot.getValue(EstacionRecarga.class);
                    lista.getLista().remove(value);

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
        return true;
    }


    public EstacionRecarga calculaMasCercama(double latitud, double longitud) {
        double min;
        List<EstacionRecarga> cercanas = lista.getMasCercanas(latitud, longitud);
        for(EstacionRecarga e:cercanas){
            try {
                e.getDistanciaReal(latitud,longitud);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        Collections.sort(cercanas, new Comparator<EstacionRecarga>() {
            @Override
            public int compare(EstacionRecarga o1, EstacionRecarga o2) {
                return (int)(o2.getDist()-o2.getDist());
            }
        });
        return cercanas.get(0);
    }
}
