package com.hackatianos.energo.energo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

class Presenter {
    ListaEstacionesRecarga lista ;

    public Presenter(){
            lista = new ListaEstacionesRecarga();
    }
    public boolean cargaEstaciones(BufferedReader br){
        try {
            lista.cargaEstaciones(br);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
