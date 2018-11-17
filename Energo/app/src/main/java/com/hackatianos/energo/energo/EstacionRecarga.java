package com.hackatianos.energo.energo;

public class EstacionRecarga {
    private String direccion;
    private String provincia;
    private String municipio;
    private String rSocial;
    private int numPuntosCarga;
    private int tension;
    private double latitud;
    private double longitud;


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

    public int getTension() {
        return tension;
    }

    public void setTension(int tension) {
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
}
