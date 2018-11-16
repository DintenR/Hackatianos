package com.hackatianos.energo.energo;

public class Vehiculo {
    private String marca;
    private int consumo;
    private int autonomía;

    public Vehiculo (String marca, int consumo, int autonomía){
        this.autonomía = autonomía;
        this.consumo = consumo;
        this.marca = marca;
    }

    public String getMarca(){ return marca;}
    public int getConsumo(){ return consumo;}
    public int getAutonomia(){ return autonomía;}
}
