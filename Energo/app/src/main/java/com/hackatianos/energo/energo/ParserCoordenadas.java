package com.hackatianos.energo.energo;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;

public class ParserCoordenadas {

    public static RemoteFetch remoteFetch = new RemoteFetch();
    private final static String URL = "https://atlas.microsoft.com/search/address/json?subscription-key=2kohMqEWPfFGbnI5dZywTxwdIkTyFnu7SQxppyXqEIo&api-version=1.0&query=";
    public static double[] getCoordenadas(String direccion) throws IOException {
        double[] coordenadas;
        direccion = URL + direccion;
        remoteFetch.cargaBufferDesdeURL(direccion);
        JsonReader reader = new JsonReader(new InputStreamReader(remoteFetch.getStream(),"UTF-8"));
        coordenadas = parseaCoordenadas(reader);
        return coordenadas;
    }
    private static double[] parseaCoordenadas(JsonReader reader) throws IOException {
        double[] coordenadas = new double[2];
        try{
        reader.beginObject();
        reader.nextName();
        reader.skipValue();
        reader.nextName();
        reader.beginArray();
        reader.beginObject();
        reader.nextName();
        reader.skipValue();
        reader.nextName();
        reader.skipValue();
        reader.nextName();
        reader.skipValue();
        reader.nextName();
        reader.skipValue();
        reader.nextName();
        reader.beginObject();
        reader.nextName();
        coordenadas[0] = reader.nextDouble(); //Lat
        reader.nextName();
        coordenadas[1] = reader.nextDouble(); //Log
        }catch(Exception e){

        }
        return coordenadas;
    }

}
