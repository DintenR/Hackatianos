package com.hackatianos.energo.energo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ListView simpleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
 /*       simpleList = findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_main, R.id.textView, countryList);
        simpleList.setAdapter(arrayAdapter);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_main,R.id.textView,StringArray);

        myRef.child("asistencia").addChildEventListener(object : ChildEventListener {
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                try {
                    val asistencia = p0.getValue(Asistencia::class.java)
                    val fechaentrada = asistencia!!.fecha
                    val calendar = Calendar.getInstance()
                    val format = SimpleDateFormat("dd-MM-yyyy",Locale.FRANCE)
                    calendar.time = fechaentrada
                    dates.add(fechaentrada)
                    datesTextColor[calendar.time] = R.color.colorRed
                    val listener = object : CaldroidListener() {
                        override fun onSelectDate(date: Date, view: View) {
                            var numberOfAlumnos = 0
                            for (i in 0 until dates.size){
                                if (format.format(dates[i]) == format.format(date)){
                                    numberOfAlumnos++
                                }
                            }
                            Toast.makeText(context, "Número de Alumnos: $numberOfAlumnos",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
                    caldroidFragment.caldroidListener = listener
                    caldroidFragment.setTextColorForDates(datesTextColor)
                    caldroidFragment.refreshView()
                }catch (a: Exception) {}
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        */
    }

    @Override
    public void onBackPressed() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }

    //Add Vehicle BTN
    public void addVehicle(View view) {
        Intent intent = new Intent(this, AddVehicle.class);
        startActivity(intent);
    }
}
