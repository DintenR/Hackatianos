package com.hackatianos.energo.energo;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class ParserEstacionRecarga {

    private ParserEstacionRecarga(){

    }

    /**
     * parseaArrayProvincias
     * <p>
     * Se le pasa un stream de datos en formato JSON que contiene provincias.
     * Crea un JsonReader para el stream de datos y llama a un método auxiliar que lo analiza
     * y extrae una lista de objetos Provincia que es la que se devuelve
     *
     * @param
     * @return List<Provincia> Lista de objetos Provincia con los datos obtenidas tras parsear el JSON
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static List parseaArrayEstacionRecarga(BufferedReader br) throws IOException {
        //FileReader reader = new FileReader(file);
        //BufferedReader br = new BufferedReader(reader);
        try {
            return readArrayEstacionRecarga(br);
        } finally {
            //reader.close();
            //br.close();
        }
    }

    /**
     * readArrayProvincias
     * <p>
     * Se le pasa un objeto JsonReader con el stream de datos JSON a analizar.
     * Crea una lista de provincias.
     * Mientras haya elementos los analiza con un método auxiliar
     * que analiza los datos de una provincia concreta
     * y devuelve un objeto Provincia que añadimos a la lista de provincias.
     * Finalmente se devuelve la lista de prvincias.
     *
     * @param reader JsonReader Stream de datos JSON apuntando al comienzo del stream
     * @return List Lista de objetos Provincia con los datos obtenidas tras parsear el JSON
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static List readArrayEstacionRecarga(BufferedReader reader) throws IOException{
        List<EstacionRecarga> lista = new ArrayList<>();
        String linea;
        reader.readLine();//lee la primera linea
        while((linea = reader.readLine()) != null){FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            EstacionRecarga e = readEstacionRecarga(linea);
            myRef.child("estaciones").child(e.getDireccion().replace("\\.","").replace("/","")).setValue(e);
        }
        Log.d("D","Se han cargado "+lista.size()+" estaciones");
        return lista;
    }

    /**
     * readProvincia
     * <p>
     * Se le pasa un objeto JsonReader con el stream de datos JSON a analizar
     * que está apuntando a una provincia concreta.
     * Va procesando este stream buscando las etiquetas de cada elemento
     * que se desea extraer, como "IDProvincia" o "Provincia"
     * y almacena la cadena de su valor en un atributo del tipo adecuado,
     * parseándolo a entero, doble, etc. si es necesario.
     * Una vez extraidos todos los atributos, crea un objeto Provincia con ellos
     * y lo devuelve.
     *
     * @param linea JsonReader stream de datos JSON
     * @return Gasolinera Objetos Gasolinera con los datos obtenidas tras parsear el JSON
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static EstacionRecarga readEstacionRecarga(String linea) throws IOException {
       EstacionRecarga estacion = new EstacionRecarga();
       String aux;
       int pos;
       linea = linea.substring(1);
       //Provincia
       pos = linea.indexOf("\"");
       Log.d("D", linea);
       aux = linea.substring(0,pos);
       Log.d("D",aux);
       aux = aux.replace("\"","");
       linea = linea.substring(pos+3,linea.length());
       aux = aux.replaceAll("\\.","").replaceAll("/","");
       estacion.setProvincia(aux);
        Log.d("D", linea);
       //Municipio
        pos = linea.indexOf("\"");
        Log.d("D","pos "+pos);
        aux = linea.substring(0,pos);
        Log.d("D",aux);
        aux = aux.replace("\"","");
        linea = linea.substring(pos+3,linea.length());
        aux = aux.replaceAll("\\.","").replaceAll("/","");
        estacion.setMunicipio(aux);
        Log.d("D", linea);
        //Direccionç

        linea = linea.substring(0,linea.length());
        Log.d("D",linea);
       pos = linea.indexOf('\"');
       aux = linea.substring(0,pos);
       aux = aux.replace("\"","");
       linea = linea.substring(pos+3,linea.length());
        aux = aux.replaceAll("\\.","").replaceAll("/","");
        estacion.setDireccion(aux);
       //rsocial
        pos = linea.indexOf('\"');
       aux = linea.substring(0,pos);
       aux = aux.replace("\"","");
       linea = linea.substring(pos+3,linea.length());
        aux = aux.replaceAll("\\.","").replaceAll("/","");
       estacion.setrSocial(aux);
       //numTomas
        pos = linea.indexOf('\"');
       aux = linea.substring(0,pos);
       aux = aux.replace("\"","");
       linea = linea.substring(pos+3,linea.length());
       estacion.setNumPuntosCarga(Integer.parseInt(aux));
       //tension
        pos = linea.indexOf('\"');
       aux = linea.substring(0,pos);
       aux = aux.replace("\"","");
       linea = linea.substring(pos+3,linea.length());
       estacion.setTension(aux);
        //coordenadas
        double[] coordenadas = new double[0];
            coordenadas = ParserCoordenadas.getCoordenadas(estacion.getDireccion() + " " + estacion.getMunicipio());
        estacion.setLatitud(coordenadas[0]);
       estacion.setLongitud(coordenadas[1]);

       return estacion;
    }
}
