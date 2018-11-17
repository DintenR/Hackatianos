package com.hackatianos.energo.energo;

import android.app.ListActivity;

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
    }

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
}
