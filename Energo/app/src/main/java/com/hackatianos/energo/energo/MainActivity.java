package com.hackatianos.energo.energo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ListView simpleList;
    private String email;
    ArrayList<Vehiculo> listVeh = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = getIntent().getStringExtra("email");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        simpleList = findViewById(R.id.simpleListView);


        myRef.child("usuarios").child(email).child("vehiculos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Vehiculo value = dataSnapshot.getValue(Vehiculo.class);
                listVeh.add(value);
                //ArrayAdapter<Vehiculo> arrayAdapter = new ArrayAdapter<Vehiculo>(this, R.layout.activity_main, R.id.textView, (Vehiculo[]) listVeh.toArray());
                //simpleList.setAdapter(arrayAdapter);
                //ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_main,R.id.textView,StringArray);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void onBackPressed() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }

    //Add Vehicle BTN
    public void addVehicle(View view) {
        Intent intent = new Intent(this, AddVehicle.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }
}
