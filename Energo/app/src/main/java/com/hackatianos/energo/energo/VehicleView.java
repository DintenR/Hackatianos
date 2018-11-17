package com.hackatianos.energo.energo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class VehicleView extends AppCompatActivity {

    private  String veh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_view);
        veh = getIntent().getStringExtra("veh");
    }


    public void iniciarViaje(View view){
        TextView txtdestino = findViewById(R.id.txtDestino);
        String destino = txtdestino.getText().toString();
        

    }
}
