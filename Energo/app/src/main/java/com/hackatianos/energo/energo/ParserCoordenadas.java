package com.hackatianos.energo.energo;

import android.util.JsonReader;

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
        String sName;
        boolean encontrado=false;
        reader.beginObject();
        for(int i=0;i<6;i++){
            reader.nextName();
            reader.skipValue();
        }
        reader.endObject();
        reader.beginArray();
        reader.beginObject();
//        reader.beginObject();
//        reader.skipValue();
//        reader.beginObject();
//        //salto valores
//        reader.skipValue();
//        reader.skipValue();
//        reader.skipValue();
//        reader.skipValue();
//        reader.skipValue();
//        reader.skipValue();
//        reader.skipValue();
//        reader.skipValue();
//        reader.skipValue();
//        reader.skipValue();
//        //fin salto
//        reader.endObject();
//        reader.beginArray();
//        reader.beginObject();
        while (reader.hasNext() && !encontrado){
            sName=reader.nextName();
            if(sName.equals("position")){
                reader.beginObject();
                //latitud
                coordenadas[0]=reader.nextDouble();
                //longitud
                coordenadas[1]=reader.nextDouble();
                reader.endObject();
                encontrado = true;
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();
        reader.endArray();
        reader.endObject();
        return coordenadas;
    }
}
