package com.hackatianos.energo.energo;

import android.app.ListActivity;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ListaEstacionesRecarga {

    private List<EstacionRecarga> lista;
    private File file;

    private static final String URL_ESTACIONES = "https://sedeaplicaciones.minetur.gob.es/Greco/DatosRISP.aspx?fichero=exportarcsv";

    public ListaEstacionesRecarga()  {
        lista = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void cargaEstaciones(BufferedReader br) throws IOException {
        //file = new File("file:///android_asset/estaciones.csv");
        //file = new File(getAssets().open("cms.csv"));
        //HttpDownloadUtility.downloadFile(URL_ESTACIONES,"C:\\Users\\Ricardo\\Desktop\\Hackathon\\Hackatianos\\Energo\\app");
        lista = ParserEstacionRecarga.parseaArrayEstacionRecarga(br);

    }

    /**
     * Retorna una lista con las 5 estaciones más cercanas a la posicion indicada mediante coordenadas.
     * @param latitud latitud de la posicion indicada
     * @param longitud longitud de la posicion indicada
     * @return Lista de estaciones mas cercanas.
     */
    public List<EstacionRecarga> getMasCercanas(double latitud, double longitud){
        ordena(latitud,longitud);
        int i=0;
        List<EstacionRecarga> result = new ArrayList<>();
        Iterator<EstacionRecarga> iterator = lista.iterator();
        while(iterator.hasNext() && i<5){
            result.add(iterator.next());
        }
        return result;
    }

    private void ordena(final double latitud, final double longitud){
        Collections.sort(lista, new Comparator<EstacionRecarga>() {
            @Override
            public int compare(EstacionRecarga es1, EstacionRecarga es2) {
                int resultado;
                resultado =(int) (Math.ceil(es1.calculaDistancia(latitud, longitud) - es2.calculaDistancia(latitud, longitud)));
                return resultado;
            }
        });
    }

    private double[] puntoMedio(double[] x, double[] y){
        double x1 = x[0];
        double x2 = x[1];
        double y1 = y[0];
        double y2 = y[1];

        double z1 = (x1 + y1) / 2;
        double z2 = (x2 + y2) / 2;

        double[] t = new double[]{z1,z2};

        return t;
    }


    public List<EstacionRecarga> getLista(){
        return lista;
    }
}
