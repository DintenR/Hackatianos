package com.hackatianos.energo.energo;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    Presenter presenter;
    BufferedReader br;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
