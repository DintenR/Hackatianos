package com.hackatianos.energo.energo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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
    ArrayList<String> listNameVeh = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = getIntent().getStringExtra("email");
        Log.d("DEBUG","EMAIL:"+email);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        simpleList = findViewById(R.id.simpleListView);


        myRef.child("usuarios").child(email).child("vehiculos").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Vehiculo value = dataSnapshot.getValue(Vehiculo.class);
                listNameVeh.add(value.getMarca());

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getBaseContext(), R.layout.activity_listview, R.id.textView, listNameVeh);
                simpleList.setAdapter(arrayAdapter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
