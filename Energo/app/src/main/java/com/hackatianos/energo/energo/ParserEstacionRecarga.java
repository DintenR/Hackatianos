package com.hackatianos.energo.energo;


import com.hackatianos.energo.energo.EstacionRecarga;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
     * @param file Inputsream Stream de datos JSON
     * @return List<Provincia> Lista de objetos Provincia con los datos obtenidas tras parsear el JSON
     * @throws IOException
     */
    public static List<EstacionRecarga> parseaArrayEstacionRecarga(BufferedReader br) throws IOException {
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
    public static List readArrayEstacionRecarga(BufferedReader reader) throws IOException{
        List<EstacionRecarga> lista = new ArrayList<>();
        String linea;
        reader.readLine();//lee la primera linea
        while((linea = reader.readLine()) != null){
            lista.add(readEstacionRecarga(linea));
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
    private static EstacionRecarga readEstacionRecarga(String linea) throws IOException {
       EstacionRecarga estacion = new EstacionRecarga();
       String aux;
       int pos;
       //Provincia
       pos = linea.indexOf(',');
       aux = linea.substring(0,pos);
       Log.d("D",aux);
       aux = aux.replace("\"","");
       linea = linea.substring(pos+1,linea.length());
       estacion.setProvincia(aux);
       //Municipio
        pos = linea.indexOf(',');
        aux = linea.substring(0,pos);
        Log.d("D",aux);
        aux = aux.replace("\"","");
        linea = linea.substring(pos+1,linea.length());
        estacion.setMunicipio(aux);
        //Direccionç

        linea = linea.substring(1,linea.length());
        Log.d("D",linea);
       pos = linea.indexOf('\"');
       aux = linea.substring(0,pos);
       aux = aux.replace("\"","");
       linea = linea.substring(pos+2,linea.length());
        estacion.setDireccion(aux);
       //rsocial
       pos = linea.indexOf(',');
       aux = linea.substring(0,pos);
       aux = aux.replace("\"","");
       linea = linea.substring(pos+1,linea.length());
       estacion.setrSocial(aux);
       //numTomas
       pos = linea.indexOf(',');
       aux = linea.substring(0,pos);
       aux = aux.replace("\"","");
       linea = linea.substring(pos+1,linea.length());
       estacion.setNumPuntosCarga(Integer.parseInt(aux));
       //tension
       pos = linea.indexOf(',');
       aux = linea.substring(0,pos);
       aux = aux.replace("\"","");
       linea = linea.substring(pos+1,linea.length());
       estacion.setTension(Integer.parseInt(aux));
        //coordenadas
       double[] coordenadas = ParserCoordenadas.getCoordenadas(estacion.getDireccion());
       estacion.setLatitud(coordenadas[0]);
       estacion.setLongitud(coordenadas[1]);

       return estacion;
    }
}
