package com.hackatianos.energo.energo;

public class Vehiculo {
    private String marca = "";
    private int consumo = 0;
    private int autonomia = 0;

    public Vehiculo (String marca, int consumo, int autonomia){
        this.autonomia = autonomia;
        this.consumo = consumo;
        this.marca = marca;
    }

    public Vehiculo(){}

    public String getMarca(){ return marca;}
    public int getConsumo(){ return consumo;}
    public int getAutonomia(){ return autonomia;}

    public void settMarca(String marca){ this.marca = marca;}
    public void setConsumo(int consumo){ this.consumo = consumo;}
    public void setAutonomia(int autonomia){ this.autonomia = autonomia;}
}
