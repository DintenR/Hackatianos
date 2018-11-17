package com.hackatianos.energo.energo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddVehicle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
    }

    public void addVehicleQuery(View view) {
        TextView txtMarca = findViewById(R.id.txtMarca);
        TextView txtConsumo = findViewById(R.id.txtConsumo);
        TextView txtAutonomia = findViewById(R.id.txtAutonomia);
        String marca = txtMarca.getText().toString();
        String consumo = txtConsumo.getText().toString();
        String autonomia = txtAutonomia.getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("vehiculos").child(marca+consumo+autonomia+Math.round(Math.random() * 100000)).setValue(new Vehiculo(marca,Integer.parseInt(consumo),Integer.parseInt(autonomia)));

        cancelVehicleQuery(view);
    }
    public void cancelVehicleQuery(View view){
        finish();
        onBackPressed();
    }
}
