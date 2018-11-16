package com.hackatianos.energo.energo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

public class AddVehicle extends AppCompatActivity {
    TextView txtMarca = (EditText) findViewById(R.id.txtMarca);
    TextView txtConsumo = (EditText) findViewById(R.id.txtConsumo);
    TextView txtAutonomia = (EditText) findViewById(R.id.txtAutonomia);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
    }


    private void addVehicle(){

    }

}
