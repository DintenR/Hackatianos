package com.hackatianos.energo.energo;

import android.util.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EstacionRecarga {
    private String direccion = "";
    private String provincia = "";
    private String municipio = "";
    private String rSocial = "";
    private int numPuntosCarga = 0;
    private String tension = "";
    private double latitud = 0.0;
    private double longitud = 0.0;


    private double dist=Double.MAX_VALUE;

    private static final String URL_RUTA="https://atlas.microsoft.com/route/directions/json?subscription-key=2kohMqEWPfFGbnI5dZywTxwdIkTyFnu7SQxppyXqEIo&api-version=1.0&query=";

    public EstacionRecarga(String direccion,String provincia,String municipio,String rSocial,int numPuntosCarga,String tension,double latitud,double longitud){
        this.direccion = direccion;
        this.provincia = provincia;
        this.municipio = municipio;
        this.rSocial = rSocial;
        this.numPuntosCarga = numPuntosCarga;
        this.tension = tension;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public EstacionRecarga(){}


    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getrSocial() {
        return rSocial;
    }

    public void setrSocial(String rSocial) {
        this.rSocial = rSocial;
    }

    public int getNumPuntosCarga() {
        return numPuntosCarga;
    }

    public void setNumPuntosCarga(int numPuntosCarga) {
        this.numPuntosCarga = numPuntosCarga;
    }

    public String getTension() {
        return tension;
    }

    public void setTension(String tension) {
        this.tension = tension;
    }


    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double calculaDistancia(double latitud, double longitud){
        double latitud1 = Math.toRadians(this.latitud);
        double longitud1 = Math.toRadians(this.longitud);
        double latitud2 = Math.toRadians(latitud);
        double longitud2 = Math.toRadians(longitud);
        double r = 6371000;
        return 2*r*Math.asin(Math.sqrt(Math.pow(Math.sin((latitud2-latitud1)/2),2)+Math.cos(latitud1)*Math.cos(latitud2) *
                Math.pow(Math.sin((longitud2-longitud1)/2),2)));
    }

    public void getDistanciaReal(double latitud, double longitud) throws IOException {
        RemoteFetch remoteFetch = new RemoteFetch();
        remoteFetch.cargaBufferDesdeURL(URL_RUTA+this.longitud+this.latitud+":"+longitud+latitud);
        parseaDistancia(new JsonReader(new InputStreamReader(remoteFetch.getStream(),"UTF-8")));
    }
    private void parseaDistancia(JsonReader reader){

        try {
            reader.beginObject();
            reader.nextName();
            reader.skipValue();
            reader.nextName();
            reader.skipValue();
            reader.nextName();
            reader.skipValue();
            reader.nextName();
            reader.beginArray();
            reader.beginObject();
            reader.nextName();
            reader.beginObject();
            reader.nextName();
           dist = reader.nextDouble();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
